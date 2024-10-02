package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Request.CreateRestaurantRequest;
import com.zosh.online_food_ordering.Response.MessageResponse;
import com.zosh.online_food_ordering.Service.ResttaurantService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private ResttaurantService resttaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = resttaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable long id
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = resttaurantService.updateRestaurant(id,req);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
//        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> createRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        resttaurantService.deleteRestaurant(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("restaurant deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant =  resttaurantService.updateRestaurantStatus(id);


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant =  resttaurantService.getRestaurantByUserId(user.getId());


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
