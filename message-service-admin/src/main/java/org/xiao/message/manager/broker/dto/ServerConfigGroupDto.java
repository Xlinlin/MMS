package org.xiao.message.manager.broker.dto;

import org.xiao.message.document.ServerConfigGroupDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * [简要描述]:服务组Dto
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 09:45
 * @since JDK 1.8
 */
@Data
public class ServerConfigGroupDto
{
    @Id
    //主键
    private String id;

    //组名称
    private String groupName;

    //描述
    private String desc;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //当前使用数量
    private int currentUseCount;

    //Mongodb库对象转换成dto对象
    public static ServerConfigGroupDto convertDto(ServerConfigGroupDocument serverConfigGroupDocument)
    {
        ServerConfigGroupDto serverConfigGroupDto = new ServerConfigGroupDto();
        serverConfigGroupDto.setId(serverConfigGroupDocument.getId());
        serverConfigGroupDto.setGroupName(serverConfigGroupDocument.getGroupName());
        serverConfigGroupDto.setCreateTime(serverConfigGroupDocument.getCreateTime());
        serverConfigGroupDto.setUpdateTime(serverConfigGroupDocument.getUpdateTime());
        serverConfigGroupDto.setDesc(serverConfigGroupDocument.getDesc());
        serverConfigGroupDto.setCurrentUseCount(serverConfigGroupDocument.getCurrentUseCount());
        return serverConfigGroupDto;
    }

}
