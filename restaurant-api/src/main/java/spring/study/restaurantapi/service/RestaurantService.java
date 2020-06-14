package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.study.restaurantapi.domain.*;

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
        Restaurant restaurant= restaurantRepository.findById(id).orElseThrow(()->new RestaurantNotFoundException(id));

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

    @Transactional//코드안에서 변경된 부분들이 코드를 벗어나면서 DB에 적용됨
    public Restaurant updateRestaurant(Long id, String name, String address) {

        Restaurant restaurant=restaurantRepository.findById(id).orElse(null);

        restaurant.updateInformation(name,address);

       return restaurant;
    }
}
