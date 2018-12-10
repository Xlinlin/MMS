package org.xiao.message.manager.broker;

import com.alibaba.fastjson.JSON;
import org.xiao.message.bean.query.ServerConfigGroupQuery;
import org.xiao.message.bean.query.ServerConfigQuery;
import org.xiao.message.document.ServerConfigDocument;
import org.xiao.message.document.ServerConfigGroupDocument;
import org.xiao.message.manager.broker.dto.ServerConfigDto;
import org.xiao.message.manager.broker.dto.ServerConfigGroupDto;
import org.xiao.message.service.ServerConfigGroupService;
import org.xiao.message.service.ServerConfigService;
import org.xiao.MessageAdminApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:broker服务测试
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 16:20
 * @since JDK 1.8
 */
public class ServerConfigTest extends MessageAdminApplicationTest
{

    @Autowired
    private ServerConfigGroupService serverConfigGroupService;

    @Autowired
    private ServerConfigService serverConfigService;

    @Test
    public void testFindById()
    {
        String id = "9cbbcf27-72df-4690-8c92-4a55b19d1b7b";
        ServerConfigGroupDocument serverConfigGroupDocument = serverConfigGroupService.findById(id);
        System.out.print(serverConfigGroupDocument);
    }

    @Test
    public void testFindById2()
    {
        String id = "192.168.206.208";
        ServerConfigDocument serverConfigDocument = serverConfigService.findById(id);
        System.out.print(serverConfigDocument);
    }

    @Test
    public void testFindPage()
    {
        ServerConfigGroupQuery query = new ServerConfigGroupQuery();
        query.setDirection(true);
        query.setGroupName("业务中台组");
        Page page = serverConfigGroupService.findPage(query);
        System.out.println(page.getTotalPages());
        System.out.println(JSON.toJSONString(page));

        List<ServerConfigGroupDocument> list = page.getContent();
        List<ServerConfigGroupDto> sourceDtos = new ArrayList<>();
        for (ServerConfigGroupDocument document : list)
        {
            sourceDtos.add(ServerConfigGroupDto.convertDto(document));
        }
        Page<ServerConfigGroupDto> dtos = new PageImpl<>(sourceDtos, new PageRequest(
                query.getPageNum() - 1, query.getPageSize()), page.getTotalElements());
        System.out.println(JSON.toJSONString(dtos));
    }

    @Test
    public void testFindPage2()
    {
        ServerConfigQuery query = new ServerConfigQuery();
        query.setDirection(true);
        query.setServerGroupId("9cbbcf27-72df-4690-8c92-4a55b19d1b7b");
        query.setServerIp("192.168.206.208");
        Page page = serverConfigService.findPage(query);
        System.out.println(page.getTotalPages());
        System.out.println(JSON.toJSONString(page));

        List<ServerConfigDocument> list = page.getContent();
        List<ServerConfigDto> sourceDtos = new ArrayList<>();
        for (ServerConfigDocument document : list)
        {
            sourceDtos.add(ServerConfigDto.convertDto(document));
        }
        Page<ServerConfigDto> dtos = new PageImpl<>(sourceDtos, new PageRequest(
                query.getPageNum() - 1, query.getPageSize()), page.getTotalElements());
        System.out.println(JSON.toJSONString(dtos));
    }

    @Test
    public void delById()
    {
        Boolean i = serverConfigGroupService.delById("5bd2da80ac34df3af400136c");
        System.out.print(i);
    }

    @Test
    public void delById2()
    {
        System.out.print(serverConfigService.remove("172.16.80.172"));
    }

    @Test
    public void findServerConfigByTopic()
    {
        ServerConfigDocument data = serverConfigService.findKafkaServerConfigByTopic("wcpeng1");
        System.out.print(data);
    }

    /**
     * [简要描述]:
     * [详细描述]:表server_config_topic_relationship_document
     *
     * @return void
     * wcpeng  2018/10/29 - 10:30
     **/
    @Test
    public void findTopicByServerIp()
    {
        List<String> ips = serverConfigService.findTopicByServerIp("172.16.80.148");
        System.out.print(ips);
    }

    @Test
    public void distributionServerConfig()
    {
        List<ServerConfigDocument> data = serverConfigService.distributionServerConfig();
        System.out.print(data);
    }

    /**
     * [简要描述]:currentUser自增长1
     * [详细描述]:server_config_group_document
     *
     * @return void
     * wcpeng  2018/10/29 - 11:52
     **/
    @Test
    public void increase()
    {
        serverConfigService.increase("5bd67d6cac34df56a5002af3", true);
    }
}
