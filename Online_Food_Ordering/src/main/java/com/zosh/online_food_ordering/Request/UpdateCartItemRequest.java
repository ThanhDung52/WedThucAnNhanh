package com.zosh.online_food_ordering.Request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long cartItemId;
    private int quantity;

}
