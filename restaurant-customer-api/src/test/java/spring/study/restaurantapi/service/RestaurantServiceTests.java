package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTests {

    private RestaurantService restaurantService;
    //실제 테스트 하려는 것이 아니면 가짜 객체를 넣어줌 @Mock 사용
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        //Mock Annotation이 붙은 곳에 올바른 객체를 초기화 시켜줌
        MockitoAnnotations.initMocks(this);

        MockRestaurantRepository();
        MockMenuItemRepository();
        MockReviewRepository();

        restaurantService=new RestaurantService(
                restaurantRepository,menuItemRepository,reviewRepository);
    }

    private void MockMenuItemRepository() {
        List<MenuItem> menuItems=new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());
        //아래 test가 실행될 때 사용하는 repository의 모든 함수를 가짜객체로 만들어 넣어줘야함
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void MockRestaurantRepository() {
        List<Restaurant> restaurants=new ArrayList<>();
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAllByAddressContainingAndCategoryId("Seoul",1L))
                .willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }
    private void MockReviewRepository(){
        List<Review> reviews=new ArrayList<>();
        reviews.add(Review.builder()
                    .name("Kim")
                    .score(4)
                    .description("good")
                    .build());
        given(reviewRepository.findAllByRestaurantId(1004L))
                .willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant=restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));

        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        MenuItem menuItem=restaurant.getMenuItems().get(0);

        Review review=restaurant.getReviews().get(0);

        assertEquals(menuItem.getName(),"Kimchi");

        assertEquals(restaurant.getId(),1004L);

        assertEquals(review.getScore(),4);
    }

    @Test
    public void getRestaurantWithNotExisted(){
    assertThatThrownBy(() -> {
        restaurantService.getRestaurant(10000L);
    }).isInstanceOf(RestaurantNotFoundException.class);
}

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants=restaurantService.getRestaurants("Seoul", 1L);

        Restaurant restaurant=restaurants.get(0);

        assertEquals(restaurant.getId(),1004L);
    }

}