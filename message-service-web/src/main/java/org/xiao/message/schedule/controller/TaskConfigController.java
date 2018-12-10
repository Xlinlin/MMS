package org.xiao.message.schedule.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.schedule.api.taskmanager.api.TaskConfigService;
import org.xiao.message.schedule.api.taskmanager.bean.dto.TaskConfigDto;
import org.xiao.message.schedule.api.taskmanager.bean.dto.TaskSearchDto;
import org.xiao.message.schedule.api.taskmanager.bean.vo.ResponseVo;
import org.xiao.message.schedule.api.taskmanager.bean.vo.TaskConfigVo;
import org.xiao.message.schedule.api.taskmanager.bean.vo.TaskModelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [简要描述]:任务调度中心-任务管理
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/2
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/schedule/taskmanager/task_manager")
public class TaskConfigController {
    @Autowired
    private TaskConfigService taskConfigService;

    @GetMapping("/get/all/model")
    public List<TaskModelVo> getAllModel(){
        return taskConfigService.getAllModel();
    }

    @PostMapping("/list/page")
    public MongoPageImp<TaskConfigVo> findTaskConfig(@RequestBody TaskSearchDto searchDto){
        return taskConfigService.findTaskConfig(searchDto);
    }

    @PostMapping("/save")
    public ResponseVo save(@RequestBody TaskConfigDto taskConfigDto){
        return taskConfigService.save(taskConfigDto);
    }

    /**
     * 启动、停止定时任务：
     * @param ids
     * @param updater
     * @param type 启动1、停止0
     * @return
     */
    @PostMapping("/operate")
    public ResponseVo operate(@RequestParam("ids") String ids, @RequestParam("updater") String updater, @RequestParam("type") Integer type){
        updater="admin";
        return taskConfigService.operate(ids,updater,type);
    }

    @GetMapping("/run/now/{id}/{updater}")
    public ResponseVo runNow(@PathVariable(name = "id") String id, @PathVariable(name = "updater") String updater){
        return taskConfigService.runNow(id,updater);
    }

    @GetMapping("/delete/{id}/{updater}")
    public ResponseVo delete(@PathVariable(name = "id") String id, @PathVariable(name = "updater") String updater){
        return taskConfigService.delete(id,updater);
    }


    @PostMapping("/update")
    public ResponseVo update(@RequestBody TaskConfigDto taskConfigDto){
        return taskConfigService.update(taskConfigDto);
    }

}
