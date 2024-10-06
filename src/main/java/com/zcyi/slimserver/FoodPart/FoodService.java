package com.zcyi.slimserver.FoodPart;

import java.util.ArrayList;

public interface FoodService {

    ArrayList<Food> selectAllFoodByType(int foodTypeId, int page);

    ArrayList<FoodType> getAllFoodType();

    ArrayList<Food> getFoodByRandom(int foodType);


    ArrayList<Food> getFoodByName(String foodName);
}
