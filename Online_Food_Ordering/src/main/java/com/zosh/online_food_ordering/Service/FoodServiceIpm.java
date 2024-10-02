package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.CreateFoodResquest;
import com.zosh.online_food_ordering.model.Category;
import com.zosh.online_food_ordering.model.Food;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceIpm implements FoodService {
    @Autowired
    private FoodRepository foodRepository;





    @Override
    public Food createtoFood(CreateFoodResquest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodcategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredientsItems(req.getIngredients());
        food.setSeasonal(req.isSeasional());
        food.setCreationDate(new Date());
        food.setVegetarian(req.isVegetarin());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

        Food food = findById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarein,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegitarein){
            foods = filterByVegetarian(foods,isVegitarein);
        }
        if (isNonveg){
            foods = filterByNonveg(foods,isNonveg);
        }
        if (isSeasonal){
            foods = filterBySeasona(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")){
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodcategory() != null){
                return food.getFoodcategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasona(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarein) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegitarein).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findById(Long foodID) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodID);

        if (optionalFood.isEmpty()){
            throw new Exception("food not exit....");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailibiityStatus(Long foodId) throws Exception {
        Food food = findById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);


    }

    @Override
    public List<Food> getAllFood() throws Exception {
        return foodRepository.findAll();
    }
}
