package com.baldacchino_gambadoro.orders_management.ClassSerializable;


import java.io.Serializable;
import java.util.HashMap;

//Classe utilizzata per deserializzare i dati ricevuti sul topic orderupdates relativo alla chiave order_paid
public class OrderPaid  implements Serializable {
    private String orderId;
    private String userId;
    private double amountPaid;
    private HashMap<String, String> extraArgs;

    public OrderPaid(String orderId, String userId, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amountPaid = amount;
        this.extraArgs = new HashMap<String, String>();
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderPaid setOrderId(String orderId) {
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
        return amountPaid;
    }

    public OrderPaid setAmount(double amount) {
        this.amountPaid = amount;
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
