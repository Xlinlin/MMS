package org.xiao.message.manager.consumer.api;

import org.xiao.message.bean.query.ConsumerQuery;
import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.manager.consumer.dto.ConsumerDto;
import org.xiao.message.service.ConsumerService;
import org.xiao.message.service.ServerConfigTopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:消费者
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/29 14:06
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/consumer")
@Slf4j
public class ConsumerRestService
{
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ServerConfigTopicService serverConfigTopicService;

    /**
     * [简要描述]:新增消费者<br/>
     * [详细描述]:<br/>
     *
     * @param consumerDto :
     * @return java.lang.Boolean
     * jun.liu  2018/10/29 - 14:31
     **/
    @RequestMapping(value = "/save")
    public Boolean saveConsumer(@RequestBody ConsumerDto consumerDto)
    {
        if (StringUtils.isBlank(consumerDto.getTopic()))
        {
            throw new RuntimeException("topic不能为空");
        }
        int a = consumerService.create(ConsumerDto.convertToConsumerDocument(consumerDto));
        return a > 0;
    }

    /**
     * [简要描述]:分页获取<br/>
     * [详细描述]:<br/>
     *
     * @return org.springframework.data.domain.Page<ConsumerDto>
     * jun.liu  2018/10/29 - 14:47
     **/
    @RequestMapping(value = "/pageConsumer")
    public Page<ConsumerDto> pageConsumer(@RequestBody ConsumerQuery consumerQuery)
    {
        Page page = consumerService.findPage(consumerQuery);
        List<ConsumerDocument> list = page.getContent();
        List<ConsumerDto> dtoList = new ArrayList<>();
        for (ConsumerDocument consumerDocument : list)
        {
            dtoList.add(ConsumerDto.convertToTopicDto(consumerDocument));
        }
        getOnlineConsumer(consumerQuery.getTopic(), dtoList);
        return new PageImpl<>(dtoList, new PageRequest(
                consumerQuery.getPageNum() - 1, consumerQuery.getPageSize()), page.getTotalElements());
    }

    /**
     * [简要描述]:消费者在线情况<br/>
     * [详细描述]:<br/>
     *
     * @param topic :
     * @param dtoList :
     * llxiao  2018/11/6 - 9:11
     **/
    private void getOnlineConsumer(String topic, List<ConsumerDto> dtoList)
    {
        //        if (CollectionUtil.isNotEmpty(dtoList))
        //        {
        //            ServerConfigTopicRelationshipDocument document = serverConfigTopicService.getServerIpByTopic(topic);
        //            if (null != document)
        //            {
        //                String brokerHost = document.getServerIp();
        //                KafkaConsumerGroupCustomService kafkaConsumerGroupCustomService = new KafkaConsumerGroupCustomService(brokerHost);
        //                List<PartitionAssignmentState> consumer;
        //                for (ConsumerDto consumerDto : dtoList)
        //                {
        //                    consumer = kafkaConsumerGroupCustomService.collectGroupAssignment(consumerDto.getConsumerId());
        //                    for (PartitionAssignmentState partitionAssignmentState : consumer)
        //                    {
        //                        if (StringUtils.isNotBlank(partitionAssignmentState.getClientId()))
        //                        {
        //                            log.info("Consumer info: clientId:{},consumerId:{}", partitionAssignmentState
        //                                    .getClientId(), partitionAssignmentState.getConsumerId());
        //                            consumerDto.setOnline(ConsumerDto.ONLINE_FLAG);
        //                        }
        //                    }
        //                }
        //                //释放连接
        //                kafkaConsumerGroupCustomService.close();
        //            }
        //        }

    }

    /**
     * [简要描述]:删除<br/>
     * [详细描述]:<br/>
     *
     * @param id :
     * @return java.lang.Boolean
     * jun.liu  2018/11/1 - 16:18
     **/
    @RequestMapping(value = "/delete/{id}")
    public Boolean delete(@PathVariable("id") String id)
    {
        if (StringUtils.isBlank(id))
        {
            throw new RuntimeException("id不能为空");
        }
        int a = consumerService.remove(id);
        return a > 0;
    }
}
