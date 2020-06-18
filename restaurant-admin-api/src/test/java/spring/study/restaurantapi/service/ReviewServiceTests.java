package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.domain.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class ReviewServiceTests {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reviewService=new ReviewService(reviewRepository);
    }
    @Test
    public void getReviews(){
        List<Review> mockReviews=new ArrayList<>();
        mockReviews.add(Review.builder().description("GREAT!").build());

        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews=reviewService.getReviews();

        assertEquals(reviews.get(0).getDescription(),"GREAT!");
    }
}