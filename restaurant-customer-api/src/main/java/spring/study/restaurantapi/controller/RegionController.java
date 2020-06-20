package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.Region;
import spring.study.restaurantapi.service.RegionService;

import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){
        List<Region> regions=regionService.getRegions();
        return  regions;
    }
}
