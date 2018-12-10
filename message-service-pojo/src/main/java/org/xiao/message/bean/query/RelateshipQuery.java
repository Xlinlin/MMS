package org.xiao.message.bean.query;

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:28
 * @since JDK 1.8
 */
@Data
public class RelateshipQuery extends BasisQuery
{
    private String topic;

    private String serverIp;
    //默认时间升降序 true升序false降序
    private boolean direction = false;
    //排序字段
    private String sortFiled = "createTime";
}
