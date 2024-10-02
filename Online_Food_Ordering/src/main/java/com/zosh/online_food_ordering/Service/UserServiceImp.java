package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.config.JwtProvider;
import com.zosh.online_food_ordering.model.User;
import com.zosh.online_food_ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email =  jwtProvider.getEmailFormJwtToken(jwt);
            User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new Exception("user not found");
        }

        return user;
    }
}
