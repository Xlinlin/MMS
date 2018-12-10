package org.xiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author nietao
 * Date 2018/10/27
 */
@SpringBootApplication()
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class MessageWebApplication
{
    public static void main(String args[])
    {
        SpringApplication.run(MessageWebApplication.class, args);
    }
}
