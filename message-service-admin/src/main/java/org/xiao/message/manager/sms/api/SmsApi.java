package org.xiao.message.manager.sms.api;

import com.alibaba.fastjson.JSONObject;
import org.xiao.message.bean.query.SmsQuery;
import org.xiao.message.constant.SmsContent;
import org.xiao.message.document.SmsDocument;
import org.xiao.message.manager.sms.dto.SmsViewDto;
import org.xiao.message.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *[简要描述]:分页获取<br/>
     *[详细描述]:<br/>
     * @param smsQuery :
     * @return org.springframework.data.domain.Page<SmsViewDto>
     * jun.liu  2018/10/31 - 13:50
     **/
    @RequestMapping(value = "/pageSms")
    public Page<SmsViewDto> pageSms(@RequestBody SmsQuery smsQuery)
    {
        Page page = smsService.findPage(smsQuery);
        List<SmsDocument> list = page.getContent();
        List<SmsViewDto> dtoList = new ArrayList<>();
        for (SmsDocument smsDocument:list)
        {
            dtoList.add(SmsViewDto.convertToSmsViewDto(smsDocument));
        }
        return new PageImpl<>(dtoList,new PageRequest(smsQuery.getPageNum()-1,smsQuery.getPageSize()),page.getTotalElements());
    }
}
