package org.xiao.message.repository;

import org.xiao.message.document.MessageFackbackDocument;

public interface MessageFackbackRepository extends BaseMongoRepository<MessageFackbackDocument, String>{

	void addSendCount(String messageId);

	MessageFackbackDocument findByMessageIdAndConsumerCode(String messageId, String consumerId);

}
