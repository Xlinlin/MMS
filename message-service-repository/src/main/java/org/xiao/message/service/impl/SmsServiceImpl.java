package org.xiao.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.xiao.message.bean.query.SmsQuery;
import org.xiao.message.constant.SmsContent;
import org.xiao.message.document.SmsDocument;
import org.xiao.message.repository.SmsRepository;
import org.xiao.message.service.SmsService;
import org.xiao.message.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 10:08
 * @since JDK 1.8
 */
@Service
@ConditionalOnProperty(name = "ali.sms.access_key_id")
public class SmsServiceImpl implements SmsService
{
    @Autowired
    IAcsClient acsClient;
    @Autowired
    SmsRepository smsRepository;

    @Override
    public String sendSms(SmsQuery smsQuery)
    {
        boolean flag = false;

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(smsQuery.getPhoneNum());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SmsContent.SIGN_NAME);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(SmsContent.SIGN_TEMPLETE_CODE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company", "公司名称");
        jsonObject.put("cardNumber", "m12312091");
        jsonObject.put("createTime", DateUtils.currentTime());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(jsonObject.toJSONString());

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(smsQuery.getOutId());
        try
        {
            SendSmsResponse response = acsClient.getAcsResponse(request);

            if (StringUtils.isNotBlank(response.getCode()) && SmsContent.STATUS_OK.equals(response.getCode()))
            {
                QuerySendDetailsRequest querySendDetailsRequest = new QuerySendDetailsRequest();
                querySendDetailsRequest.setBizId(response.getBizId());
                querySendDetailsRequest.setPhoneNumber(smsQuery.getPhoneNum());
                SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
                querySendDetailsRequest.setSendDate(ft.format(new Date()));
                //必填-页大小
                querySendDetailsRequest.setPageSize(10L);
                //必填-当前页码从1开始计数
                querySendDetailsRequest.setCurrentPage(1L);

                List<SmsDocument> list = new ArrayList<>();
                while (!flag)
                {
                    QuerySendDetailsResponse querySendDetailsResponse = acsClient
                            .getAcsResponse(querySendDetailsRequest);
                    if (SmsContent.STATUS_OK.equals(querySendDetailsResponse.getCode()))
                    {
                        SmsDocument smsDocument = null;
                        for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse
                                .getSmsSendDetailDTOs())
                        {
                            if (!flag)
                            {
                                flag = true;
                            }
                            smsDocument = new SmsDocument();
                            smsDocument.setBizId(response.getBizId());
                            smsDocument.setContent(smsSendDetailDTO.getContent());
                            smsDocument.setId(UUID.randomUUID().toString());
                            smsDocument.setOutId(smsSendDetailDTO.getOutId());
                            smsDocument.setSendDate(smsSendDetailDTO.getSendDate());
                            smsDocument.setPhoneNum(smsSendDetailDTO.getPhoneNum());
                            smsDocument.setSendStatus(smsSendDetailDTO.getSendStatus());
                            list.add(smsDocument);
                        }
                        if (CollectionUtils.isNotEmpty(list))
                        {
                            smsRepository.insertAll(list);
                        }
                    }
                }
            }
            jsonObject = new JSONObject();
            jsonObject.put("status", "OK");
            return jsonObject.toJSONString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jsonObject = new JSONObject();
            jsonObject.put("status", "error");
            jsonObject.put("message", "发送短信失败");
            return jsonObject.toJSONString();
        }
    }

    @Override
    public Page findPage(SmsQuery smsQuery)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(smsQuery.getPhoneNum()))
        {
            criteria.and("phoneNum").is(smsQuery.getPhoneNum());
        }
        if (StringUtils.isNotBlank(smsQuery.getSendDate()))
        {
            criteria.and("sendDate").regex("^.*" + smsQuery.getSendDate() + ".*$");
        }
        if (smsQuery.getSendStatus() != null)
        {
            criteria.and("sendStatus").is(smsQuery.getSendStatus());
        }
        Sort sort = null;
        if (StringUtils.isNotBlank(smsQuery.getSortFiled()))
        {
            Sort.Direction direction = smsQuery.isDirection() ? Sort.Direction.ASC : Sort.Direction.DESC;
            if (SmsContent.SEND_DATE.equals(smsQuery.getSortFiled()))
            {

                sort = new Sort(direction, SmsContent.SEND_DATE);
            }
        }
        query.addCriteria(criteria);

        PageRequest pageRequest = new PageRequest(smsQuery.getPageNum(), smsQuery.getPageSize());
        return smsRepository.queryByConditionPage(query, pageRequest, sort);
    }

    @Override
    public int delete(String id)
    {
        return smsRepository.delete(id);
    }

    @Override
    public Integer getSendNum(String phoneNum)
    {
        if (StringUtils.isBlank(phoneNum))
        {
            throw new RuntimeException("手机号码不能为空");
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("phoneNum").is(phoneNum);
        criteria.and("sendDate")
                .regex("^.*" + DateUtils.currentFormatDate(DateUtils.DATE_TO_STRING_SHORT_PATTERN) + ".*$");
        query.addCriteria(criteria);
        List<SmsDocument> list = smsRepository.findByCondition(query);
        if (CollectionUtils.isNotEmpty(list))
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public List<SmsDocument> seleteAllNoSuccess()
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("sendStatus").is(SmsContent.SEND_STATUS);
        query.addCriteria(criteria);
        return smsRepository.findByCondition(query);
    }

    @Override
    public void updateAll(List<SmsDocument> list)
    {
        List<String> ids = new ArrayList<>();
        for (SmsDocument smsDocument : list)
        {
            ids.add(smsDocument.getId());
        }
        Query query = new Query(Criteria.where("id").in(ids));
        Update update = new Update();
        update.set("sendStatus", SmsContent.SEND_STATUS_SUCCESS);
        smsRepository.batchUpdate(query, update);
    }

    /**
     * [简要描述]:日期统计消息总数量<br/>
     * [详细描述]:<br/>
     *
     * @param date : (当天)日期格式yyyy-MM-dd
     * @return int 总数
     * llxiao  2018/11/13 - 15:14
     **/
    @Override
    public Long countByDate(String date)
    {
        return this.smsRepository.countByDate(date);
    }

    /**
     * [简要描述]:状态统计消息总数<br/>
     * [详细描述]:<br/>
     *
     * @param status : 状态，统计未消费、异常、死信数量
     * @return int 总数
     * llxiao  2018/11/13 - 15:16
     **/
    @Override
    public Long countByStatus(Integer status)
    {
        return this.smsRepository.countByStatus(status);
    }
}
