package com.zcyi.slimserver.FoodPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodDao foodDao;

    @Override
    public ArrayList<Food> selectAllFoodByType(int foodTypeId, int page) {
        return foodDao.selectAllFoodByType(foodTypeId, page);
    }

    @Override
    public ArrayList<FoodType> getAllFoodType() {
        return foodDao.getAllFoodType();
    }

    @Override
    public ArrayList<Food> getFoodByRandom(int foodType) {
        return foodDao.getFoodByRandom(foodType);
    }

    @Override
    public ArrayList<Food> getFoodByName(String foodName) {
        return foodDao.getFoodByName(foodName);
    }

}
