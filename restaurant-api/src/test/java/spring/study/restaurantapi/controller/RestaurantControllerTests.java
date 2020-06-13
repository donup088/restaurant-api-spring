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
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));
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
    public void detail() throws Exception {

        Restaurant restaurant1=new Restaurant(1004L,"Bob zip","Seoul");
        Restaurant restaurant2=new Restaurant(333L,"Sool zip","Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(333L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(containsString("Kimchi")));
        mvc.perform(get("/restaurants/333"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"Sool zip\"")))
                .andExpect(content().string(
                        containsString("\"id\":333")));
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"name\":\"Dong\",\"address\":\"Seoul\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/null"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }
}