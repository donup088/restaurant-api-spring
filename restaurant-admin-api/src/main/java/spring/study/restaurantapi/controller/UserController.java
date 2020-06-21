package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user=userService.addUser(resource.getEmail(),resource.getName());

        String url="/users/"+ user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(@PathVariable("id") Long id,
                       @RequestBody User resource){
        String email=resource.getEmail();
        String name=resource.getName();
        Long level=resource.getLevel();

        userService.updateUser(id,email,name,level);

        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id")Long id){
        userService.deactivateUser(id);

        return "";
    }
}
