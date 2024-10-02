package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws  Exception;

    public User findUserByEmail(String email) throws Exception;
}
