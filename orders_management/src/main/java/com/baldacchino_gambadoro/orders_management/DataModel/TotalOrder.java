package com.baldacchino_gambadoro.orders_management.DataModel;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//Classe che permette di gestire una lista di ordini
@Document(collection = "TotalOrder")
public class TotalOrder {

    @Id
    private ObjectId _id;

    private String userId;

    private List<Order> orders;

    private String addressShipment;

    private String addressBilling;

    private double amount;

    private String status;

    public TotalOrder(ObjectId _id, String userId, double amount, String status) {
        this._id = _id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public TotalOrder setStatus(String status) {
        this.status = status;
        return this;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getAddressShipment() {
        return addressShipment;
    }

    public void setAddressShipment(String addressShipment) {
        this.addressShipment = addressShipment;
    }

    public String getAddressBilling() {
        return addressBilling;
    }

    public void setAddressBilling(String addressBilling) {
        this.addressBilling = addressBilling;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
