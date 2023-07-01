package ua.malysh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import ua.malysh.domain.Category;
import ua.malysh.domain.NutritionalValue;
import ua.malysh.domain.Product;
import ua.malysh.service.ProductService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private static final String URL = "/api/v1/products";

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Product product;

    @BeforeAll
    static void setup() {
        product = new Product("Test Product", Category.MEAT);

        var nutritionalValue = new NutritionalValue();
        nutritionalValue.setEnergie(300D);
        nutritionalValue.setCarbohydrates(50D);
        nutritionalValue.setProtein(20D);
        nutritionalValue.setFat(15D);

        product.setNutritionalValue(nutritionalValue);
    }

    @Test
    void shouldReturnStatusCreated() throws Exception {
        long id = 1L;

        when(productService.save(product)).thenReturn(id);

        mockMvc.perform(post(URL)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnStatusOkAndReturnRetrievedProduct() throws Exception {
        long id = 1L;

        var retrievedProduct = product;
        retrievedProduct.setId(id);

        when(productService.findById(id)).thenReturn(retrievedProduct);

        mockMvc.perform(get(URL + "/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("category").value(product.getCategory().toString()))
                .andDo(print());
    }

    @Test
    void shouldReturnStatusOk() throws Exception {
        long id = 1L;

        when(productService.deleteById(id)).thenReturn(id);

        mockMvc.perform(delete(URL + "/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldReturnStatusForbidden() throws Exception {
        long id = 1L;

        when(productService.deleteById(id)).thenReturn(id);

        mockMvc.perform(delete(URL + "/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}