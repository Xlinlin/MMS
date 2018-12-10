package org.xiao.message.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.message.api.MessageService;
import org.xiao.message.monitor.api.message.bean.dto.MessageSourceDto;
import org.xiao.message.monitor.api.message.bean.query.MessageQuery;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author sylu
 * @version 1.0, 2018/11/7 09:33
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/monitor/message/dead_queue")
public class DeadQueueController
{

    @Autowired
    private MessageService messageService;

    @GetMapping("/findPage")
    public MongoPageImp<MessageSourceDto> getPageData(MessageQuery messageQuery)
    {
        MongoPageImp<MessageSourceDto> pageData = messageService.findPage(messageQuery);
        return pageData;
    }
}