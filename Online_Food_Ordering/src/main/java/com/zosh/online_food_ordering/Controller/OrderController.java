package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Request.AddCartItemRequest;
import com.zosh.online_food_ordering.Request.OrderRequest;
import com.zosh.online_food_ordering.Response.PaymentResponse;
import com.zosh.online_food_ordering.Service.OrderService;
import com.zosh.online_food_ordering.Service.PaymentService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.CartItem;
import com.zosh.online_food_ordering.model.Order;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req,user);
        PaymentResponse res = paymentService.createPaymentLink(order);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                             @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUserOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
