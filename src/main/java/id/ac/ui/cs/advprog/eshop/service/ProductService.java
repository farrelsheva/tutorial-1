package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public void deleteById(String productId);
    public Product findById(String productId);
    public void editById(String productId, Product product);
    public List<Product> findAll();
}
