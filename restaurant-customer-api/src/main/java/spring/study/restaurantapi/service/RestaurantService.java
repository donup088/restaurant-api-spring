package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.*;

import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    private MenuItemRepository menuItemRepository;

    private ReviewRepository reviewRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository,
                             ReviewRepository reviewRepository) {
        this.restaurantRepository=restaurantRepository;
        this.menuItemRepository=menuItemRepository;
        this.reviewRepository=reviewRepository;
    }

    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant= restaurantRepository.findById(id).orElseThrow(()->new RestaurantNotFoundException(id));

        List<MenuItem> menuItems=menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        List<Review> reviews=reviewRepository.findAllByRestaurantId(id);
        restaurant.setReviews(reviews);
        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants=restaurantRepository.findAll();

        return restaurants;
    }


}
