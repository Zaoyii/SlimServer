package com.zcyi.slimserver.FoodPart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface FoodDao {

    //
    @Select("select * from t_food where food_type_id =#{foodTypeId} limit #{page},20")
    @Results({
            @Result(property = "foodId", column = "food_id"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "foodImage", column = "food_image"),
            @Result(property = "foodTypeId", column = "food_type_id"),
            @Result(property = "foodCalories", column = "food_calories"),
            @Result(property = "foodCarbohydrate", column = "food_carbohydrate"),
            @Result(property = "foodProtein", column = "food_protein"),
            @Result(property = "foodFat", column = "food_fat"),
            @Result(property = "foodCreateTime", column = "food_create_time"),
    })
    ArrayList<Food> selectAllFoodByType(int foodTypeId, int page);

    @Select("select * from t_food_type ")
    @Results({
            @Result(property = "foodTypeId", column = "food_type_id"),
            @Result(property = "foodTypeName", column = "food_type_name"),
            @Result(property = "foodTypePinyin", column = "food_type_pinyin"),
            @Result(property = "foodTypeImg", column = "food_type_img"),
            @Result(property = "foodTypeCreateTime", column = "food_type_create_time"),
    })
    ArrayList<FoodType> getAllFoodType();

    @Select("select * from t_food where food_type_id = #{foodType} order by rand() LIMIT 20")
    @Results({
            @Result(property = "foodId", column = "food_id"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "foodImage", column = "food_image"),
            @Result(property = "foodTypeId", column = "food_type_id"),
            @Result(property = "foodCalories", column = "food_calories"),
            @Result(property = "foodCarbohydrate", column = "food_carbohydrate"),
            @Result(property = "foodProtein", column = "food_protein"),
            @Result(property = "foodFat", column = "food_fat"),
            @Result(property = "foodCreateTime", column = "food_create_time"),
    })
    ArrayList<Food> getFoodByRandom(int foodType);

    @Select("select * from t_food where food_name LIKE CONCAT('%',#{name},'%')")
    @Results({
            @Result(property = "foodId", column = "food_id"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "foodImage", column = "food_image"),
            @Result(property = "foodTypeId", column = "food_type_id"),
            @Result(property = "foodCalories", column = "food_calories"),
            @Result(property = "foodCarbohydrate", column = "food_carbohydrate"),
            @Result(property = "foodProtein", column = "food_protein"),
            @Result(property = "foodFat", column = "food_fat"),
            @Result(property = "foodCreateTime", column = "food_create_time"),
    })
    ArrayList<Food> getFoodByName(String name);

}
