package com.zcyi.slimserver.ExercisePart;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ExerciseDao {
    @Select("select * from t_exercise where exercise_user_id =#{userId}")
    @Results({
            @Result(property = "exerciseId", column = "exercise_id"),
            @Result(property = "userId", column = "exercise_user_id"),
            @Result(property = "exerciseType", column = "exercise_type"),
            @Result(property = "exerciseData", column = "exercise_data"),
            @Result(property = "exerciseDistance", column = "exercise_distance"),
            @Result(property = "exerciseSpeed", column = "exercise_speed"),
            @Result(property = "exerciseCalories", column = "exercise_calories"),
            @Result(property = "startTime", column = "exercise_start_time"),
            @Result(property = "endTime", column = "exercise_end_time"),
            @Result(property = "exerciseCreateTime", column = "exercise_create_time"),
    })
    ArrayList<Exercise> selectAllExerciseByUserId(int userId);

    @Select("select * from t_exercise where exercise_id =#{exerciseId}")
    @Results({
            @Result(property = "exerciseId", column = "exercise_id"),
            @Result(property = "userId", column = "exercise_user_id"),
            @Result(property = "exerciseType", column = "exercise_type"),
            @Result(property = "exerciseData", column = "exercise_data"),
            @Result(property = "exerciseDistance", column = "exercise_distance"),
            @Result(property = "exerciseSpeed", column = "exercise_speed"),
            @Result(property = "exerciseCalories", column = "exercise_calories"),
            @Result(property = "startTime", column = "exercise_start_time"),
            @Result(property = "endTime", column = "exercise_end_time"),
            @Result(property = "exerciseCreateTime", column = "exercise_create_time"),
    })
    Exercise selectAllExerciseByExerciseId(int exerciseId);

    @Select("SELECT * FROM t_exercise WHERE MONTH(exercise_create_time) = MONTH(CURDATE());")
    @Results({
            @Result(property = "exerciseId", column = "exercise_id"),
            @Result(property = "userId", column = "exercise_user_id"),
            @Result(property = "exerciseType", column = "exercise_type"),
            @Result(property = "exerciseDistance", column = "exercise_distance"),
            @Result(property = "exerciseCalories", column = "exercise_calories"),
            @Result(property = "startTime", column = "exercise_start_time"),
            @Result(property = "endTime", column = "exercise_end_time"),
            @Result(property = "exerciseCreateTime", column = "exercise_create_time"),
    })
    ArrayList<Exercise> getThisMonthDataByUserId(int userId);

    @Select("SELECT * FROM t_exercise WHERE YEAR(exercise_create_time) = YEAR(CURDATE()) and exercise_user_id=#{userId};")
    @Results({
            @Result(property = "exerciseId", column = "exercise_id"),
            @Result(property = "userId", column = "exercise_user_id"),
            @Result(property = "exerciseType", column = "exercise_type"),
            @Result(property = "exerciseDistance", column = "exercise_distance"),
            @Result(property = "exerciseCalories", column = "exercise_calories"),
            @Result(property = "startTime", column = "exercise_start_time"),
            @Result(property = "endTime", column = "exercise_end_time"),
            @Result(property = "exerciseCreateTime", column = "exercise_create_time"),
    })
    ArrayList<Exercise> getThisYearDataByUserId(int userId);

    @Insert("insert into t_exercise (exercise_user_id,exercise_type,exercise_data,exercise_distance,exercise_speed,exercise_calories,exercise_start_time,exercise_end_time) " +
            "value (#{userId},#{exerciseType},#{exerciseData},#{exerciseDistance},#{exerciseSpeed},#{exerciseCalories},#{startTime},#{endTime}) ")
    int addExercise(Exercise exercise);

}
