package org.xiao.message.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/29 15:54
 * @since JDK 1.8
 */
@Configuration
public class SmsConfig
{

    private static final Log LOG = LogFactory.getLog(SmsConfig.class);

    @Value("${ali.sms.product}")
    private String product = "";
    @Value("${ali.sms.domain}")
    private String domain = "";
    @Value("${ali.sms.access_key_id}")
    private String accessKeyId = "";
    @Value("${ali.sms.access_key_secret}")
    private String accessKeySecret = "";
    @Value("${sun.net.client.defaultConnectTimeout}")
    private String defaultConnectTimeout;
    @Value("${sun.net.client.defaultReadTimeout}")
    private String defaultReadTimeout;

    /**
     *[简要描述]:初始化acsClient,暂不支持region化<br/>

     * @return void 
     * jun.liu  2018/10/29 - 16:12
     **/
    @Bean
    public IAcsClient createAcsClient() throws ClientException
    {
            LOG.info("--------------开始初始化阿里云通信连接--------------");

            System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
            System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            LOG.info("--------------初始化阿里云通信连接完成--------------");
            return acsClient;
    }
}
