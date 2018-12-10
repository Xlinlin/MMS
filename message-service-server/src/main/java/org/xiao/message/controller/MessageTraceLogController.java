package org.xiao.message.controller;

import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.service.MessageTraceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message/trace/log")
public class MessageTraceLogController
{
    @Autowired
    MessageTraceLogService messageTraceLogService;

    @PostMapping(path = "/save")
    public int save(@RequestBody MessageTraceLogDocument document)
    {
        messageTraceLogService.add(document);
        return 1;
    }

    @PostMapping(path = "/remove/{id}")
    public int remove(@PathVariable String id)
    {
        messageTraceLogService.delete(id);
        return 1;
    }
}
