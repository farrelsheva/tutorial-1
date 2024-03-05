package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();
    public Order save(Order order){
        int i = 0;
        for (Order saved: orderData){
            if (saved.getId().equals(order.getId())){
                orderData.set(i, order);
                return order;
            }
            i += 1;
        }

        orderData.add(order);
        return order;
    }

    public Order findById(String Id){
        for (Order saved: orderData){
            if (saved.getId().equals(Id)){
                return saved;
            }
        }
        return null;
    }

    public List<Order> findAllByAuthor(String author){
        List<Order> result = new ArrayList<>();
        for (Order saved: orderData){
            if (saved.getAuthor().equals(author)){
                result.add(saved);
            }
        }
        return result;
    }

}
