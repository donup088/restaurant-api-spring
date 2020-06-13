package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.study.restaurantapi.domain.*;
import spring.study.restaurantapi.service.RestaurantService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        return restaurantService.getRestaurants();
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        Restaurant restaurant=restaurantService.getRestaurant(id);

        return  restaurant;
    }

    @PostMapping("/restaurants")//포스트로 보낼때 상태를 같이넘겨주기 위해서 ResponseEntitu사용
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {
        String name=resource.getName();
        String address=resource.getAddress();

        Restaurant restaurant=new Restaurant(name,address);
        restaurantService.addRestaurant(restaurant);

        URI location=new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
