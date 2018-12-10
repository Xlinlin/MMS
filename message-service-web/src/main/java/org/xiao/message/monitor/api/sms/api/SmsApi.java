package org.xiao.message.monitor.api.sms.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.sms.query.SmsQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.monitor.api.sms.dto.SmsViewDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/7
 * @since JDK 1.8
 */
@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping(value = "/sms")
public interface SmsApi
{
    @RequestMapping(value = "/send")
    String sendSms(@RequestBody SmsQuery smsQuery);

    @RequestMapping(value = "/pageSms")
    MongoPageImp<SmsViewDto> pageSms(@RequestBody SmsQuery smsQuery);
}
