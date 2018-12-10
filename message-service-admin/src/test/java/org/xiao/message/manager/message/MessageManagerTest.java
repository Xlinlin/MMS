package org.xiao.message.manager.message;

import com.alibaba.fastjson.JSON;
import org.xiao.message.bean.query.MessageQuery;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.SmsService;
import org.xiao.MessageAdminApplicationTest;
import org.xiao.message.manager.message.dto.MessageSourceDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/25 15:54
 * @since JDK 1.8
 */
public class MessageManagerTest extends MessageAdminApplicationTest
{
    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private SmsService smsService;

    @Test
    public void testFindById()
    {
        String messageId = "be3f2f79-7533-4c50-9e78-8952d702c6f5";
        MessageSourceDocument document = messageSourceService.findByMessageId(messageId);
        System.out.println(document);
    }

    @Test
    public void testFindPage()
    {
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setDirection(true);
        messageQuery.setTopic("init_test");
        messageQuery.setStartTime("2018-11-05 15:09:43");
        messageQuery.setEndTime("2018-11-06 15:29:00");
        Page page = messageSourceService.findPage(messageQuery);
        System.out.println(page.getTotalPages());
        System.out.println(JSON.toJSONString(page));

        List<MessageSourceDocument> list = page.getContent();
        List<MessageSourceDto> sourceDtos = new ArrayList<>();
        for (MessageSourceDocument messageSourceDocument : list)
        {
            sourceDtos.add(MessageSourceDto.convertDto(messageSourceDocument));
        }
        Page<MessageSourceDto> dtos = new PageImpl<>(sourceDtos, new PageRequest(
                messageQuery.getPageNum() - 1, messageQuery.getPageSize()), page.getTotalElements());
        System.out.println(JSON.toJSONString(dtos));
    }

    @Test
    public void countTest()
    {
        Long dateCount = messageSourceService.countByDate("2018-11-07");
        System.out.println(dateCount);
        Long unSendCount = messageSourceService.countByStatus(MessageSourceDocument.SEND);
        System.out.println(unSendCount);
        Long abnormalCount = messageSourceService.countByStatus(MessageSourceDocument.ABNORMAL);
        System.out.println(abnormalCount);
        Long deadCount = messageSourceService.countByStatus(MessageSourceDocument.DEAD);
        System.out.println(deadCount);
        System.out.println(smsService.countByDate("2018-10-31"));
        System.out.println(smsService.countByStatus(1));
        System.out.println(smsService.countByStatus(3));
    }
}
