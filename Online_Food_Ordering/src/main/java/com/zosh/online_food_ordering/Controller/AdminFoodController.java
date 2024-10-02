package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Request.CreateFoodResquest;
import com.zosh.online_food_ordering.Response.MessageResponse;
import com.zosh.online_food_ordering.Service.FoodService;
import com.zosh.online_food_ordering.Service.ResttaurantService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.Food;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResttaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodResquest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Food food = foodService.createtoFood(req,req.getCategory(), restaurant);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> DeleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);

         foodService.deleteFood(id);

         MessageResponse messageResponse = new MessageResponse();
         messageResponse.setMessage("Food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);


        Food food = foodService.updateAvailibiityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
