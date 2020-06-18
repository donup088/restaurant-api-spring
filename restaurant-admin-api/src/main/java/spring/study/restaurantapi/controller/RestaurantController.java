package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.study.restaurantapi.domain.Restaurant;
import spring.study.restaurantapi.service.RestaurantService;

import javax.validation.Valid;
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

    @PostMapping("/restaurants")//포스트로 보낼때 상태를 같이넘겨주기 위해서 ResponseEntity 사용
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
        Restaurant restaurant=restaurantService.addRestaurant(
                Restaurant.builder()
                        .name(resource.getName())
                        .address(resource.getAddress())
                        .build());

        URI location=new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("restaurants/{id}")
    public String update(@PathVariable("id")Long id,
                             @Valid @RequestBody Restaurant resource){
        String name=resource.getName();
        String address=resource.getAddress();
        restaurantService.updateRestaurant(id,name,address);

        return "{}";
    }
}
