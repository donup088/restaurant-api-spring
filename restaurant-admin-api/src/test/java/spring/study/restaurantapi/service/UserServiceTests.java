package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
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
    public void getUsers(){
        List<User> mockUsers=new ArrayList<>();
        mockUsers.add(User.builder()
                .name("dong")
                .level(1L)
                .email("d@e.com")
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users=userService.getUsers();

        User user=users.get(0);

        assertEquals(user.getName(),"dong");
    }

    @Test
    public void addUser(){
        String email="ad@example.com";
        String name="Admin";
        User mockUser=User.builder().email(email).name(name).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user=userService.addUser(email,name);

        assertEquals(user.getName(),"Admin");
    }

    @Test
    public void updateUser(){
        Long id=123L;
        String email="ad@example.com";
        String name="Dong";
        Long level=1L;

        User mockUser=User.builder().id(id).name("Admin").level(100L).build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user=userService.updateUser(id,email,name,level);

        verify(userRepository).findById(eq(id));

        assertEquals(user.getName(),"Dong");
        assertEquals(user.isAdmin(),false);
    }

    @Test
    public void deactivateUser(){

        User mockUser=User.builder()
                .id(123L)
                .email("ad@example.com")
                .name("Dong")
                .level(100L)
                .build();

        given(userRepository.findById(123L)).willReturn(Optional.of(mockUser));

        User user=userService.deactivateUser(123L);

        verify(userRepository).findById(123L);

        assertEquals(user.isAdmin(),false);

        assertEquals(user.isActive(),false);


    }
}