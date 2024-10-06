package com.zcyi.slimserver.TaskPart;

import java.util.ArrayList;
import java.util.List;

public interface TaskService {

    ArrayList<Task> selectTaskByGoalId(long goalType);

    TaskAssignment findById(int taskAssignmentId);

    int insert(TaskAssignment taskAssignment);

    List<TaskAssignment> findByUserIdAndCurrentTime(int userId);

    int updateActualCompletionCount(int taskAssignmentId, int actualCompletionCount);

    Task selectTaskByTaskId(long taskId);
    int updateUserGoal(long userId,int goalType);
}
