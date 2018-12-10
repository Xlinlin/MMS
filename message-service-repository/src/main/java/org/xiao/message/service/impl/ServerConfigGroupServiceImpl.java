package org.xiao.message.service.impl;

import org.xiao.message.bean.query.ServerConfigGroupQuery;
import org.xiao.message.document.ServerConfigGroupDocument;
import org.xiao.message.repository.ServerConfigGroupRepository;
import org.xiao.message.service.ServerConfigGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * [简要描述]:服务组管理实现类
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 11:37
 * @since JDK 1.8
 */
@Service
public class ServerConfigGroupServiceImpl implements ServerConfigGroupService
{

    @Autowired
    private ServerConfigGroupRepository serverConfigGroupRepository;

    /**
     * [简要描述]:通过主键查询
     * [详细描述]:<br/>
     *
     * @param id :
     * @return ServerConfigGroupDocument
     * wcpeng  2018/10/26 - 11:37
     **/
    @Override
    public ServerConfigGroupDocument findById(String id)
    {
        return serverConfigGroupRepository.get(id);
    }

    @Override
    public Page<ServerConfigGroupDocument> findPage(ServerConfigGroupQuery query)
    {
        Query mongodbQuery = new Query();
        String groupName = query.getGroupName();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(groupName))
        {
            criteria = Criteria.where("groupName").is(groupName);
        }
        mongodbQuery.addCriteria(criteria);
        //创建时间降序
        Sort.Direction direction = query.isDirection() ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = new Sort(direction, "createTime");
        PageRequest pageRequest = new PageRequest(query.getPageNum(), query.getPageSize());
        return serverConfigGroupRepository.queryByConditionPage(mongodbQuery, pageRequest, sort);
    }

    /**
     * [简要描述]:根据主键删除
     * [详细描述]:<br/>
     *
     * @param id :
     * @return int
     * wcpeng  2018/10/26 - 17:09
     **/
    @Override
    public Boolean delById(String id)
    {
        return serverConfigGroupRepository.delete(id) > 0;
    }

}
