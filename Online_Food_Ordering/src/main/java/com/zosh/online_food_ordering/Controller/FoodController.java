package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Service.FoodService;
import com.zosh.online_food_ordering.Service.ResttaurantService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.Food;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResttaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);


        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam (required = false) boolean vagetarian,
            @RequestParam (required = false) boolean seasonal,
            @RequestParam (required = false) boolean nonveg,
            @RequestParam(required = false)String food_category ,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vagetarian,nonveg,seasonal,food_category);



        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Food>> getAllFood(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getAllFood();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
