package com.zosh.online_food_ordering.repository;

import com.zosh.online_food_ordering.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long> {

    List<IngredientsItem> findByRestaurantId(Long id);


}
