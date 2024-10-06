package com.zcyi.slimserver.FoodPart;

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
public class Food {
    long foodId;
    String foodName;
    String foodImage;
    long foodTypeId;
    String foodCalories;
    String foodCarbohydrate;
    String foodProtein;
    String foodFat;
    String foodCreateTime;
}
