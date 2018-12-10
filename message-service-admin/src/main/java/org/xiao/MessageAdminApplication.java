package org.xiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/25 11:38
 * @since JDK 1.8
 */
@SpringBootApplication()
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class MessageAdminApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MessageAdminApplication.class, args);
    }
}
