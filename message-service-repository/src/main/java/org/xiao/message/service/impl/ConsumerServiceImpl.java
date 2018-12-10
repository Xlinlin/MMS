package org.xiao.message.service.impl;

import org.xiao.message.bean.query.ConsumerQuery;
import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.repository.ConsumerRepository;
import org.xiao.message.service.ConsumerService;
import org.xiao.message.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
    ConsumerRepository consumerRepository;

	@Override
	public List<ConsumerDocument> findByTopic(String topic) {

		Query query = new Query();
		query.addCriteria(Criteria.where("topic").is(topic));

		return consumerRepository.findByCondition(query);
	}

	@Override
	public int create(ConsumerDocument document) {
		document.setCreateTime(DateUtils.currentTime());
		ConsumerDocument a = consumerRepository.save(document);
		if (a != null)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public int remove(String id) {
		return consumerRepository.delete(id);
	}

	@Override
	public Page findPage(ConsumerQuery consumerQuery)
	{
		Query query = new Query();
		String topic = consumerQuery.getTopic();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(topic))
		{
			criteria.and("topic").is(topic);
		}
		query.addCriteria(criteria);
		PageRequest pageRequest = new PageRequest(consumerQuery.getPageNum(), consumerQuery.getPageSize());
		return consumerRepository.queryByConditionPage(query, pageRequest);
	}

}
