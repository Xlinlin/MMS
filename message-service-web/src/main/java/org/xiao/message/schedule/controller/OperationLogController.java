package org.xiao.message.schedule.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.schedule.api.operationlog.api.OperationLogService;
import org.xiao.message.schedule.api.operationlog.bean.dto.DispatchLogDto;
import org.xiao.message.schedule.api.operationlog.bean.vo.DispatchLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/schedule/operation/log")
public class OperationLogController
{
    @Autowired
    private OperationLogService operationLogService;
    /**
     * [简要描述]:获取分页调度日志列表
     * [详细描述]:<br/>
      * @param logDto :
     * @return MongoPageImp
     * xmli  2018/11/9 - 17:20
     **/
    @RequestMapping("/operationLogList")
    public MongoPageImp<DispatchLogVo> list(@RequestBody DispatchLogDto logDto){
        MongoPageImp<DispatchLogVo> oprList=operationLogService.list(logDto);
        return oprList;
    }
}
