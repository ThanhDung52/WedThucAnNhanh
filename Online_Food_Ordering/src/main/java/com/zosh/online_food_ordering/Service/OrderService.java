package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.OrderRequest;
import com.zosh.online_food_ordering.model.Order;
import com.zosh.online_food_ordering.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public  Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void calcelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;


    public Order findOrderById(Long orderId) throws Exception;
}
