package com.zcyi.slimserver.FoodPart;

import com.zcyi.slimserver.Base.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodServiceImpl foodService;

    public FoodController(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/getAllFoodType")
    @ResponseBody
    public ApiResult<ArrayList<FoodType>> getAllFoodType() {
        ArrayList<FoodType> allFoodType = foodService.getAllFoodType();
        if (allFoodType != null && allFoodType.size() > 0) {
            return ApiResult.success(allFoodType);
        } else {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
    }

    @GetMapping("/getFoodByType")
    @ResponseBody
    public ApiResult<ArrayList<Food>> getFoodByType(int foodType, int page) {
        if (page >= 0) {
            ArrayList<Food> foods = foodService.selectAllFoodByType(foodType, page * 20);
            if (foods != null) {
                if (foods.size() > 0) {
                    return ApiResult.success(foods);
                } else {
                    return ApiResult.success("已无更多数据");
                }

            } else {
                return ApiResult.failed("获取失败，请稍后尝试~");
            }
        }
        return ApiResult.failed("获取失败，请稍后尝试~");
    }

    @GetMapping("/getFoodByRandom")
    @ResponseBody
    public ApiResult<ArrayList<Food>> getFoodByRandom(int foodType) {
        ArrayList<Food> foods = foodService.getFoodByRandom(foodType);
        if (foods != null && foods.size() > 0) {
            return ApiResult.success(foods);
        } else {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
    }

    @GetMapping("/getFoodByName")
    @ResponseBody
    public ApiResult<ArrayList<Food>> getFoodByName(String foodName) {
        if (foodName == null || foodName.isEmpty()) {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
        ArrayList<Food> foods = foodService.getFoodByName(foodName);
        if (foods != null && foods.size() > 0) {
            return ApiResult.success(foods);
        } else {
            return ApiResult.failed("获取失败，请稍后尝试~");
        }
    }

}
