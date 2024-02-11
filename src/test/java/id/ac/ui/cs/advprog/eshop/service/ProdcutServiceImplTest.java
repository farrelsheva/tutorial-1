package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@ExtendWith(MockitoExtension.class)
public class ProdcutServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    //generate products for testing
    public static List<Product> generateProducts(int numberOfProducts) {
        return IntStream.range(0, numberOfProducts).mapToObj(i -> {
            Product product = new Product();
            product.setProductId("ID" + i);
            product.setProductName("Product " + i);
            product.setProductQuantity(i * 10); // Example logic for quantity
            return product;
        }).collect(Collectors.toList());
    }

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

    @Test
    public void testFindProductbyId(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sambo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.findById(any())).thenReturn(product);

        Product foundProduct = productServiceImpl.findById(product.getProductId());

        assertNotNull(foundProduct, "must not be null" );
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
        assertEquals(product.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    public void testDeleteProductByIdSucess() {
        String existingProductId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product existingProduct = new Product();
        existingProduct.setProductId(existingProductId);

        when(productRepository.deleteById(existingProductId)).thenReturn(existingProduct);

        productServiceImpl.deleteById(existingProductId);


        verify(productRepository, times(1)).deleteById(existingProductId);

    }

    @Test
    public void testDeleteProductById_NonExistent() {
        String nonexistentProductId = "nonexistent-product-id";

        when(productRepository.deleteById(nonexistentProductId)).thenReturn(null);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productServiceImpl.deleteById(nonexistentProductId);
        });

        assertTrue(exception.getMessage().contains("Product with id " + nonexistentProductId + " not found"));
        verify(productRepository, times(1)).deleteById(nonexistentProductId);
    }


    @Test
    void testEditProductById() {
        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sambo Cap Bambang");
        editedProduct.setProductQuantity(100);

        Product newValues = new Product();
        newValues.setProductId(editedProduct.getProductId()); //because of the nature of edit in the app, ID will not be changed
        newValues.setProductName("Sambo Cap Usep");
        newValues.setProductQuantity(200);

        productServiceImpl.editById(editedProduct.getProductId(), newValues);

        // Use ArgumentCaptor to capture the product passed to editById
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).editById(eq(editedProduct.getProductId()), productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();

        // Assert that the captured product has the updated values
        assertEquals(newValues.getProductName(), capturedProduct.getProductName());
        assertEquals(newValues.getProductQuantity(), capturedProduct.getProductQuantity());
        assertEquals(editedProduct.getProductId(), capturedProduct.getProductId());
    }


    @Test
    public void testFindAll() {
        // Define test data. 100 products
        List<Product> expectedProducts = generateProducts(100);

        // Simulate findAll
        when(productRepository.findAll()).thenReturn(expectedProducts.iterator());

        // Invoke the method under test
        List<Product> actualProducts = productServiceImpl.findAll();

        // Assert correctness
        assertEquals(expectedProducts.size(), actualProducts.size(), "The size of the product lists should match.");
        for (int i = 0; i < expectedProducts.size(); i++) {
            assertEquals(expectedProducts.get(i), actualProducts.get(i), "The products should match.");
        }

        // Verify interactions
        verify(productRepository, times(1)).findAll();
    }

}
