package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Service.OrderService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.Order;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;


    @Autowired
    private UserService userService;




    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String order_status
    ) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getRestaurantOrder(id,order_status);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }



    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id,orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
