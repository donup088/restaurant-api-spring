package spring.study.restaurantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.restaurantapi.domain.Category;
import spring.study.restaurantapi.domain.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class CategoryServiceTests {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        categoryService=new CategoryService(categoryRepository);
    }

    @Test
    public void getCategories(){
        List<Category> mockCategories=new ArrayList<>();
        mockCategories.add(Category.builder().name("Coffee").build());

        given(categoryRepository.findAll()).willReturn(mockCategories);

        List<Category> categories=categoryService.getCategories();

        Category category=categories.get(0);

        assertEquals(category.getName(),"Coffee");
    }

}