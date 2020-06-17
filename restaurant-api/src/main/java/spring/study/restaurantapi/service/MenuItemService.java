package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.MenuItem;
import spring.study.restaurantapi.domain.MenuItemRepository;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository){
        this.menuItemRepository=menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        update(restaurantId, menuItems);
    }

    private void update(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem:menuItems){
            if(menuItem.isDestroy()){
                menuItemRepository.deleteById(menuItem.getId());
                return;
            }

            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        }
    }
}
