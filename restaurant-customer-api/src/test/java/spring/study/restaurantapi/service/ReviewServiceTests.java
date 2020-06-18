package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.domain.ReviewRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
    public void addReview(){
        Review review=Review.builder()
                .name("Dong")
                .score(5)
                .description("맛있어요")
                .build();

        reviewService.addReview(123L,review);

        verify(reviewRepository).save(any());
    }
}