package org.xiao.message.service.impl;

import org.xiao.message.bean.query.RelateshipQuery;
import org.xiao.message.repository.ServerConfigTopicRelationshipRepository;
import org.xiao.message.service.ServerConfigTopicRelationshipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:43
 * @since JDK 1.8
 */
@Service
public class ServerConfigTopicRelationshipServiceImpl implements ServerConfigTopicRelationshipService
{
    @Autowired
    private ServerConfigTopicRelationshipRepository serverConfigTopicRelationshipRepository;

    @Override
    public Page findPage(RelateshipQuery relateshipQuery)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(relateshipQuery.getTopic()))
        {
            criteria.and("topic").regex("^.*"+ relateshipQuery.getTopic() + ".*$");
        }
        if (StringUtils.isNotBlank(relateshipQuery.getServerIp()))
        {
            criteria.and("serverIp").is(relateshipQuery.getServerIp());
        }
        Sort.Direction direction = relateshipQuery.isDirection() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(direction, relateshipQuery.getSortFiled());
        query.addCriteria(criteria);

        PageRequest pageRequest = new PageRequest(relateshipQuery.getPageNum(), relateshipQuery.getPageSize());
        return serverConfigTopicRelationshipRepository.queryByConditionPage(query, pageRequest, sort);
    }
}
