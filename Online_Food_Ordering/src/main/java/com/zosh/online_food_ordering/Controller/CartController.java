package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Request.AddCartItemRequest;
import com.zosh.online_food_ordering.Request.UpdateCartItemRequest;
import com.zosh.online_food_ordering.Service.CartService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.Cart;
import com.zosh.online_food_ordering.model.CartItem;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addToCart(@RequestBody AddCartItemRequest req,
                                              @RequestHeader("Authorization") String jwt
                                              ) throws Exception{

        CartItem cartItem = cartService.addToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }


    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }


    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
          @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.cleatCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

//    @GetMapping("/carts/{id}/item")
//    public ResponseEntity<CartItem> findUserCartItem(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
//
//    }
}
