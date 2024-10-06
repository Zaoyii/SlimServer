package com.zcyi.slimserver.ExercisePart;

import com.zcyi.slimserver.Base.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    private final ExerciseServiceImpl exerciseService;

    public ExerciseController(ExerciseServiceImpl exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/addExercise")
    @ResponseBody
    public ApiResult<Exercise> addExercise(@RequestBody Exercise exercise) {
        if (exercise != null) {
            exerciseService.addExercise(exercise);
            return ApiResult.success("添加成功");
        } else {
            return ApiResult.failed("参数有误");
        }
    }

    @PostMapping("/addExercises")
    @ResponseBody
    public ApiResult<Exercise> addExercises(@RequestBody ArrayList<Exercise> exercises) {
        System.out.println(exercises + "-=-=-==--");
        if (exercises != null) {
            System.out.println(exercises);
            for (int i = 0; i < exercises.size(); i++) {
                exerciseService.addExercise(exercises.get(i));
            }
            return ApiResult.success("添加成功");
        } else {
            return ApiResult.failed("参数有误");
        }
    }

    @GetMapping("/getExerciseByUserId")
    @ResponseBody
    public ApiResult<ArrayList<Exercise>> getExerciseByUserId(int userId) {
        ArrayList<Exercise> exercise = exerciseService.selectAllExerciseByUserId(userId);
        System.out.println("-=-=--=-" + exercise);
        if (exercise != null && exercise.size() > 0) {
            return ApiResult.success(exercise);
        } else {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
    }

    @GetMapping("/getExerciseByExerciseId")
    @ResponseBody
    public ApiResult<Exercise> getExerciseByExerciseId(int exerciseId) {
        Exercise exercise = exerciseService.selectAllExerciseByExerciseId(exerciseId);
        if (exercise != null) {
            return ApiResult.success(exercise);
        } else {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
    }

    @GetMapping("/getEndExerciseDataByUserId")
    @ResponseBody
    public ApiResult<HashMap<String, Double>> getEndExerciseDataByUserId(int userId) {

        ArrayList<Exercise> monthExercise = exerciseService.getThisMonthDataByUserId(userId);
        ArrayList<Exercise> yearExercise = exerciseService.getThisYearDataByUserId(userId);
        if (monthExercise == null || yearExercise == null) {
            return ApiResult.failed("获取数据失败~");
        }

        Double monthDistance = 0d;
        Double monthCalories = 0d;
        Double yearDistance = 0d;
        Double yearCalories = 0d;
        HashMap<String, Double> result = new HashMap<>();

        if (yearExercise.size() > 0) {
            for (int i = 0; i < yearExercise.size(); i++) {
                yearDistance += yearExercise.get(i).exerciseDistance;
                yearCalories += yearExercise.get(i).exerciseCalories;
            }
            if (monthExercise.size() > 0) {
                for (int i = 0; i < monthExercise.size(); i++) {
                    monthDistance += monthExercise.get(i).exerciseDistance;
                    monthCalories += monthExercise.get(i).exerciseCalories;
                }
            }
            result.put("monthDistance", monthDistance);
            result.put("yearDistance", yearDistance);
            result.put("monthCalories", monthCalories);
            result.put("yearCalories", yearCalories);
            return ApiResult.success(result);
        } else {
            return ApiResult.failed();
        }

    }

}
