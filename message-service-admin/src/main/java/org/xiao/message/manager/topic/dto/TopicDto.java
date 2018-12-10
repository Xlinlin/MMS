package org.xiao.message.manager.topic.dto;

import org.xiao.message.document.TopicDocument;
import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/26 08:49
 * @since JDK 1.8
 */
@Data
public class TopicDto
{
    public static int STOP = 0;

    public static int START = 1;

    private String id;

    private String topic;

    private String desc;

    private String stopTime;//结束时间

    private Integer status = 0;//状态0-未使用,1-使用中,2-已停用

    private String createTime;//创建时间

    private String startTime;//开始时间

    private String createIp;//开始IP

    private String serverGroupId;//所属服务组ID

    private String topicType;

    public static TopicDto convertToTopicDto(TopicDocument topicDocument)
    {
        TopicDto topicDto = null;
        if (topicDocument != null)
        {
            topicDto = new TopicDto();
            topicDto.setCreateTime(topicDocument.getCreateTime());
            topicDto.setDesc(topicDocument.getDesc());
            topicDto.setId(topicDocument.getId());
            topicDto.setStartTime(topicDocument.getStartTime());
            topicDto.setStatus(topicDocument.getStatus());
            topicDto.setStopTime(topicDocument.getStopTime());
            topicDto.setTopic(topicDocument.getTopic());
            topicDto.setTopicType(topicDocument.getTopicType());
            topicDto.setCreateIp(topicDocument.getCreateIp());
            topicDto.setServerGroupId(topicDocument.getServerGroupId());
        }
        return topicDto;
    }

    public static TopicDocument convertToTopicDocument(TopicDto topicDto)
    {
        TopicDocument topicDocument = null;
        if (topicDto != null)
        {
            topicDocument = new TopicDocument();
            topicDocument.setCreateTime(topicDto.getCreateTime());
            topicDocument.setDesc(topicDto.getDesc());
            topicDocument.setId(topicDto.getId());
            topicDocument.setStartTime(topicDto.getStartTime());
            topicDocument.setCreateIp(topicDto.getCreateIp());
            topicDocument.setStatus(topicDto.getStatus());
            topicDocument.setStopTime(topicDto.getStopTime());
            topicDocument.setTopic(topicDto.getTopic());
            topicDocument.setTopicType(topicDto.getTopicType());
            topicDocument.setServerGroupId(topicDto.getServerGroupId());
        }
        return topicDocument;
    }
}
