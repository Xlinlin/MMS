package org.xiao.message.schedule.api.taskmanager.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.schedule.api.taskmanager.bean.dto.TaskConfigDto;
import org.xiao.message.schedule.api.taskmanager.bean.dto.TaskSearchDto;
import org.xiao.message.schedule.api.taskmanager.bean.vo.ResponseVo;
import org.xiao.message.schedule.api.taskmanager.bean.vo.TaskConfigVo;
import org.xiao.message.schedule.api.taskmanager.bean.vo.TaskModelVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [简要描述]:任务管理
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/2
 * @since JDK 1.8
 */
@FeignClient(value = ServiceProduceConstants.OMNI_JOB_SERVICE, url = "http://192.168.206.201:6666")
@RequestMapping("/task/config")
public interface TaskConfigService
{
    @GetMapping("/get/all/model")
    List<TaskModelVo> getAllModel();

    @PostMapping("/list/page")
    MongoPageImp<TaskConfigVo> findTaskConfig(@RequestBody TaskSearchDto searchDto);

    @PostMapping("/save")
    ResponseVo save(@RequestBody TaskConfigDto taskConfigDto);

    /**
     * 启动、停止定时任务：
     *
     * @param ids
     * @param updater
     * @param type 启动1、停止0
     * @return
     */
    @PostMapping("/operate")
    ResponseVo operate(@RequestParam("ids") String ids, @RequestParam("updater") String updater,
            @RequestParam("type") Integer type);

    @GetMapping("/run/now/{id}/{updater}")
    ResponseVo runNow(@PathVariable(name = "id") String id, @PathVariable(name = "updater") String updater);

    @GetMapping("/delete/{id}/{updater}")
    ResponseVo delete(@PathVariable(name = "id") String id, @PathVariable(name = "updater") String updater);

    @PostMapping("/update")
    ResponseVo update(@RequestBody TaskConfigDto taskConfigDto);

}
