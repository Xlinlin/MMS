package org.xiao.message.monitor.api.sms.query;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 14:45
 * @since JDK 1.8
 */
@Data
public class SmsQuery extends BasisQuery
{
    private String phoneNum;

    private String sendDate;

    private String sendStatus;

    //排序字段(sendDate,receiveDate)
    private String sortFiled;

    //默认时间升降序 true升序false降序
    private boolean direction=false;

    private String outId;
}
