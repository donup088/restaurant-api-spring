package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.Restaurant;
import spring.study.restaurantapi.service.RestaurantService;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region,
            @RequestParam("category")Long categoryId){
        return restaurantService.getRestaurants(region, categoryId);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        Restaurant restaurant=restaurantService.getRestaurant(id);

        return  restaurant;
    }

}
