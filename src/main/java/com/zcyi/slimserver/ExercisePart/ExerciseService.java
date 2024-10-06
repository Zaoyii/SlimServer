package com.zcyi.slimserver.ExercisePart;

import com.zcyi.slimserver.FoodPart.Food;
import com.zcyi.slimserver.FoodPart.FoodType;

import java.util.ArrayList;

public interface ExerciseService {
    ArrayList<Exercise> selectAllExerciseByUserId(int userId);
    int addExercise(Exercise exercise);
    ArrayList<Exercise> getThisMonthDataByUserId(int userId);
    ArrayList<Exercise> getThisYearDataByUserId(int userId);
    Exercise selectAllExerciseByExerciseId(int exerciseId);
}
