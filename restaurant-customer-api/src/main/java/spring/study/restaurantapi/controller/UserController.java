package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user=userService.registerUser(
                resource.getEmail(),resource.getName(),resource.getPassword());

        String url="/users/"+user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
