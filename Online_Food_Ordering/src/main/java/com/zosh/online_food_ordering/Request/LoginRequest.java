package com.zosh.online_food_ordering.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;

    private String password;
}
