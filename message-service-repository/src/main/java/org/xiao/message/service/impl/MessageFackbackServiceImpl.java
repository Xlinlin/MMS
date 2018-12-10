package org.xiao.message.service.impl;

import java.util.List;

import org.xiao.message.repository.MessageFackbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.xiao.message.document.MessageFackbackDocument;
import org.xiao.message.service.MessageFackbackService;

@Service
public class MessageFackbackServiceImpl implements MessageFackbackService {
	@Autowired
    MessageFackbackRepository messageFackbackRepository;

	@Override
	public List<MessageFackbackDocument> findByMessageId(String messageId) {
		// TODO Auto-generated method stub
		Query query=new Query(Criteria.where("messageId").is(messageId));
		
		return 	messageFackbackRepository.findByCondition(query);
	}

	@Override
	public void addSendCount(String messageId) {
		messageFackbackRepository.addSendCount(messageId);
	}

}
