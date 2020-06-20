package spring.study.restaurantapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.restaurantapi.domain.MenuItem;
import spring.study.restaurantapi.domain.Restaurant;
import spring.study.restaurantapi.domain.RestaurantNotFoundException;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.service.RestaurantService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)

class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean//가짜 객체를 만들면서 repository 따로 안만들어도됨.
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant>restaurants=new ArrayList<>();
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        //가짜 객체 생성->@MockBean의 사용으로 controller만 테스트할 수 있게됨
        given(restaurantService.getRestaurants("Seoul",1L)).willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(
                        containsString("\"id\":1004")));

    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        MenuItem menuItem=MenuItem.builder()
                .name("Kimchi")
                .build();

        restaurant.setMenuItems(Arrays.asList(menuItem));

        Review review=Review.builder()
                .name("Dong")
                .score(3)
                .description("so-so")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("so-so")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(10000L))
                .willThrow(new RestaurantNotFoundException(10000L));

        mvc.perform(get("/restaurants/10000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}