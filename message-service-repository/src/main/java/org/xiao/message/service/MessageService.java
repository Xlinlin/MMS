package org.xiao.message.service;

import org.xiao.message.bean.dto.FeedbackMessageDto;
import org.xiao.message.bean.dto.SendMessageDto;
import org.xiao.message.document.MessageSourceDocument;

public interface MessageService
{
    MessageSourceDocument sendMessage(SendMessageDto messageDto);

    void feedbackMessage(FeedbackMessageDto messageDto);

    MessageSourceDocument findById(String messageId);
}
