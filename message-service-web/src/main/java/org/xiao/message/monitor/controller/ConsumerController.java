package org.xiao.message.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.customer.api.ConsumerRestService;
import org.xiao.message.monitor.api.customer.dto.ConsumerDto;
import org.xiao.message.monitor.api.customer.query.ConsumerQuery;

@Slf4j
@RestController
@RequestMapping("/api/consumer")
public class ConsumerController
{
    @Autowired
    private ConsumerRestService consumerRestService;

    @RequestMapping(value = "/save")
    public Boolean saveConsumer(@RequestBody ConsumerDto consumerDto)
    {
        return consumerRestService.saveConsumer(consumerDto);
    }

    @RequestMapping(value = "/pageConsumer")
    public MongoPageImp<ConsumerDto> pageConsumer(@RequestBody ConsumerQuery consumerQuery)
    {
        return consumerRestService.pageConsumer(consumerQuery);
    }

    @RequestMapping(value = "/delete/{id}")
    public Boolean delete(@PathVariable("id") String id)
    {
        return consumerRestService.delete(id);
    }
}
