package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProdcutServiceImplTest;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    public void setUp(){
        products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setProductId("ID" + i);
            product.setProductName("Product " + i);
            product.setProductQuantity(i * 10);
            products.add(product);
        }
        when(productService.findAll()).thenReturn(products);
    }

    @Test
    public void testProductListPage() throws Exception {
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    public void testProductList_HasCorrectModelDataCreated() throws Exception {
        mockMvc.perform(get("/product/list"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(100)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is("ID50")),
                                hasProperty("productName", is("Product 50")),
                                hasProperty("productQuantity", is(500))
                        )
                )));
        verify(productService, times(1)).findAll();
    }
    @Test
    public void testProductList_HasCorrectModelDataNotCreated() throws Exception {
        List<Product> mockProducts = Arrays.asList();
        when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(get("/product/list"))
                .andExpect(model().attribute("products", mockProducts))
                .andExpect(model().attribute("products", hasSize(0)));
    }

    @Test
    public void testProductList_HasDeletedProduct() throws Exception {
        Product deletedProduct = products.get(0);
        products.remove(0);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(model().attribute("products", products))
                .andExpect(model().attribute("products", hasSize(99)))
                .andExpect(model().attribute("products", not(hasItem(deletedProduct))));

        verify(productService, times(1)).findAll();
    }

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testCreateProductPage_Post() throws Exception {
        Product mockProduct = new Product();
        mockProduct.setProductName("Test Product");
        mockProduct.setProductQuantity(100);
        when(productService.create(any())).thenReturn(mockProduct);

        mockMvc.perform(post("/product/create")
                .param("productName", "Test Product")
                .param("productQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).create(argThat(product -> product.getProductName().equals("Test Product") && product.getProductQuantity() == 100));
    }

    @Test
    public void testCreateProduct_PostBadRequest() throws Exception {
        mockMvc.perform(post("/product/create")
                .param("productName", "Test Product")
                .param("productQuantity", "Wrong"))
                .andExpect(status().isBadRequest());

        verify(productService, times(0)).create(any());

    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/ID1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).deleteById("ID1");
    }

    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        mockMvc.perform(get("/product/delete/ID500"))
                        .andExpect(status().isNotFound());

        verify(productService, times(0)).deleteById("ID500");
    }
}
