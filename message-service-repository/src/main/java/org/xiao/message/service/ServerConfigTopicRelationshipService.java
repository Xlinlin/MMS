package org.xiao.message.service;

import org.xiao.message.bean.query.RelateshipQuery;
import org.springframework.data.domain.Page;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:42
 * @since JDK 1.8
 */
public interface ServerConfigTopicRelationshipService
{

    /**
     *[简要描述]:分页获取<br/>
     *[详细描述]:<br/>
     * @param relateshipQuery :
     * @return org.springframework.data.domain.Page
     * jun.liu  2018/11/5 - 15:46
     **/
    Page findPage(RelateshipQuery relateshipQuery);
}
