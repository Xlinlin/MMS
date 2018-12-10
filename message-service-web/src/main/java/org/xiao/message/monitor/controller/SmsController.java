package org.xiao.message.monitor.controller;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @since JDK 1.8
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.sms.api.SmsApi;
import org.xiao.message.monitor.api.sms.dto.SmsViewDto;
import org.xiao.message.monitor.api.sms.query.SmsQuery;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/7
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/monitor/sms/sms_mgt")
public class SmsController
{

    @Autowired
    private SmsApi smsApi;

    @RequestMapping(value = "/send")
    public String sendSms(@RequestBody SmsQuery smsQuery)
    {
        return smsApi.sendSms(smsQuery);
    }

    @RequestMapping(value = "/pageSms")
    public MongoPageImp<SmsViewDto> pageSms(@RequestBody SmsQuery smsQuery)
    {
        return smsApi.pageSms(smsQuery);
    }
}
