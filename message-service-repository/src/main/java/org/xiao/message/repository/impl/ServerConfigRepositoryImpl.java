package org.xiao.message.repository.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.xiao.message.document.ServerConfigDocument;
import org.xiao.message.repository.ServerConfigRepository;
@Service
public class ServerConfigRepositoryImpl extends BaseMongoRepositoryImpl<ServerConfigDocument, String> implements ServerConfigRepository {

	public ServerConfigRepositoryImpl() {
		super(ServerConfigDocument.class);
	}
	
	@Override
	public ServerConfigDocument findServerConfigByTopic(String topic) {
		
		Query query=new Query();
		query.addCriteria(Criteria.where("topic").in(topic));
		
		List<ServerConfigDocument> documents=findByCondition(query);
		if(documents==null||documents.isEmpty())
		{
			return null;
		}
		return documents.get(0);
	}

	@Override
	public List<ServerConfigDocument> findServerConfigByGroupId(String serverGroupId) {
		
		Query query=new Query();
		query.addCriteria(Criteria.where("serverGroupId").in(serverGroupId));
		return findByCondition(query);
	}

}
