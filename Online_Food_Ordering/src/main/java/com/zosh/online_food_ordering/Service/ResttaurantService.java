package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.CreateRestaurantRequest;
import com.zosh.online_food_ordering.dto.RestaurantDto;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.model.User;

import java.util.List;

public interface ResttaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public  void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId,User user) throws  Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;

}
