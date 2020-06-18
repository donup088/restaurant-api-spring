package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            @Valid @RequestBody Review resource,
            @PathVariable Long restaurantId) throws URISyntaxException {
        Review review = reviewService.addReview(restaurantId,resource);
        String uri="/restaurants/"+restaurantId+"/reviews/"+review.getId();
        return ResponseEntity.created(new URI(uri)).body("{}");
    }

}
