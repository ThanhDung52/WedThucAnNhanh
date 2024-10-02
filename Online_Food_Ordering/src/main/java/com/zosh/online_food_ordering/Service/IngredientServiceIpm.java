package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.model.Category;
import com.zosh.online_food_ordering.model.IngredientCategory;
import com.zosh.online_food_ordering.model.IngredientsItem;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.repository.IngredientCategoryRepository;
import com.zosh.online_food_ordering.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceIpm implements IngredientsService{

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private ResttaurantService resttaurantService;




    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if (opt.isEmpty()){
            throw new Exception("Ingredient category not font");
        }

        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {

        resttaurantService.findRestaurantById(id);

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = resttaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);


        IngredientsItem ingredients = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredients);
        return null;
    }
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = resttaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }



    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {

        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if (optionalIngredientsItem.isEmpty()){
            throw new Exception("ingredient not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }

    @Override
    public void deleteIngredientCategory(Long id) throws Exception {
        IngredientCategory ingredientCategory = findIngredientCategoryById(id);
        ingredientCategory.setIngredients(null);

        ingredientCategoryRepository.save(ingredientCategory);
    }


}
