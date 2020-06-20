package spring.study.restaurantapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.restaurantapi.domain.Category;
import spring.study.restaurantapi.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void list() throws Exception {
        List<Category> categories=new ArrayList<>();
        categories.add(Category.builder().name("coffee").build());

        given(categoryService.getCategories()).willReturn(categories);

        mvc.perform(get("/categories"))
                .andExpect(content().string(containsString("coffee")))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"coffee\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));

        verify(categoryService).addCategory("coffee");
    }

}