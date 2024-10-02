package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.CreateFoodResquest;
import com.zosh.online_food_ordering.model.Category;
import com.zosh.online_food_ordering.model.Food;
import com.zosh.online_food_ordering.model.Restaurant;

import java.util.List;

public interface FoodService {

    public Food createtoFood(CreateFoodResquest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,boolean isVegitarein,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findById(Long foodID) throws Exception;

    public Food updateAvailibiityStatus(Long foodId) throws Exception;

public List<Food> getAllFood() throws Exception;


}
