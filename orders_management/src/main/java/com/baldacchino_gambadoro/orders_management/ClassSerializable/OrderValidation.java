package com.baldacchino_gambadoro.orders_management.ClassSerializable;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

//Classe utilizzata per deserializzare i dati ricevuti sul topic orderupdates relativo alla chiave order_validation
public class OrderValidation implements Serializable {

    private LocalDateTime timestamp;
    private Integer status;
    private ObjectId orderId;
    private List<String> extraArgs;

    public OrderValidation(LocalDateTime timestamp, Integer status, ObjectId orderId) {
        this.timestamp = timestamp;
        this.status = status;
        this.orderId = orderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderValidation setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public OrderValidation setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public OrderValidation setOrderId(ObjectId orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<String> getExtraArgs() {
        return extraArgs;
    }

    public OrderValidation setExtraArgs(List<String> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }
}
