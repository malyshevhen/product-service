package ua.malysh;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///test_db")
class ProductServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
