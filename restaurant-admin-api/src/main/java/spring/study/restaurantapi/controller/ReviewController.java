package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.service.ReviewService;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> list(){
        List<Review>reviews=reviewService.getReviews();

        return  reviews;
    }

}
