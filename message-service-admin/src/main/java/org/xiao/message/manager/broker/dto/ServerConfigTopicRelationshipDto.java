package org.xiao.message.manager.broker.dto;

import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:31
 * @since JDK 1.8
 */
@Data
public class ServerConfigTopicRelationshipDto
{
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    //最大错误次数
    public static final int MAX_ERROR_COUNT = 4;

    private String id;
    private String topic;
    private String serverIp;
    private String createTime;

    //更新时间
    private String updateTime;

    //错误次数
    private Integer errorCount;

    //0正常，1异常
    private Integer status;

    //异常消息
    private String exceptionMsg;

    public static ServerConfigTopicRelationshipDto convertToDto(ServerConfigTopicRelationshipDocument document)
    {
        ServerConfigTopicRelationshipDto dto = null;
        if (document!=null)
        {
            dto = new ServerConfigTopicRelationshipDto();
            dto.setCreateTime(document.getCreateTime());
            dto.setErrorCount(document.getErrorCount());
            dto.setExceptionMsg(document.getExceptionMsg());
            dto.setId(document.getId());
            dto.setServerIp(document.getServerIp());
            dto.setStatus(document.getStatus());
            dto.setTopic(document.getTopic());
            dto.setUpdateTime(document.getUpdateTime());
        }
        return dto;
    }
}
