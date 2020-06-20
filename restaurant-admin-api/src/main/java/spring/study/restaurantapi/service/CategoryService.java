package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.restaurantapi.domain.Category;
import spring.study.restaurantapi.domain.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        Category category=Category.builder().name(name).build();
        categoryRepository.save(category);

        return category;
    }
}
