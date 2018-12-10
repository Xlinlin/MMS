package org.xiao.message.repository;

import org.xiao.message.document.ServerConfigGroupDocument;

public interface ServerConfigGroupRepository extends BaseMongoRepository<ServerConfigGroupDocument, String>
{

    ServerConfigGroupDocument findMinUserServerGroup();

    void increase(String serverGroupId, boolean isAdd);

}
