package org.xiao.message.service;

import org.xiao.message.bean.query.ConsumerQuery;
import org.xiao.message.document.ConsumerDocument;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConsumerService {
	
	List<ConsumerDocument> findByTopic(String topic);
	
	int create(ConsumerDocument document);

	int remove(String id);

	Page findPage(ConsumerQuery consumerQuery);
}
