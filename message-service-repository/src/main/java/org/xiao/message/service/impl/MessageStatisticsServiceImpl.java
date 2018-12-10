package org.xiao.message.service.impl;

import org.xiao.message.constant.MessageConstant;
import org.xiao.message.document.MessageStatusStatisticsDocument;
import org.xiao.message.document.MessageWeekSendDocument;
import org.xiao.message.repository.MessageStatisticsRepository;
import org.xiao.message.repository.MessageWeekSendRepository;
import org.xiao.message.service.MessageStatisticsService;
import org.xiao.message.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 09:50
 * @since JDK 1.8
 */
@Service
public class MessageStatisticsServiceImpl implements MessageStatisticsService
{
    @Autowired
    private MessageStatisticsRepository messageStatisticsRepository;
    @Autowired
    private MessageWeekSendRepository messageWeekSendRepository;

    @Override
    public void countMessageStatus()
    {
        Query query = null;
        Criteria criteria = null;
        for (int i= MessageConstant.BEFORE_DAY;i>=1;i--)
        {
            query = new Query();
            criteria = new Criteria();
            criteria.and("statisticsDate").is(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,-i));
            query.addCriteria(criteria);
            List<MessageStatusStatisticsDocument> dataList = messageStatisticsRepository.findByCondition(query);
            List<MessageStatusStatisticsDocument> list = messageStatisticsRepository.countMessageStatus(-i);
            if (CollectionUtils.isEmpty(dataList) && CollectionUtils.isNotEmpty(list))
            {
                batchInsertAll(list,-i);
            }
            else if (CollectionUtils.isNotEmpty(dataList) && CollectionUtils.isNotEmpty(list))
            {
                if (dataList.size()>=list.size())
                {
                    update(dataList,list,dataList.size()>=list.size());
                }
                else
                {
                    List<MessageStatusStatisticsDocument> newlist = update(list,dataList,dataList.size()>=list.size());
                    batchInsertAll(newlist,-i);
                }
            }
        }
    }

    private List<MessageStatusStatisticsDocument> update(List<MessageStatusStatisticsDocument> list1,List<MessageStatusStatisticsDocument> list2,boolean flag)
    {
        List<MessageStatusStatisticsDocument> newList = new ArrayList<>();
        for (MessageStatusStatisticsDocument document1 : list1)
        {
            for (MessageStatusStatisticsDocument document2 : list2)
            {
                if (document1.getStatus() == document2.getStatus())
                {
                    int a = document1.getStatusSum() == null ? 0 : document1.getStatusSum();
                    int b = document2.getStatusSum() == null ? 0 : document2.getStatusSum();
                    if (a != b)
                    {
                        Query query = new Query();
                        Update update = new Update();
                        if (flag)
                        {
                            query.addCriteria(Criteria.where("id").in(document1.getId()));
                            update.set("statusSum", document2.getStatusSum()).set("updateTime", DateUtils.currentTime());
                        }
                        else
                        {
                            query.addCriteria(Criteria.where("id").in(document2.getId()));
                            update.set("statusSum", document1.getStatusSum()).set("updateTime", DateUtils.currentTime());
                        }
                        messageStatisticsRepository.update(query, update);
                    }
                    newList.add(document1);
                }
            }
        }
        if (!flag)
        {
            list1 = new ArrayList<>(list1);
            list1.removeAll(newList);
        }
        return list1;
    }

    private void batchInsertAll(List<MessageStatusStatisticsDocument> list,int day)
    {
        for (MessageStatusStatisticsDocument document : list)
        {
            document.setStatisticsDate(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,day));
            document.setCreateTime(DateUtils.currentTime());
        }
        messageStatisticsRepository.insertAll(list);
    }

    @Override
    public void countWeekMessageSendSum()
    {
        Query query = null;
        Criteria criteria = null;
        for (int i=MessageConstant.BEFORE_DAY;i>=1;i--)
        {
            query = new Query();
            criteria = new Criteria();
            criteria.and("statisticsDate").is(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,-i));
            query.addCriteria(criteria);
            List<MessageWeekSendDocument> list = messageWeekSendRepository.findByCondition(query);

            MessageWeekSendDocument sendSumObj = messageWeekSendRepository.countWeekMessageSendSum(null, -i);
            int sendSum = sendSumObj == null ? 0 : sendSumObj.getSendSum() == null ? 0 : sendSumObj.getSendSum();
            MessageWeekSendDocument successSumObj = messageWeekSendRepository
                    .countWeekMessageSendSum(MessageConstant.MESSAGE_SUCCESS_STATUS, -i);
            int successSum = successSumObj == null ? 0 : successSumObj.getSuccessSum() == null ? 0 : successSumObj.getSuccessSum();
            MessageWeekSendDocument failSumObj = messageWeekSendRepository
                    .countWeekMessageSendSum(MessageConstant.MESSAGE_FAIL_STATUS, -i);
            int failSum = failSumObj == null ? 0 : failSumObj.getFailSum() == null ? 0 : failSumObj.getFailSum();
            MessageWeekSendDocument deadSumObj = messageWeekSendRepository
                    .countWeekMessageSendSum(MessageConstant.MESSAGE_DEAD_STATUS, -i);
            int deadSum = deadSumObj == null ? 0 : deadSumObj.getDeadSum() == null ? 0 : deadSumObj.getDeadSum();
            if (CollectionUtils.isEmpty(list))
            {
                MessageWeekSendDocument document = new MessageWeekSendDocument();
                document.setSendSum(sendSum);
                document.setSuccessSum(successSum);
                document.setFailSum(failSum);
                document.setDeadSum(deadSum);
                document.setCreateTime(DateUtils.currentTime());
                document.setStatisticsDate(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,-i));
                messageWeekSendRepository.insert(document);
            }
            else
            {
                MessageWeekSendDocument document = list.get(0);
                boolean flag = true;
                Query query1 = new Query();
                Update update = new Update();
                if (sendSum != (document.getSendSum()==null?0:document.getSendSum()))
                {
                    update.set("sendSum", sendSum);
                    flag = false;
                }
                if (successSum != (document.getSuccessSum()==null?0:document.getSuccessSum()))
                {
                    update.set("successSum", successSum);
                    flag = false;
                }
                if (failSum != (document.getFailSum()==null?0:document.getFailSum()))
                {
                    update.set("failSum", failSum);
                    flag = false;
                }
                if (deadSum != (document.getDeadSum()==null?0:document.getDeadSum()))
                {
                    update.set("deadSum", deadSum);
                    flag = false;
                }
                if (!flag)
                {
                    query1.addCriteria(Criteria.where("id").in(document.getId()));
                    update.set("updateTime", DateUtils.currentTime());
                    messageWeekSendRepository.update(query1,update);
                }
            }

        }
    }

    @Override
    public List<MessageWeekSendDocument> getMessageWeekSend(Date date)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("statisticsDate").gte(DateUtils.getWeekFirstDay(date,DateUtils.DATE_TO_STRING_SHORT_PATTERN)),
                Criteria.where("statisticsDate").lte(DateUtils.getWeekLastDay(date,DateUtils.DATE_TO_STRING_SHORT_PATTERN)));
        Sort sort = new Sort(Sort.Direction.ASC, "statisticsDate");
        query.addCriteria(criteria);
        query.with(sort);
        return messageWeekSendRepository.findByCondition(query);
    }
}
