package org.xiao.message.monitor.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.message.api.ApiMessage;
import org.xiao.message.monitor.api.message.api.MessageService;
import org.xiao.message.monitor.api.message.bean.dto.MessageSourceDto;
import org.xiao.message.monitor.api.message.bean.dto.MessageTraceLogDto;
import org.xiao.message.monitor.api.message.bean.query.MessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor/message/query_message")
public class MessageController
{

    @Autowired
    private MessageService messageService;
    @Autowired
    private ApiMessage apiMessage;

    @RequestMapping("/findPage")
    public MongoPageImp<MessageSourceDto> findPage(@RequestBody MessageQuery messageQuery)
    {
        return messageService.findPage(messageQuery);
    }

    @PostMapping(path = "send")
    public String sendMessage(@RequestParam("messageId") String messageId)
    {
        return apiMessage.resendMessage(messageId);
    }

    @RequestMapping("/trail")
    public List<MessageTraceLogDto> trailMessage(@RequestParam("messageId")String messageId){
        return messageService.trailMessage(messageId);
    }
}
