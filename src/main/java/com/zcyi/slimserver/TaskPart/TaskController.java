package com.zcyi.slimserver.TaskPart;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zcyi.slimserver.Base.ApiResult;
import com.zcyi.slimserver.Util.UtilMethod;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/getTask")
    @ResponseBody
    public ApiResult<ArrayList<TaskAssignment>> getTask(@RequestHeader("Authorization") String authorization, int goalType) {
        System.out.println("---=-==-=" + goalType);
        if (goalType == -1) {
            System.out.println("--=-==-");
            return ApiResult.failed("参数有误");
        }
        DecodedJWT verify = UtilMethod.verify(authorization);
        String userId = verify.getClaim("userId").asString();
        System.out.println(":------=-=" + userId);
        ArrayList<Task> tasks = taskService.selectTaskByGoalId(goalType);
        if (tasks == null || tasks.size() == 0) {
            return ApiResult.failed("获取失败");
        }
        System.out.println(":------=-=" + userId);
        ArrayList<TaskAssignment> taskAssignments = taskService.findByUserIdAndCurrentTime(Integer.parseInt(userId));
        if (taskAssignments != null && taskAssignments.size() > 0) {
            taskAssignments = UtilMethod.setTaskName(taskAssignments, tasks);
            return ApiResult.success(taskAssignments);
        } else {
            System.out.println(":------=-=");
            for (Task t : tasks) {
                TaskAssignment taskAssignment = new TaskAssignment();
                taskAssignment.setTaskDuration("weekly");
                taskAssignment.setTaskGoalType(goalType);
                taskAssignment.setExpectedCompletionCount(t.getTaskTimes());
                taskAssignment.setUserId(Integer.parseInt(userId));
                taskAssignment.setTaskId(t.getTaskId());
                Map<String, String> sevenDay = UtilMethod.generateSevenDay();
                taskAssignment.setStartDate(sevenDay.get("startTime"));
                taskAssignment.setEndDate(sevenDay.get("endTime"));
                taskAssignment.setActualCompletionCount(0);
                taskService.insert(taskAssignment);
            }
            ArrayList<TaskAssignment> insertTaskAssignments = taskService.findByUserIdAndCurrentTime(Integer.parseInt(userId));
            if (insertTaskAssignments != null && insertTaskAssignments.size() > 0) {
                insertTaskAssignments = UtilMethod.setTaskName(insertTaskAssignments, tasks);
                return ApiResult.success(insertTaskAssignments);
            } else {
                return ApiResult.failed("获取失败");
            }
        }
    }

    @PostMapping("/updateTask")
    @ResponseBody
    public ApiResult<TaskAssignment> updateTask(int taskAssignmentId) {
        TaskAssignment taskAssignment = taskService.findById(taskAssignmentId);
        if (taskAssignment.getActualCompletionCount() == 0) {
            taskService.updateActualCompletionCount(taskAssignmentId, 1);
            return ApiResult.success(getTaskAssignment(taskAssignmentId));
        } else {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            String assignmentUpdateTime = taskAssignment.getAssignmentUpdateTime();
            System.out.println(today + ":" + assignmentUpdateTime);
            if (!today.equals(assignmentUpdateTime.substring(0, 10))) {
                if (taskAssignment.getActualCompletionCount() + 1 <= taskAssignment.getExpectedCompletionCount()) {
                    taskService.updateActualCompletionCount(taskAssignmentId, taskAssignment.getActualCompletionCount() + 1);
                    return ApiResult.success(getTaskAssignment(taskAssignmentId));
                } else {
                    return ApiResult.failed("任务已完成");
                }
            } else {
                return ApiResult.failed("今日已完成此任务");
            }
        }
    }

    private TaskAssignment getTaskAssignment(int taskAssignmentId) {
        TaskAssignment result;
        result = taskService.findById(taskAssignmentId);
        Task task = taskService.selectTaskByTaskId(result.taskId);
        result.setTaskName(task.getTaskName());
        result.setTaskExample(task.getTaskExample());
        result.setTaskTime(task.getTaskTime());
        return result;
    }

}
