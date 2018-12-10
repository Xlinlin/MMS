package org.xiao.message.controller;

import org.xiao.message.bean.dto.ConsumerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.service.ConsumerService;

@RestController
@RequestMapping("/consumer")
public class ConsumerController
{
    @Autowired
    ConsumerService consumerService;

    @PostMapping(path = "/create")
    public int create(@RequestBody ConsumerDto consumerDto)
    {
        ConsumerDocument document = new ConsumerDocument();
        document.setDesc(consumerDto.getDesc());
        document.setTopic(consumerDto.getTopic());
        document.setConsumerCode(consumerDto.getConsumerCode());
        return consumerService.create(document);
    }

    @PostMapping(path = "/remove/{id}")
    public int remove(@PathVariable String id)
    {
        return consumerService.remove(id);
    }
}
