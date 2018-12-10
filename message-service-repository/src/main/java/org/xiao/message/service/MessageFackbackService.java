package org.xiao.message.service;

import java.util.List;

import org.xiao.message.document.MessageFackbackDocument;

public interface MessageFackbackService {

	List<MessageFackbackDocument> findByMessageId(String messageId);

	void addSendCount(String messageId);
	
}
