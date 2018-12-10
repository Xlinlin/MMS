package org.xiao.message.service.impl;

import org.xiao.message.bean.dto.ServerConfigDto;
import org.xiao.message.bean.dto.ServerConfigGroupDto;
import org.xiao.message.bean.query.ServerConfigQuery;
import org.xiao.message.document.ServerConfigDocument;
import org.xiao.message.document.ServerConfigGroupDocument;
import org.xiao.message.repository.ServerConfigGroupRepository;
import org.xiao.message.repository.ServerConfigRepository;
import org.xiao.message.repository.ServerConfigTopicRelationshipRepository;
import org.xiao.message.service.ServerConfigService;
import org.xiao.message.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServerConfigServiceImpl implements ServerConfigService
{

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Autowired
    private ServerConfigTopicRelationshipRepository serverConfigTopicRelationshipRepository;

    @Autowired
    private ServerConfigGroupRepository serverConfigGroupRepository;

    /**
     * [简要描述]:自增长/减<br/>
     * [详细描述]:<br/>
     *
     * @param serverGroupId :
     * @param isAdd : true赠 false减
     * llxiao  2018/11/8 - 16:15
     **/
    @Override
    public void increase(String serverGroupId, boolean isAdd)
    {
        serverConfigGroupRepository.increase(serverGroupId, isAdd);
    }

    /**
     * [简要描述]:通过主键查询
     * [详细描述]:<br/>
     *
     * @param serverIp :
     * @return ServerConfigDocument
     * wcpeng  2018/10/26 - 15:41
     **/
    @Override
    public ServerConfigDocument findById(String serverIp)
    {
        return serverConfigRepository.get(serverIp);
    }

    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return org.springframework.data.domain.Page<ServerConfigDocument>
     * wcpeng  2018/10/26 - 15:46
     **/
    @Override
    public Page<ServerConfigDocument> findPage(ServerConfigQuery query)
    {
        Query mongodbQuery = new Query();
        String serverIp = query.getServerIp();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(serverIp))
        {
            criteria = Criteria.where("serverIp").is(serverIp);
        }
        String serverGroupId = query.getServerGroupId();
        if (StringUtils.isNotBlank(serverGroupId))
        {
            criteria.and("serverGroupId").is(serverGroupId);
        }
        mongodbQuery.addCriteria(criteria);

        //创建时间降序
        Sort.Direction direction = query.isDirection() ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = new Sort(direction, "createTime");
        PageRequest pageRequest = new PageRequest(query.getPageNum(), query.getPageSize());
        return serverConfigRepository.queryByConditionPage(mongodbQuery, pageRequest, sort);
    }

    @Override
    public List<ServerConfigDocument> findServerConfigByGroupId(String serverGroupId)
    {
        return serverConfigRepository.findServerConfigByGroupId(serverGroupId);
    }

    @Override
    public ServerConfigDocument findKafkaServerConfigByTopic(String topic)
    {
        return serverConfigRepository.findServerConfigByTopic(topic);
    }

    @Override
    public int save(ServerConfigDto configDto)
    {

        ServerConfigDocument document = new ServerConfigDocument();
        document.setCreateTime(DateUtils.currentTime());
        document.setServerIp(configDto.getServerIp());
        document.setPort(configDto.getPort());
        document.setServerGroupId(configDto.getServerGroupId());
        document.setDesc(configDto.getDesc());
        serverConfigRepository.save(document);

        return 1;
    }

    @Override
    public int remove(String serverIp)
    {
        serverConfigRepository.delete(serverIp);
        return 0;
    }

    /**
     * [简要描述]:关联topic和ServerConfig
     * [详细描述]:server_config_topic_relationship_document
     *
     * @param topic :
     * @param serverIp :
     * @return int
     * wcpeng  2018/10/29 - 10:35
     **/
    @Override
    public int bindServerConfigTopic(String topic, String serverIp)
    {
        return serverConfigTopicRelationshipRepository.bindServerConfigTopic(topic, serverIp);
    }

    /**
     * [简要描述]:通过serverIp获取对应的topic
     * [详细描述]:<br/>
     *
     * @param serverIp :
     * @return java.util.List<java.lang.String>
     * wcpeng  2018/10/29 - 10:37
     **/
    @Override
    public List<String> findTopicByServerIp(String serverIp)
    {

        return serverConfigTopicRelationshipRepository.findTopicByServerIp(serverIp);
    }

    @Override
    public List<ServerConfigDocument> distributionServerConfig()
    {
        //查询使用最少的服务服务器组
        ServerConfigGroupDocument document = serverConfigGroupRepository.findMinUserServerGroup();
        if (document == null)
        {
            return null;
        }
        return serverConfigRepository.findServerConfigByGroupId(document.getId());
    }

    @Override
    public int createServerConfigGroup(ServerConfigGroupDto dto)
    {
        ServerConfigGroupDocument document = new ServerConfigGroupDocument();
        document.setId(UUID.randomUUID().toString());
        document.setCreateTime(DateUtils.currentTime());
        document.setUpdateTime(DateUtils.currentTime());
        document.setDesc(dto.getDesc());
        document.setGroupName(dto.getGroupName());
        serverConfigGroupRepository.save(document);

        return 1;
    }

    /**
     * [简要描述]:修改服务组
     * [详细描述]:<br/>
     *
     * @param id :
     * @param dto :
     * @return int
     * wcpeng  2018/10/31 - 16:19
     **/
    @Override
    public int updateServerConfigGroup(String id, ServerConfigGroupDto dto)
    {

        ServerConfigGroupDocument document = serverConfigGroupRepository.get(id);
        if (document == null)
        {
            throw new RuntimeException();
        }

        document.setUpdateTime(DateUtils.currentTime());
        document.setDesc(dto.getDesc());
        document.setGroupName(dto.getGroupName());
        serverConfigGroupRepository.save(document);

        return 1;
    }

    /**
     * [简要描述]:修改服务
     * [详细描述]:<br/>
     *
     * @param id :
     * @param dto :
     * @return int
     * wcpeng  2018/10/31 - 16:16
     **/
    @Override
    public int updateServerConfig(String id, ServerConfigDto dto)
    {
        ServerConfigDocument document = serverConfigRepository.get(id);
        if (document == null)
        {
            throw new RuntimeException();
        }
        document.setDesc(dto.getDesc());
        document.setPort(dto.getPort());
        document.setServerGroupId(dto.getServerGroupId());
        serverConfigRepository.save(document);
        return 1;
    }

    @Override
    public int removeServerConfigGroup(String id)
    {
        return serverConfigGroupRepository.delete(id);
    }

}
