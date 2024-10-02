package com.zosh.online_food_ordering.Request;

import com.zosh.online_food_ordering.model.Category;
import com.zosh.online_food_ordering.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodResquest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredients;
}
