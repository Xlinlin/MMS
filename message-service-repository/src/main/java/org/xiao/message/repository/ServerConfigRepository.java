package org.xiao.message.repository;

import java.util.List;

import org.xiao.message.document.ServerConfigDocument;

public interface ServerConfigRepository extends BaseMongoRepository<ServerConfigDocument, String>
{
    ServerConfigDocument findServerConfigByTopic(String topic);

    /**
     * [简要描述]:服务组ID查询对应发服务器<br/>
     * [详细描述]:<br/>
     *
     * @param serverGroupId :
     * @return java.util.List<ServerConfigDocument>
     * llxiao  2018/11/2 - 9:38
     **/
    List<ServerConfigDocument> findServerConfigByGroupId(String serverGroupId);
}
