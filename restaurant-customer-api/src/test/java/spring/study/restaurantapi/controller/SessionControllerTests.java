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
import spring.study.restaurantapi.service.EmailNotExistedException;
import spring.study.restaurantapi.service.PasswordWrongException;
import spring.study.restaurantapi.service.UserService;
import spring.study.restaurantapi.utils.JwtUtil;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;


    @Test
    public void createWithValidData() throws Exception {
        String email="dong@example.com";
        String password="test";
        Long id =123L;
        String name="dong";
        User mockUser=User.builder()
                .id(id)
                .name(name)
                .password("ACCESSTOKEN")
                .build();

        given(userService.authenticate(email,password)).willReturn(mockUser);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"dong@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"")))
                .andExpect(content().string(containsString(".")));

        verify(userService).authenticate(eq(email),eq(password));

    }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("dong@example.com","asd"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"dong@example.com\",\"password\":\"asd\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("dong@example.com"),eq("asd"));

    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("z@example.com","asd"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"z@example.com\",\"password\":\"asd\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("z@example.com"),eq("asd"));

    }
}