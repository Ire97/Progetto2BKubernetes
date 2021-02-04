package com.baldacchino_gambadoro.orders_management.ClassSerializable;

import com.baldacchino_gambadoro.orders_management.DataModel.Order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

//Classe utilizzata per serializzare l'invio su topic orderupdates in caso di post con successo.
public class OrderCompleted implements Serializable {

    private String orderId;
    private List<Order> products;
    private double total;
    private String shippingAddress;
    private String billingAddress;
    private String userId;
    private HashMap<String, String> extraArgs;

    public OrderCompleted(String orderId, List<Order> products, double total, String shippingAddress, String billingAddress, String userId) {
        this.orderId = orderId;
        this.products = products;
        this.total = total;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.userId = userId;
        this.extraArgs = new HashMap<String, String>();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Order> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getUserId() {
        return userId;
    }

    public HashMap<String, String> getExtraArgs() {
        return extraArgs;
    }

    public OrderCompleted setExtraArgs(HashMap<String, String> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }
}
