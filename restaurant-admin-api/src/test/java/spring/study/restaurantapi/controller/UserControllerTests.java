package spring.study.restaurantapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users=new ArrayList<>();
        users.add(User.builder()
                .name("Dong")
                .email("d@example.com")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(content().string(containsString("Dong")))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        String email="admin@example.com";
        String name="Admin";

        User user=User.builder()
                .email(email)
                .name(name)
                .build();

        given(userService.addUser(email,name)).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@example.com\",\"name\":\"Admin\"}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(email,name);
    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/users/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@example.com\",\"name\":\"Admin\",\"level\":100}"))
                .andExpect(status().isOk());

        Long id=123L;
        String email="admin@example.com";
        String name="Admin";
        Long level=100L;

        verify(userService).updateUser(eq(id),eq(email),eq(name),eq(level));
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/123"))
                .andExpect(status().isOk());

        verify(userService).deactivateUser(123L);
    }
}