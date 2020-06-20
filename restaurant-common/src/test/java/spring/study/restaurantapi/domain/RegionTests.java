package spring.study.restaurantapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionTests {
    @Test
    public void create(){
        Region region=Region.builder().name("서울").build();

        assertEquals(region.getName(),"서울");
    }

}