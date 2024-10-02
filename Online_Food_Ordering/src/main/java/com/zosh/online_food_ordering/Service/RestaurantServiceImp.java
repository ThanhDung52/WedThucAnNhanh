package com.zosh.online_food_ordering.Service;

import com.zosh.online_food_ordering.Request.CreateRestaurantRequest;
import com.zosh.online_food_ordering.dto.RestaurantDto;
import com.zosh.online_food_ordering.model.Address;
import com.zosh.online_food_ordering.model.ContactInformation;
import com.zosh.online_food_ordering.model.Restaurant;
import com.zosh.online_food_ordering.model.User;
import com.zosh.online_food_ordering.repository.AddressRepository;
import com.zosh.online_food_ordering.repository.RestaurantRepository;
import com.zosh.online_food_ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements ResttaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);



       return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);


        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        if (updatedRestaurant.getAddress() != null) {
            Address address = updatedRestaurant.getAddress();
            if (address.getStreetAddress() != null) {
                restaurant.getAddress().setStreetAddress(address.getStreetAddress());
            }
            if (address.getCity() != null) {
                restaurant.getAddress().setCity(address.getCity());
            }
            if (address.getStateProvince() != null) {
                restaurant.getAddress().setStateProvince(address.getStateProvince());
            }
            if (address.getPostalCode() != null) {
                restaurant.getAddress().setPostalCode(address.getPostalCode());
            }
            if (address.getCountry() != null) {
                restaurant.getAddress().setCountry(address.getCountry());
            }
        }
        if (updatedRestaurant.getContactInformation() != null) {
            ContactInformation contactInfo = updatedRestaurant.getContactInformation();
            if (contactInfo.getEmail() != null) {
                restaurant.getContactInformation().setEmail(contactInfo.getEmail());
            }
            if (contactInfo.getMobile() != null) {
                restaurant.getContactInformation().setMobile(contactInfo.getMobile());
            }
            if (contactInfo.getTwitter() != null) {
                restaurant.getContactInformation().setTwitter(contactInfo.getTwitter());
            }
            if (contactInfo.getInstagram() != null) {
                restaurant.getContactInformation().setInstagram(contactInfo.getInstagram());
            }
        }
        if (updatedRestaurant.getImages() != null){
            restaurant.setImages(updatedRestaurant.getImages());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt= restaurantRepository.findById(id);

        if (opt.isEmpty()){
            throw new Exception("restaurant not found with id" + id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null){
            throw new Exception("restaurant not found with owner id" + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new  RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites){
            if (favorite.getId().equals(restaurantId)){
                isFavorited = true;
                break;
            }
        }

        if (isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
