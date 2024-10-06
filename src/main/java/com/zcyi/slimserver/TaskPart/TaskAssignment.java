package com.zcyi.slimserver.TaskPart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskAssignment {
    public int taskAssignmentId;
    public int userId;
    public int taskId;
    public String taskName;
    public String taskExample;
    public int taskTime;
    public String startDate;
    public String endDate;
    public int expectedCompletionCount;
    public int actualCompletionCount;
    public String taskDuration;
    public int taskGoalType;
    public String assignmentUpdateTime;

    @Override
    public String toString() {
        return "TaskAssignment{" +
                "taskAssignmentId=" + taskAssignmentId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", expectedCompletionCount=" + expectedCompletionCount +
                ", actualCompletionCount=" + actualCompletionCount +
                ", taskDuration='" + taskDuration + '\'' +
                ", taskGoalType=" + taskGoalType +
                '}';
    }
}
