package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.AddCartItemRequest;
import com.zosh.online_food_ordering.model.Cart;
import com.zosh.online_food_ordering.model.CartItem;
import com.zosh.online_food_ordering.model.Food;
import com.zosh.online_food_ordering.model.User;
import com.zosh.online_food_ordering.repository.CartItemRepository;
import com.zosh.online_food_ordering.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceIpm implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItem()){
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                CartItem updatedCartItem = updateCartItemQuantity(cartItem.getId(), newQuantity);

                // Cập nhật lại tổng giá trị của giỏ hàng
                updateCartTotal(cart);
                return updatedCartItem;
            }
        }


        CartItem newcartItem = new CartItem();
        newcartItem.setFood(food);
        newcartItem.setCart(cart);
        newcartItem.setQuantity(req.getQuantity());
        newcartItem.setIngredients(req.getIngredients());
        newcartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem saveCartItem = cartItemRepository.save(newcartItem);

        cart.getItem().add(saveCartItem);
        return saveCartItem;
    }

    private void updateCartTotal(Cart cart) {
        Long total = 0L;
        for (CartItem cartItem : cart.getItem()) {
            total += cartItem.getTotalPrice();
        }
        cart.setTotal(total);

        // Lưu lại giỏ hàng sau khi cập nhật tổng giá trị
        cartRepository.save(cart);
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item  = cartItemOptional.get();
        item.setQuantity(quantity);


        item.setTotalPrice(item.getFood().getPrice()*quantity);



        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {


        User user = userService.findUserByJwtToken(jwt);


        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }

        CartItem item = cartItemOptional.get();

        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;

        for (CartItem cartItem : cart.getItem()){
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()){
            throw new Exception("cart not found with id" + id);
        }

        return cartOptional.get();
    }

    @Override
    public Cart findCartUserId(Long userId) throws Exception {
      //  User user = userService.findUserByJwtToken(jwt);


        Cart cart =  cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart cleatCart(Long userId) throws Exception {

       // User user = userService.findUserByJwtToken(userId);

        Cart cart =findCartUserId(userId);

        cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
