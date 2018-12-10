package org.xiao.message.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.xiao.message.api.DeliveryMessageDto;
import org.xiao.message.api.FeedbackMessageDto;
import org.xiao.message.api.MessageFeedbackFeign;
import org.xiao.message.api.ServerConfigFeign;
import org.xiao.message.util.NetUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.xiao.message.kafka.KafkaConsumerBuild;
import org.xiao.message.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseKafkaConsumerServer
        implements MessageListener<String, String>, ApplicationListener<ContextRefreshedEvent>, DisposableBean
{
    private static final String BROKER_API = "/api/server/getByTopic";
    private static final String FEEDBACK_API = "/api/message/feedback";

    private static final Integer taskNumber = 3;

    private static final List<MessageFeedbackTask> taskList = new ArrayList<>();

    private KafkaMessageListenerContainer<String, String> container;

    protected String serverIp;

    protected String messageServerHost = "http://192.168.206.208:7767";

    public abstract String getConsumerId();

    public abstract String getTopic();

    public abstract MessageListener getMessageListener();

    //业务逻辑处理
    public abstract void consumerMessage(String message);

    @Autowired
    MessageFeedbackFeign messageFeedbackFeign;

    @Autowired
    ServerConfigFeign serverConfigFeign;

    OkHttpClient okHttpClient;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (event.getSource() instanceof AnnotationConfigEmbeddedWebApplicationContext)
        {
            AnnotationConfigEmbeddedWebApplicationContext context = (AnnotationConfigEmbeddedWebApplicationContext) event
                    .getSource();
            if (context.isRunning())
            {
                //initHttpClient();
                this.initKafkaListener();
                for (int i = 0; i < taskNumber; i++)
                {
                    taskList.add(new MessageFeedbackTask(messageFeedbackFeign));
                    //taskList.add(new MessageFeedbackTask(okHttpClient, messageServerHost));
                }
            }
        }
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> record)
    {
        String topic = record.topic();
        String value = record.value();
        String key = record.key();
        if (log.isDebugEnabled())
        {
            log.debug("~~~~~~~~~~~~接收到topic为{}的消息!", topic);
            log.debug("~~~~~~~~~~~~原始消息：{}", value);
            log.debug("~~~~~~~~~~~~消息key：{}", key);
        }

        DeliveryMessageDto dto = null;
        Gson gson = new Gson();
        try
        {
            dto = gson.fromJson(value, DeliveryMessageDto.class);
        }
        catch (JsonParseException e)
        {
            log.error("~~~~~~消息解析异常，主题：{}，消息Key{}");
            log.error("~~~~~~原始消息内容：{}");
            log.error("~~~~~~异常消息------------", e);
        }

        if (null != dto)
        {
            if (log.isDebugEnabled())
            {
                log.debug("业务消息内容：{}", gson.toJson(dto));
            }
            // 消息收到反馈
            feedbackMessage(dto);

            // 业务逻辑处理
            consumerMessage(dto.getMessageBody());
        }

    }

    private static class MessageFeedbackTask implements Runnable
    {
        private static final MediaType json = MediaType.parse("application/json; charset=utf-8");

        private ConcurrentLinkedQueue<FeedbackMessageDto> messageDtos = new ConcurrentLinkedQueue<>();

        private MessageFeedbackFeign feedbackFeign;

        private OkHttpClient okHttpClient;

        private String serverHost;

        public MessageFeedbackTask(MessageFeedbackFeign feedbackFeign)
        {
            setDaemonThread();
            this.feedbackFeign = feedbackFeign;
        }

        public MessageFeedbackTask(OkHttpClient okHttpClient, String serverHost)
        {
            setDaemonThread();
            this.serverHost = serverHost;
            this.okHttpClient = okHttpClient;
        }

        private void setDaemonThread()
        {
            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }

        @Override
        public void run()
        {
            while (true)
            {
                feedbackMessage();
            }
        }

        //消息反馈
        private void feedbackMessage()
        {
            try
            {
                if (messageDtos.size() > 0)
                {
                    FeedbackMessageDto messageDto = messageDtos.poll();
                    if (feedbackFeign != null)
                    {
                        if (log.isDebugEnabled())
                        {
                            log.debug("开始上报已收到消息，反馈消息内容：{}", JSON.toJSONString(messageDto));
                        }
                        feedbackFeign.feedbackMessage(messageDto);
                    }
                    else
                    {
                        // http提交
                        httpFeedback(messageDto);
                    }
                }
                else
                {
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e)
            {
                log.error("消息反馈失败，当前线程中断。", e);
            }
        }

        //异步HTTP JSON提交
        private void httpFeedback(FeedbackMessageDto messageDto)
        {
            Gson gson = new Gson();
            String params = gson.toJson(messageDto);
            RequestBody requestBody = RequestBody.create(json, params);
            StringBuilder sb = new StringBuilder();
            if (!serverHost.startsWith("http://"))
            {
                sb.append("http://");
            }
            sb.append(serverHost);
            sb.append(FEEDBACK_API);
            final Request request = new Request.Builder().url(sb.toString()).post(requestBody).build();
            okHttpClient.newCall(request);
        }

    }

    /**
     * [简要描述]:kafka消费者监听初始化<br/>
     * [详细描述]:<br/>
     * <p>
     * llxiao  2018/10/23 - 17:10
     **/
    protected void initKafkaListener()
    {
        // 如手动填写，则不进行远程获取
        if (StringUtils.isBlank(serverIp))
        {
            //远程服务器通过主题获取服务列表
            serverIp = serverConfigFeign.getByTopic(getTopic());
            // http远程获取连接
            //serverIp = getBrokerServer();
        }
        if (StringUtils.isBlank(serverIp))
        {
            throw new RuntimeException("~~~~该主题暂无绑定服务，无法进行broker服务器连接，请检查相主题与服务器绑定关配置!");
        }

        if (log.isDebugEnabled())
        {
            log.debug("~~~~~~~~~~~初始化kafka连接....");
            log.debug("~~~~~~~~~~~kafka服务器地址：{}", serverIp);
            log.debug("~~~~~~~~~~~订阅主题：{}", getTopic());
            log.debug("~~~~~~~~~~~客户端ID和组：{}", getConsumerId());
        }

        KafkaConsumerBuild build = new KafkaConsumerBuild();
        ConsumerFactory<String, String> consumerFactory = build.consumerFactory(getConsumerId(), serverIp);
        ContainerProperties containerProperties = build.createContainerProperties(getTopic(), getMessageListener());

        //委托给1个或多个KafkaMessageListenerContainer以提供多线程消费
        //ConcurrentMessageListenerContainer<String, String> concurrentMessageListenerContainer = new ConcurrentMessageListenerContainer(consumerFactory, containerProperties);
        //设置线程数
        //concurrentMessageListenerContainer.setConcurrency(3);
        //concurrentMessageListenerContainer.start();

        //从单个线程上的所有主题/分区接收所有消息。
        container = build.createKafkaMessageListenerContainer(containerProperties, consumerFactory);
        container.start();
    }

    //消息反馈
    private void feedbackMessage(DeliveryMessageDto dto)
    {
        FeedbackMessageDto feedbackMessageDto = getFeedbackMessageDto(dto);
        messageFeedbackFeign.feedbackMessage(feedbackMessageDto);
        // 异步消息反馈
        //taskList.get(new Random().nextInt(taskNumber)).messageDtos.add(feedbackMessageDto);
    }

    private FeedbackMessageDto getFeedbackMessageDto(DeliveryMessageDto dto)
    {
        FeedbackMessageDto feedbackMessageDto = new FeedbackMessageDto();
        feedbackMessageDto.setConsumerIp(NetUtil.getLoacalHost());
        feedbackMessageDto.setConsumerTime(DateUtils.currentTime());
        feedbackMessageDto.setConsumerId(getConsumerId());
        feedbackMessageDto.setMessageId(dto.getMessageId());
        feedbackMessageDto.setTraceLogId(dto.getTraceLogId());
        return feedbackMessageDto;
    }

    //同步获取服务器地址
    private String getBrokerServer()
    {
        StringBuilder getServerUrl = new StringBuilder();
        if (!messageServerHost.startsWith("http"))
        {
            getServerUrl.append("http://");
        }
        getServerUrl.append(messageServerHost);
        getServerUrl.append(BROKER_API);
        getServerUrl.append("?topic=");
        getServerUrl.append(getTopic());
        final Request request = new Request.Builder().url(getServerUrl.toString()).build();
        final Call call = okHttpClient.newCall(request);
        try
        {
            Response response = call.execute();
            return response.body().string();
        }
        catch (IOException e)
        {
            log.error("获取服务器信息失败", e);
        }
        return "";
    }

    private void initHttpClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    @Override
    public void destroy()
    {
        container.stop();
    }
}
