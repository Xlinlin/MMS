package org.xiao.message.service;

import org.xiao.message.bean.dto.ServerConfigDto;
import org.xiao.message.bean.dto.ServerConfigGroupDto;
import org.xiao.message.bean.query.ServerConfigQuery;
import org.xiao.message.document.ServerConfigDocument;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServerConfigService
{

    ServerConfigDocument findKafkaServerConfigByTopic(String topic);

    int save(ServerConfigDto configDto);

    int remove(String serverIp);

    int bindServerConfigTopic(String topic, String serverIp);

    List<String> findTopicByServerIp(String serverIp);

    int createServerConfigGroup(ServerConfigGroupDto dto);

    int updateServerConfigGroup(String id, ServerConfigGroupDto dto);

    int removeServerConfigGroup(String id);

    List<ServerConfigDocument> distributionServerConfig();

    List<ServerConfigDocument> findServerConfigByGroupId(String serverGroupId);

    /**
     * [简要描述]:自增长/减<br/>
     * [详细描述]:<br/>
     *
     * @param serverGroupId : 组ID
     * @param isAdd : true添加
     * @return void
     * llxiao  2018/11/8 - 16:16
     **/
    void increase(String serverGroupId, boolean isAdd);

    /**
     * [简要描述]:通过主键查询
     * [详细描述]:<br/>
     *
     * @param serverIp :
     * @return ServerConfigDocument
     * wcpeng  2018/10/26 - 15:41
     **/
    ServerConfigDocument findById(String serverIp);

    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return org.springframework.data.domain.Page<ServerConfigDocument>
     * wcpeng  2018/10/26 - 15:46
     **/
    Page<ServerConfigDocument> findPage(ServerConfigQuery query);

    /**
     * [简要描述]:修改服务
     * [详细描述]:<br/>
     *
     * @param id :
     * @param dto :
     * @return int
     * wcpeng  2018/10/31 - 16:16
     **/
    int updateServerConfig(String id, ServerConfigDto dto);
}
