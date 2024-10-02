package com.zosh.online_food_ordering.Request;

import com.zosh.online_food_ordering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}
