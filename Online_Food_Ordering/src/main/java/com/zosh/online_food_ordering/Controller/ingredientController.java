package com.zosh.online_food_ordering.Controller;

import com.zosh.online_food_ordering.Request.IngredientCategoryRequest;
import com.zosh.online_food_ordering.Request.IngredientRequest;
import com.zosh.online_food_ordering.Response.MessageResponse;
import com.zosh.online_food_ordering.Service.IngredientsService;
import com.zosh.online_food_ordering.Service.UserService;
import com.zosh.online_food_ordering.model.IngredientCategory;
import com.zosh.online_food_ordering.model.IngredientsItem;
import com.zosh.online_food_ordering.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class ingredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserService userService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception{
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception{
        IngredientsItem item = ingredientsService.createIngredientsItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(
          @PathVariable Long id
    ) throws Exception{
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> restaurantIngredient(
            @PathVariable Long id
    ) throws Exception{
        List<IngredientsItem> item = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> restaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception{
        List<IngredientCategory> item = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteIngredient(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String jwt
                                                            ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        ingredientsService.deleteIngredientCategory(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Successfully deleted ingredient category");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
