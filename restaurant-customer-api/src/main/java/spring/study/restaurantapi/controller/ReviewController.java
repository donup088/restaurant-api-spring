package spring.study.restaurantapi.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.service.ReviewService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @Valid @RequestBody Review resource,
            @PathVariable Long restaurantId) throws URISyntaxException {
        Claims claims= (Claims) authentication.getPrincipal();
        String name=claims.get("name",String.class);
        Integer score=resource.getScore();
        String description=resource.getDescription();

        Review review = reviewService.addReview(
                restaurantId,name,score,description);

        String uri="/restaurants/"+restaurantId+"/reviews/"+review.getId();

        return ResponseEntity.created(new URI(uri)).body("{}");
    }

}
