package org.xiao.message.manager.broker.dto;

import org.xiao.message.document.ServerConfigDocument;
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
public class ServerConfigDto
{
    //主键   服务ip
    @Id
    private String serverIp;

    //服务组ID
    private String serverGroupId;

    //端口
    private int port;

    //创建时间
    private String createTime;

    //描述
    private String desc;

    //Mongodb库对象转换成dto对象
    public static ServerConfigDto convertDto(ServerConfigDocument serverConfigDocument)
    {
        ServerConfigDto serverConfigDto = new ServerConfigDto();
        serverConfigDto.setServerIp(serverConfigDocument.getServerIp());
        serverConfigDto.setPort(serverConfigDocument.getPort());
        serverConfigDto.setCreateTime(serverConfigDocument.getCreateTime());
        serverConfigDto.setDesc(serverConfigDocument.getDesc());
        serverConfigDto.setServerGroupId(serverConfigDocument.getServerGroupId());
        return serverConfigDto;
    }

}
