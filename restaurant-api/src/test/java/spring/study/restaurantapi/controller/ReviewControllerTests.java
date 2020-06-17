package spring.study.restaurantapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.restaurantapi.domain.Review;
import spring.study.restaurantapi.service.ReviewService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    ReviewService reviewService;

    @Test
    public void createWithValidData() throws Exception {
        given(reviewService.addReview(eq(1L),any()))
                .willReturn(Review.builder()
                        .id(123L)
                        .build());

        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Dong\",\"score\":\"5\",\"description\":\"맛있어요\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1/reviews/123"));

        verify(reviewService).addReview(eq(1L),any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"score\":\"\",\"description\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(reviewService,never()).addReview(eq(1L),any());
    }
}