package org.xiao.message.manager.message.api;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.bean.query.MessageQuery;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.manager.message.dto.MessageSourceDto;
import org.xiao.message.manager.message.dto.MessageTraceLogDto;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.MessageTraceLogService;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]: 消息管理服务
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/25 15:33
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/message/manager")
public class MessageAdminApi
{
    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private MessageTraceLogService messageTraceLogService;

    /**
     * [简要描述]:ID查找<br/>
     * [详细描述]:<br/>
     *
     * @param messageId :
     * @return MessageSourceDto
     * llxiao  2018/10/25 - 15:45
     **/
    @RequestMapping("/findByMessageId")
    public MessageSourceDto findByMessageId(String messageId)
    {
        MessageSourceDocument messageSourceDocument = this.messageSourceService.findByMessageId(messageId);
        return MessageSourceDto.convertDto(messageSourceDocument);
    }

    /**
     * [简要描述]:分页查询<br/>
     * [详细描述]:<br/>
     *
     * @param messageQuery : 分页条件
     * @return org.springframework.data.domain.Page<MessageSourceDto>
     * llxiao  2018/10/25 - 17:17
     **/
    @RequestMapping("/findPage")
    public Page<MessageSourceDto> findPage(@RequestBody MessageQuery messageQuery)
    {
        Page page = messageSourceService.findPage(messageQuery);
        List<MessageSourceDocument> list = page.getContent();
        List<MessageSourceDto> sourceDtos = new ArrayList<>();
        for (MessageSourceDocument messageSourceDocument : list)
        {
            sourceDtos.add(MessageSourceDto.convertDto(messageSourceDocument));
        }
        return new PageImpl<>(sourceDtos, new PageRequest(
                messageQuery.getPageNum() - 1, messageQuery.getPageSize()), page.getTotalElements());
    }

    /**
     * [简要描述]:消息ID追踪消息<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @return java.util.List<MessageTraceLogDto>
     * llxiao  2018/11/7 - 14:53
     **/
    @RequestMapping("/trail")
    public List<MessageTraceLogDto> trailMessage(String messageId)
    {
        List<MessageTraceLogDto> traceLogDtos = new ArrayList<>();
        List<MessageTraceLogDocument> documents = messageTraceLogService.findByMessageId(messageId);
        if (CollectionUtil.isNotEmpty(documents))
        {
            for (MessageTraceLogDocument document : documents)
            {
                traceLogDtos.add(MessageTraceLogDto.convertDto(document));
            }
        }

        return traceLogDtos;
    }

}
