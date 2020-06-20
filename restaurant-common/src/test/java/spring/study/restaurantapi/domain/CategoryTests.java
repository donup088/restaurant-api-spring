package spring.study.restaurantapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTests {

    @Test
    public void create(){
        Category category=Category.builder().name("Korean Food").build();

        assertEquals(category.getName(),"Korean Food");
    }
}