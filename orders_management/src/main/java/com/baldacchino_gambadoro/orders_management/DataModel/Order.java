package com.baldacchino_gambadoro.orders_management.DataModel;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Order")
public class Order {

    private Integer product_id;
    private Integer quantity;
    private double price;

    public Integer getProduct_id() {
        return product_id;
    }

    public Order setProduct_id(Integer product_id) {
        this.product_id = product_id;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
