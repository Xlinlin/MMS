package org.xiao.message.manager.consumer.dto;

import org.xiao.message.document.ConsumerDocument;
import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/29 14:06
 * @since JDK 1.8
 */
@Data
public class ConsumerDto
{
    //在线状态
    public static final int ONLINE_FLAG = 1;

    private String consumerId;
    private String consumerCode;
    private String topic;
    private String desc;
    private String createTime;//开始时间
    // 是否在线 0离线，1在线线
    private Integer online = 0;

    public static ConsumerDocument convertToConsumerDocument(ConsumerDto consumerDto)
    {
        ConsumerDocument consumerDocument = null;
        if (consumerDto != null)
        {
            consumerDocument = new ConsumerDocument();
            consumerDocument.setConsumerCode(consumerDto.getConsumerCode());
            consumerDocument.setConsumerId(consumerDto.getConsumerId());
            consumerDocument.setCreateTime(consumerDto.getCreateTime());
            consumerDocument.setDesc(consumerDto.getDesc());
            consumerDocument.setTopic(consumerDto.getTopic());
        }
        return consumerDocument;
    }

    public static ConsumerDto convertToTopicDto(ConsumerDocument consumerDocument)
    {
        ConsumerDto consumerDto = null;
        if (consumerDocument != null)
        {
            consumerDto = new ConsumerDto();
            consumerDto.setConsumerCode(consumerDocument.getConsumerCode());
            consumerDto.setConsumerId(consumerDocument.getConsumerId());
            consumerDto.setCreateTime(consumerDocument.getCreateTime());
            consumerDto.setDesc(consumerDocument.getDesc());
            consumerDto.setTopic(consumerDocument.getTopic());
        }
        return consumerDto;
    }
}
