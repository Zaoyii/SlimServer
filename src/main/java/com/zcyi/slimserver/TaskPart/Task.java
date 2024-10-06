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
public class Task {
    int taskId;
    String taskName;
    int taskTime;
    int taskTimes;
    int taskGoalType;
    String taskExample;
}
