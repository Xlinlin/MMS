package org.xiao.message.service;

import org.xiao.message.bean.query.SmsQuery;
import org.xiao.message.document.SmsDocument;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 10:02
 * @since JDK 1.8
 */
public interface SmsService
{
    /**
     *[简要描述]:发送消息<br/>
     *[详细描述]:<br/>
     * @param smsQuery :
     * @return com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
     * jun.liu  2018/10/30 - 10:06
     **/
    String sendSms(SmsQuery smsQuery);

    Page findPage(SmsQuery smsQuery);

    int delete(String id);

    Integer getSendNum(String phoneNum);

    List<SmsDocument> seleteAllNoSuccess();

    void updateAll(List<SmsDocument> list);

    /**
     * [简要描述]:日期统计消息总数量<br/>
     * [详细描述]:<br/>
     *
     * @param date : (当天)日期格式yyyy-MM-dd
     * @return int 总数
     * llxiao  2018/11/13 - 15:14
     **/
    Long countByDate(String date);

    /**
     * [简要描述]:状态统计消息总数<br/>
     * [详细描述]:<br/>
     *
     * @param status : 状态，统计未消费、异常、死信数量
     * @return int 总数
     * llxiao  2018/11/13 - 15:16
     **/
    Long countByStatus(Integer status);
}
