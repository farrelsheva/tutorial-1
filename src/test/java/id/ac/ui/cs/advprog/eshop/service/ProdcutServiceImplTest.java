package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ProdcutServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    public void testCreateFirstProduct() {
        Product product = new Product();
        product.setProductName("Sambo Cap Bambang");
        product.setProductQuantity(100);
        // Assume the repository just returns the product; no ID assignment simulation in mock
        //expect the repository to return the product
        when(productRepository.create(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            return p;
        });
        assertNull(product.getProductId(), "The ID should be null before creation");

        Product createdProduct = productServiceImpl.create(product);

        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertNotNull(createdProduct.getProductId(), "The ID should not be null after creation");
        assertEquals("Sambo Cap Bambang", createdProduct.getProductName());
        assertEquals(100, createdProduct.getProductQuantity());
        // 1 is the default value of productId
        assertEquals(1, Integer.parseInt(createdProduct.getProductId()));
    }




}
