package org.xiao.message.repository.impl;

import org.springframework.stereotype.Service;

import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.repository.ConsumerRepository;
@Service
public class ConsumerRepositoryImpl extends BaseMongoRepositoryImpl<ConsumerDocument, String> implements ConsumerRepository{

	public ConsumerRepositoryImpl(){
		super(ConsumerDocument.class);
	}
	
}
