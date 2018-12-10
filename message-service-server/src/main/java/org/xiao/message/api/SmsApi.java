package org.xiao.message.api;

import com.alibaba.fastjson.JSONObject;
import org.xiao.message.bean.query.SmsQuery;
import org.xiao.message.constant.SmsContent;
import org.xiao.message.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 10:39
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/sms")
public class SmsApi
{
    @Autowired
    private SmsService smsService;

    /**
     *[简要描述]:发送短信<br/>
     *[详细描述]:<br/>
     * @param smsQuery :
     * @return void
     * jun.liu  2018/10/30 - 14:11
     **/
    @RequestMapping(value = "/send")
    public String sendSms(@RequestBody SmsQuery smsQuery)
    {
        if (StringUtils.isBlank(smsQuery.getPhoneNum()))
        {
            throw new RuntimeException("参数为空");
        }
        JSONObject jsonObject = new JSONObject();
        Integer num = smsService.getSendNum(smsQuery.getPhoneNum());
        if (num >= SmsContent.SEND_NUM_LIMIT)
        {
            jsonObject.put("status", "error");
            jsonObject.put("message", "已达到获取上限，每个手机号每天最多获取10次");
            return jsonObject.toJSONString();
        }
        return smsService.sendSms(smsQuery);
    }
}
