package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.service.UserService;
import spring.study.restaurantapi.utils.JwtUtil;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {
        User user=userService.authenticate(resource.getEmail(),resource.getPassword());

        String accessToken=jwtUtil.createToken(user.getId(),user.getName());

        String url="/session";

        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto
                .builder()
                .accessToken(accessToken)
                .build());
    }
}
