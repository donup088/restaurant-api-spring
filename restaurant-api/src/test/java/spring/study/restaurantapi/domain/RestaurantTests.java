package spring.study.restaurantapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {

    @Test
    public void create(){
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        assertEquals(restaurant.getId(),1004L);
        assertEquals(restaurant.getName(),"Bob zip");
        assertEquals(restaurant.getAddress(),"Seoul");
    }

    @Test
    public void information(){
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        assertEquals(restaurant.getInformation(),"Bob zip in Seoul");

    }

}