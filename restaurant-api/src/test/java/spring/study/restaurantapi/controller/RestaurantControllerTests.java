package spring.study.restaurantapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.restaurantapi.domain.*;
import spring.study.restaurantapi.service.RestaurantService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        //가짜 객체 생성->@MockBean의 사용으로 controller만 테스트할 수 있게됨
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
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

    @Test
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant=invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Dong\",\"address\":\"Seoul\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/333")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Sea food\",\"address\":\"강남\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(333L,"Sea food","강남");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/333")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }
}