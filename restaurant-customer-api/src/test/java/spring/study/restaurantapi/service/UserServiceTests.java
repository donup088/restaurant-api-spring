package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.domain.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService=new UserService(userRepository);
    }

    @Test
    public void registerUser(){
        String email="dong@example.com";
        String name="dong";
        String password="test";

        userService.registerUser(email,name,password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail(){
        String email="dong@example.com";
        String name="dong";
        String password="test";

        User user= User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        assertThatThrownBy(() -> {
            userService.registerUser(email,name,password);
        }).isInstanceOf(EmailExistedException.class);

        verify(userRepository,never()).save(any());
    }

}