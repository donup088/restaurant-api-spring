package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.domain.ReviewRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }

    public Review addReview(Long restaurantId,Review review) {
        review.setRestaurantId(restaurantId);
        return reviewRepository.save(review);
    }
}
