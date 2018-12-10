package org.xiao.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCaching
public class MessageServiceProducerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MessageServiceProducerApplication.class, args);
    }
}
