package org.xiao.message.manager.job;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import org.xiao.message.constant.SmsContent;
import org.xiao.message.document.SmsDocument;
import org.xiao.message.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/7 14:04
 * @since JDK 1.8
 */
@Slf4j
@Component
public class SmsJob
{
    @Autowired
    private SmsService smsService;
    @Autowired
    IAcsClient acsClient;

    @Scheduled(fixedRate = 10000)
    public void updateSmsSendStatus()
    {
        //log.info("更改短信回执状态定时任务开始====");
        List<SmsDocument> list = smsService.seleteAllNoSuccess();
        if (CollectionUtils.isNotEmpty(list))
        {
            log.info("获取等待回执状态的短信成功");
            for (SmsDocument smsDocument : list)
            {
                QuerySendDetailsRequest querySendDetailsRequest = new QuerySendDetailsRequest();
                querySendDetailsRequest.setBizId(smsDocument.getBizId());
                querySendDetailsRequest.setPhoneNumber(smsDocument.getPhoneNum());
                SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
                querySendDetailsRequest.setSendDate(ft.format(new Date()));
                //必填-页大小
                querySendDetailsRequest.setPageSize(10L);
                //必填-当前页码从1开始计数
                querySendDetailsRequest.setCurrentPage(1L);
                boolean flag = false;
                try
                {
                    while (!flag)
                    {
                        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(querySendDetailsRequest);
                        if (SmsContent.STATUS_OK.equals(querySendDetailsResponse.getCode()))
                        {
                            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                            {
                                if (!flag)
                                {
                                    flag = true;
                                }
                                smsDocument.setSendStatus(smsSendDetailDTO.getSendStatus());
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    log.info("获取短信id=" + smsDocument.getId() + " 回执状态失败," + e.getMessage());
                }
            }
            log.info("批量更新短信发送状态开始==");
            smsService.updateAll(list);
            log.info("批量更新短信发送状态成功==");
        }
        //log.info("更改短信回执状态定时任务结束====");
    }
}
