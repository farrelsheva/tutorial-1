package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProdcutServiceImplTest;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        // for findById
        Product specificProduct = products.get(1);
        when(productService.findById("ID1")).thenReturn(specificProduct);
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
        doThrow(new NoSuchElementException("Product with id ID500 not found")).when(productService).deleteById("ID500");
        mockMvc.perform(get("/product/delete/ID500"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/product/list"))
                        .andExpect(flash().attributeExists("errorMessage"));
        verify(productService, times(1)).deleteById("ID500");
    }

    @Test
    public void testEditProductPage() throws Exception{

        mockMvc.perform(get("/product/edit/ID1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));

        // the getmapping should call findById to get the product
        verify(productService, times(1)).findById("ID1");
    }

    @Test
    public void testEditProductPage_Post() throws Exception {
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        String productId = "ID1";
        String productName = "Test Product";
        int productQuantity = 10000;

        mockMvc.perform(post("/product/edit/{productId}", productId)
                        .param("productName", productName)
                        .param("productQuantity", String.valueOf(productQuantity)))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/product/list"));


        verify(productService, times(1)).editById(eq(productId), productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();
        assertEquals(productName, capturedProduct.getProductName());
        assertEquals(productQuantity, capturedProduct.getProductQuantity());
    }

    @Test
    public void testEditProductPage_PostBadRequest() throws Exception {
        mockMvc.perform(post("/product/edit/ID1")
                .param("productName", "Test Product")
                .param("productQuantity", "Wrong"))
                .andExpect(status().isBadRequest());

        verify(productService, times(0)).editById(any(), any());
    }

    @Test
    public void testEditProductPage_PostNotFound() throws Exception {
         String nullProductId = "ID500";
         Product mockProduct = new Product();
         mockProduct.setProductName("Test Product");
         mockProduct.setProductQuantity(100);

         when(productService.findById(nullProductId)).thenReturn(null);

         mockMvc.perform(post("/product/edit/{productId}", nullProductId)
                        .param("productName", "Test Product")
                        .param("productQuantity", "100"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/product/list"));

         verify(productService, never()).editById(anyString(), any());

    }

}
