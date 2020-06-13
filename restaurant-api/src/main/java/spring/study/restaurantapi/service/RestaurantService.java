package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.MenuItem;
import spring.study.restaurantapi.domain.MenuItemRepository;
import spring.study.restaurantapi.domain.Restaurant;
import spring.study.restaurantapi.domain.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository=restaurantRepository;
        this.menuItemRepository=menuItemRepository;
    }

    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant= restaurantRepository.findById(id).orElse(null);

        List<MenuItem> menuItems=menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants=restaurantRepository.findAll();

        return restaurants;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
