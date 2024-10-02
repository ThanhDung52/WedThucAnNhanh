package com.zosh.online_food_ordering.repository;

import com.zosh.online_food_ordering.model.Cart;
import org.hibernate.Length;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByCustomerId(Long userId);
}
