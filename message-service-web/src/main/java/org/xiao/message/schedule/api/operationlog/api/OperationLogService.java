package org.xiao.message.schedule.api.operationlog.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.schedule.api.operationlog.bean.dto.DispatchLogDto;
import org.xiao.message.schedule.api.operationlog.bean.vo.DispatchLogVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = ServiceProduceConstants.OMNI_JOB_SERVICE)
@RequestMapping("/dispatch/log")
public interface OperationLogService
{
    @PostMapping("/list/page")
    public MongoPageImp<DispatchLogVo> list(@RequestBody DispatchLogDto logDto);
}
