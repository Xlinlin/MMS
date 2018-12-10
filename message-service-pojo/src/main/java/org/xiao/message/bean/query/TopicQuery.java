package org.xiao.message.bean.query;

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/26 08:59
 * @since JDK 1.8
 */
@Data
public class TopicQuery extends BasisQuery
{
    private String topic;

    private Integer status;

    private String startTime;

    private String stopTime;
}
