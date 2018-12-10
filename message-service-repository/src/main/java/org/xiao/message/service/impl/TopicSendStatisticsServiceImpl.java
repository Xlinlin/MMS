package org.xiao.message.service.impl;

import org.xiao.message.constant.MessageConstant;
import org.xiao.message.document.TopicSendStatisticsDocument;
import org.xiao.message.repository.TopicSendStatisticsRepository;
import org.xiao.message.service.TopicSendStatisticsService;
import org.xiao.message.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @version 1.0, 2018/11/14 14:44
 * @since JDK 1.8
 */
@Service
public class TopicSendStatisticsServiceImpl implements TopicSendStatisticsService
{
    @Autowired
    private TopicSendStatisticsRepository topicSendStatisticsRepository;

    @Override
    public void countTopicSendSum()
    {
        Query query = null;
        Criteria criteria = null;
        for (int i= MessageConstant.BEFORE_DAY;i>=1;i--)
        {
            query = new Query();
            criteria = new Criteria();
            criteria.and("statisticsDate").is(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,-i));
            query.addCriteria(criteria);
            List<TopicSendStatisticsDocument> dataList = topicSendStatisticsRepository.findByCondition(query);
            List<TopicSendStatisticsDocument> list = topicSendStatisticsRepository.countTopicSendSum(-i);
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
                    List<TopicSendStatisticsDocument> newlist = update(list,dataList,dataList.size()>=list.size());
                    batchInsertAll(newlist,-i);
                }
            }
        }
    }

    @Override
    public List<TopicSendStatisticsDocument> listTopicSendStatistics(Date date)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("statisticsDate")
                .is(DateUtils.DateToString(date, DateUtils.DATE_TO_STRING_SHORT_PATTERN)));
        query.addCriteria(criteria);
        return topicSendStatisticsRepository.findByCondition(query);
    }

    private void batchInsertAll(List<TopicSendStatisticsDocument> list, int day)
    {
        for (TopicSendStatisticsDocument document:list)
        {
            document.setStatisticsDate(DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,day));
            document.setCreateTime(DateUtils.currentTime());
        }
        topicSendStatisticsRepository.insertAll(list);
    }

    private List<TopicSendStatisticsDocument> update(List<TopicSendStatisticsDocument> list1,List<TopicSendStatisticsDocument> list2,boolean flag)
    {
        List<TopicSendStatisticsDocument> newList = new ArrayList<>();
        for (TopicSendStatisticsDocument document1:list1)
        {
            for (TopicSendStatisticsDocument document2:list2)
            {
                if ((document1.getTopic()==null?"":document1.getTopic()).equals(document2.getTopic()))
                {
                    int a = document1.getSendSum() == null ? 0 : document1.getSendSum();
                    int b = document2.getSendSum() == null ? 0 : document2.getSendSum();
                    if (a != b)
                    {
                        Query query = new Query();
                        Update update = new Update();
                        if (flag)
                        {
                            query.addCriteria(Criteria.where("id").in(document1.getId()));
                            update.set("sendSum", document2.getSendSum()).set("updateTime", DateUtils.currentTime());
                        }
                        else
                        {
                            query.addCriteria(Criteria.where("id").in(document2.getId()));
                            update.set("sendSum", document1.getSendSum()).set("updateTime", DateUtils.currentTime());
                        }
                        topicSendStatisticsRepository.update(query, update);
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
}
