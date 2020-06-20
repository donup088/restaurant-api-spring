package spring.study.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.study.restaurantapi.domain.Region;
import spring.study.restaurantapi.service.RegionService;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/regions")
    public ResponseEntity<?> create(@RequestBody Region resource) throws URISyntaxException {
        String url="/regions/"+resource.getId();

        regionService.addRegion(resource.getName());

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
