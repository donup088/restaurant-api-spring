package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.MenuItem;
import spring.study.restaurantapi.domain.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTests {

    private MenuItemService menuItemService;

    @Mock
    MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        menuItemService=new MenuItemService(menuItemRepository);
    }

    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems=new ArrayList<>();

        menuItems.add(MenuItem.builder().name("coffee").build());
        menuItems.add(MenuItem.builder().id(1L).name("latte").build());
        menuItems.add(MenuItem.builder().id(123L).destroy(true).build());

        menuItemService.bulkUpdate(1L,menuItems);

        verify(menuItemRepository,times(2)).save(any());
        verify(menuItemRepository).deleteById(eq(123L));
    }

}