package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTests {

    private RestaurantService restaurantService;
    //실제 테스트 하려는 것이 아니면 가짜 객체를 넣어줌 @Mock 사용
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        //Mock Annotation이 붙은 곳에 올바른 객체를 초기화 시켜줌
        MockitoAnnotations.initMocks(this);

        MockRestaurantRepository();
        MockMenuItemRepository();

        restaurantService=new RestaurantService(
                restaurantRepository,menuItemRepository);
    }

    private void MockMenuItemRepository() {
        List<MenuItem> menuItems=new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));
        //아래 test가 실행될 떄 사용하는 repository의 모든 함수를 가짜객체로 만들어 넣어줘야함
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void MockRestaurantRepository() {
        List<Restaurant> restaurants=new ArrayList<>();
        Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurant(){
        Restaurant restaurant=restaurantService.getRestaurant(1004L);
        MenuItem menuItem=restaurant.getMenuItems().get(0);

        assertEquals(menuItem.getName(),"Kimchi");

        assertEquals(restaurant.getId(),1004L);
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants=restaurantService.getRestaurants();
        Restaurant restaurant=restaurants.get(0);

        assertEquals(restaurant.getId(),1004L);
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant=new Restaurant("Dong","Seoul");
        Restaurant saved=new Restaurant(123L,"Dong","Seoul");

        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant newRestaurant=restaurantService.addRestaurant(restaurant);

        assertEquals(newRestaurant.getId(),123L);
    }
    @Test
    public void updateRestaurant(){
        Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L,"Sea Food","부산");

        assertEquals(restaurant.getName(),"Sea Food");
        assertEquals(restaurant.getAddress(),"부산");
    }
}