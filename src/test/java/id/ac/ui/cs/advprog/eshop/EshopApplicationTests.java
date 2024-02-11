package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    // Test class added ONLY to cover main() invocation not covered by application tests.
    @Test
    void main() {
        EshopApplication.main(new String[] {});
    }

}
