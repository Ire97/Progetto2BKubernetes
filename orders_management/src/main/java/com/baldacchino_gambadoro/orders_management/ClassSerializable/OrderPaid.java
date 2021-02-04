package com.baldacchino_gambadoro.orders_management.ClassSerializable;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.HashMap;

//Classe utilizzata per deserializzare i dati ricevuti sul topic orderupdates relativo alla chiave order_paid
public class OrderPaid  implements Serializable {
    private ObjectId orderId;
    private String userId;
    private double amount;
    private HashMap<String, String> extraArgs;

    public OrderPaid(ObjectId orderId, String userId, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.extraArgs = new HashMap<String, String>();
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public OrderPaid setOrderId(ObjectId orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OrderPaid setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public OrderPaid setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public HashMap<String, String> getExtraArgs() {
        return extraArgs;
    }

    public OrderPaid setExtraArgs(HashMap<String, String> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }
}
