package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    private int productId = 1;

    @Override
    public Product create(Product product){
        product.setProductId(String.valueOf(productId++));
        productRepository.create(product);
        return product;
    }

    public void deleteById(String productId){
        Product product = productRepository.deleteById(productId);
        if(product == null){
            throw new NoSuchElementException("Product with id " + productId + " not found");
        }
    }

    public Product findById(String productId){
        return productRepository.findById(productId);
    }

    public void editById(String productId, Product product){
        productRepository.editById(productId, product);
    }

    @Override
    public List<Product> findAll(){
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}
