package org.xiao.message.monitor.api.message.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.message.bean.dto.MessageSourceDto;
import org.xiao.message.monitor.api.message.bean.dto.MessageTraceLogDto;
import org.xiao.message.monitor.api.message.bean.query.MessageQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;

import java.util.List;

@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/message/manager")
public interface MessageService
{

    /**
     * [简要描述]:分页查询<br/>
     * [详细描述]:<br/>
     *
     * @param messageQuery : 分页条件
     * @return
     * ttyang  2018/10/31 - 10:56
     **/
    @RequestMapping("/findPage")
    MongoPageImp<MessageSourceDto> findPage(@RequestBody MessageQuery messageQuery);

    /**
     * [简要描述]:消息ID追踪消息<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     **/
    @RequestMapping("/trail")
    List<MessageTraceLogDto> trailMessage(@RequestParam("messageId") String messageId);
}
