package com.zcyi.slimserver.ExercisePart;

import com.zcyi.slimserver.FoodPart.Food;
import com.zcyi.slimserver.FoodPart.FoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    ExerciseDao exerciseDao;

    @Override
    public ArrayList<Exercise> selectAllExerciseByUserId(int userId) {
        return exerciseDao.selectAllExerciseByUserId(userId);
    }

    @Override
    public int addExercise(Exercise exercise) {
        return exerciseDao.addExercise(exercise);
    }

    @Override
    public ArrayList<Exercise> getThisMonthDataByUserId(int userId) {

        return exerciseDao.getThisMonthDataByUserId(userId);
    }

    @Override
    public ArrayList<Exercise> getThisYearDataByUserId(int userId) {
        return exerciseDao.getThisYearDataByUserId(userId);
    }

    @Override
    public Exercise selectAllExerciseByExerciseId(int exerciseId) {
        return exerciseDao.selectAllExerciseByExerciseId(exerciseId);
    }
}
