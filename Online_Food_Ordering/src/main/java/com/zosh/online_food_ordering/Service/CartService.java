package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.AddCartItemRequest;
import com.zosh.online_food_ordering.model.Cart;
import com.zosh.online_food_ordering.model.CartItem;

public interface CartService {

    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;

    public Long calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartUserId(Long userId) throws Exception;

    public Cart cleatCart(Long userId) throws Exception;

}
