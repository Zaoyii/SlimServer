package com.zcyi.slimserver.ExercisePart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Exercise {
    long exerciseId;
    long userId;
    int exerciseType;
    String exerciseData;
    Double exerciseDistance;
    Double exerciseSpeed;
    Double exerciseCalories;
    String startTime;
    String endTime;
    String exerciseCreateTime;
}
