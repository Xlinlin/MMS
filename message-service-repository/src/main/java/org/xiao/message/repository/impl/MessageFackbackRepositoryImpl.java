package org.xiao.message.repository.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import org.xiao.message.document.MessageFackbackDocument;
import org.xiao.message.repository.MessageFackbackRepository;
@Service
public class MessageFackbackRepositoryImpl extends BaseMongoRepositoryImpl<MessageFackbackDocument, String> implements MessageFackbackRepository {

	public MessageFackbackRepositoryImpl() {
		super(MessageFackbackDocument.class);
	}

	@Override
	public void addSendCount(String messageId) {
		Query query=new Query(Criteria.where("messageId").is(messageId));
		 
		Update update=new Update();
		update.inc("currentUseCount", 1);
		mongoTemplate.updateFirst(query, update, MessageFackbackDocument.class);
	}
	
	@Override
	public MessageFackbackDocument findByMessageIdAndConsumerCode(String messageId, String consumerId) {
		Query query=new Query(Criteria.where("messageId").is(messageId).and("consumerCode").is(consumerId));
		 
		return mongoTemplate.findOne(query, MessageFackbackDocument.class);
	}
	
}
