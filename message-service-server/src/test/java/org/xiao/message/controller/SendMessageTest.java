package org.xiao.message.controller;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.repository.MessageSourceRepository;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/31 16:07
 * @since JDK 1.8
 */
public class SendMessageTest extends BaseTest
{
    @Autowired
    MessageSourceRepository messageSourceRepository;

    @Test
    public void testSendMessage()
    {
        String topic = "测试topic8";
        String message = "test_message_";
        for (int i = 0; i < 100; i++)
        {
            MessageSourceDocument document = new MessageSourceDocument();
            String messageId = UUID.randomUUID().toString();
            document.setBusinessKey(topic + i);
            document.setCreateTime(DateUtils.currentTime());
            document.setKey(topic + i);
            document.setMessage(message + i);
            document.setMessageId(messageId);
            document.setTopic(topic);
            document.setReceiveIp(NetUtil.getLoacalHost());
            //消息状态记录
            document.setStatus(MessageSourceDocument.RECEIVED);
            messageSourceRepository.save(document);
        }

    }
}
