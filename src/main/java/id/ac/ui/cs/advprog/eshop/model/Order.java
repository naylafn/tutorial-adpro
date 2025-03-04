package id.ac.ui.cs.advprog.eshop.model;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    @Setter
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    public Order (String id, List <Product> products, Long orderTime, String author, String status) {
    }
}
