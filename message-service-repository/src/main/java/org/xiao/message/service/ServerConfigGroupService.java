package org.xiao.message.service;

import org.xiao.message.bean.query.ServerConfigGroupQuery;
import org.xiao.message.document.ServerConfigGroupDocument;
import org.springframework.data.domain.Page;

/**
 * [简要描述]:服务组管理service
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 10:45
 * @since JDK 1.8
 */
public interface ServerConfigGroupService
{
    /**
     * [简要描述]:通过主键查询
     * [详细描述]:<br/>
     *
     * @param id :
     * @return ServerConfigGroupDocument
     * wcpeng  2018/10/26 - 11:37
     **/
    ServerConfigGroupDocument findById(String id);

    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return org.springframework.data.domain.Page<ServerConfigGroupDocument>
     * wcpeng  2018/10/26 - 11:38
     **/
    Page<ServerConfigGroupDocument> findPage(ServerConfigGroupQuery query);

    /**
     * [简要描述]:根据主键删除
     * [详细描述]:<br/>
     *
     * @return int
     * wcpeng  2018/10/26 - 17:08
     **/
    Boolean delById(String id);

}
