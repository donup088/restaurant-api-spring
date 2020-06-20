package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.Region;
import spring.study.restaurantapi.domain.RegionRepository;

import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository=regionRepository;
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();

    }
}
