package com.zosh.online_food_ordering.Service;


import com.zosh.online_food_ordering.model.Category;
import com.zosh.online_food_ordering.model.IngredientCategory;
import com.zosh.online_food_ordering.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws  Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;

    void  deleteIngredientCategory(Long id) throws Exception;

}
