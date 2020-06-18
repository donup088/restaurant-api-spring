package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.study.restaurantapi.domain.MenuItem;
import spring.study.restaurantapi.service.MenuItemService;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> bulkUpdate(
            @PathVariable("restaurantId")Long restaurantId,
            @RequestBody List<MenuItem> menuItems){
        menuItemService.bulkUpdate(restaurantId,menuItems);
        return null;
    }
    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list(@PathVariable("restaurantId")Long restaurantId){
        return menuItemService.getMenuItems(restaurantId);
    }
}
