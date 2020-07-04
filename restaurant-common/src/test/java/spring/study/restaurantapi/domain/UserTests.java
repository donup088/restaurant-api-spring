package spring.study.restaurantapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void create(){
        User user=User.builder()
                .email("ddd@example.com")
                .level(1L)
                .name("dd")
                .build();

        assertEquals(user.getLevel(),1L);
        assertEquals(user.isAdmin(),false);
        assertEquals(user.isActive(),true);

        user.deactivate();

        assertEquals(user.isActive(),false);
    }


}