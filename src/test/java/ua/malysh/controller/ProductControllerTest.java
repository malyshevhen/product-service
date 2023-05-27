package ua.malysh.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.malysh.domain.Category;
import ua.malysh.domain.Measure;
import ua.malysh.domain.Product;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private static final String URL = "/api/v1/products";

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnStatusCreated() throws Exception {
        long id = 1L;
        var newProduct = new Product("Test Product", Category.MEAT, Measure.KILOGRAM);

        when(productService.save(newProduct)).thenReturn(id);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnStatusOkAndReturnRetrievedProduct() throws Exception {
        long id = 1L;
        var name = "Test Product";
        var category = Category.MEAT;
        var measure = Measure.KILOGRAM;
        var retrievedProduct = new Product();
        retrievedProduct.setId(id);
        retrievedProduct.setName(name);
        retrievedProduct.setCategory(category);
        retrievedProduct.setMeasure(measure);

        when(productService.findById(id)).thenReturn(retrievedProduct);

        mockMvc.perform(get(URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("category").value(category.toString()))
                .andExpect(jsonPath("measure").value(measure.toString()))
                .andDo(print());
    }

    @Test
    void shouldReturnStatusOk() throws Exception {
        long id = 1L;

        when(productService.deleteById(id)).thenReturn(id);

        mockMvc.perform(delete(URL + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}