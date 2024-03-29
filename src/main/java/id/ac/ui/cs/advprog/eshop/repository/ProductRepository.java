package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();



    //it was not specified in the module, but it is assumed that the ID is unique
    public Product create(Product product){
        productData.add(product);
        return product;
    }

    public Product deleteById(String productId){
        Iterator<Product> productIterator = productData.iterator();
        while(productIterator.hasNext()){
            Product product = productIterator.next();
            if(product.getProductId().equals(productId)){
                productIterator.remove();
                return product;
            }
        }
        return null;
    }

    public Product findById(String productId){
        for(Product product : productData){
            if(product.getProductId().equals(productId)){
                return product;
            }
        }
        return null;
    }

    public void editById(String productId, Product product){
        for(int i = 0; i < productData.size(); i++){
            if(productData.get(i).getProductId().equals(productId)){
                productData.set(i, product);
                return;
            }
        }
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}