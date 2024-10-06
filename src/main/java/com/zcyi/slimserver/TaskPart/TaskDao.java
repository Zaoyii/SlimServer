package com.zcyi.slimserver.TaskPart;

import com.zcyi.slimserver.UserPart.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface TaskDao {
    @Select("Select * from t_task where task_goal_type = #{goalType} ORDER BY task_id")
    @Results({
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "taskName", column = "task_name"),
            @Result(property = "taskTime", column = "task_time"),
            @Result(property = "taskTimes", column = "task_times"),
            @Result(property = "taskGoalType", column = "task_goal_type"),
            @Result(property = "taskExample", column = "task_example"),
    })
    ArrayList<Task> selectTaskByGoalId(long goalType);

    @Select("Select * from t_task where task_id = #{taskId} ORDER BY task_id")
    @Results({
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "taskName", column = "task_name"),
            @Result(property = "taskTime", column = "task_time"),
            @Result(property = "taskTimes", column = "task_times"),
            @Result(property = "taskGoalType", column = "task_goal_type"),
            @Result(property = "taskExample", column = "task_example"),
    })
    Task selectTaskByTaskId(long taskId);

    @Select("SELECT * FROM t_assignment WHERE task_assignment_id = #{taskAssignmentId}")
    @Results({
            @Result(property = "taskAssignmentId", column = "task_assignment_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "expectedCompletionCount", column = "expected_completion_count"),
            @Result(property = "actualCompletionCount", column = "actual_completion_count"),
            @Result(property = "taskDuration", column = "task_duration"),
            @Result(property = "taskGoalType", column = "task_goal_type"),
            @Result(property = "assignmentUpdateTime", column = "assignment_update_time")
    })
    TaskAssignment findById(int taskAssignmentId);

    @Insert("INSERT INTO t_assignment (user_id, task_id, start_date, end_date, expected_completion_count, actual_completion_count, task_duration, task_goal_type) " +
            "VALUES (#{userId}, #{taskId}, #{startDate}, #{endDate}, #{expectedCompletionCount}, #{actualCompletionCount}, #{taskDuration}, #{taskGoalType})")
    @Options(useGeneratedKeys = true, keyProperty = "taskAssignmentId")
    int insert(TaskAssignment taskAssignment);

    @Select("SELECT * FROM t_assignment WHERE user_id = #{userId} AND CURRENT_DATE() BETWEEN start_date AND end_date")
    @Results({
            @Result(property = "taskAssignmentId", column = "task_assignment_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "expectedCompletionCount", column = "expected_completion_count"),
            @Result(property = "actualCompletionCount", column = "actual_completion_count"),
            @Result(property = "taskDuration", column = "task_duration"),
            @Result(property = "taskGoalType", column = "task_goal_type"),
            @Result(property = "assignmentUpdateTime", column = "assignment_update_time")
    })
    ArrayList<TaskAssignment> findByUserIdAndCurrentTime(@Param("userId") int userId);

    @Update("UPDATE t_assignment SET actual_completion_count = #{actualCompletionCount} WHERE task_assignment_id = #{taskAssignmentId}")
    int updateActualCompletionCount(@Param("taskAssignmentId") int taskAssignmentId, @Param("actualCompletionCount") int actualCompletionCount);

    @Update("update t_user set user_goal=#{userGoal} where user_id= #{userId}")
    int updateUserGoal(long userId,int goalType);
}
