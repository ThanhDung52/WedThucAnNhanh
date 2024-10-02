package com.zosh.online_food_ordering.Request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long foodId;
    private  int quantity;
    private List<String> ingredients;
}
