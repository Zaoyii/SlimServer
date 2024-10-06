package com.zcyi.slimserver.TaskPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Override
    public ArrayList<Task> selectTaskByGoalId(long goalType) {
        return taskDao.selectTaskByGoalId(goalType);
    }

    @Override
    public TaskAssignment findById(int taskAssignmentId) {
        return taskDao.findById(taskAssignmentId);
    }

    @Override
    public int insert(TaskAssignment taskAssignment) {
        return taskDao.insert(taskAssignment);
    }

    @Override
    public ArrayList<TaskAssignment> findByUserIdAndCurrentTime(int userId) {
        return taskDao.findByUserIdAndCurrentTime(userId);
    }

    @Override
    public int updateActualCompletionCount(int taskAssignmentId, int actualCompletionCount) {
        return taskDao.updateActualCompletionCount(taskAssignmentId, actualCompletionCount);
    }

    @Override
    public Task selectTaskByTaskId(long taskId) {
        return taskDao.selectTaskByTaskId(taskId);
    }

    @Override
    public int updateUserGoal(long userId, int goalType) {
        return taskDao.updateUserGoal(userId,goalType);
    }


}
