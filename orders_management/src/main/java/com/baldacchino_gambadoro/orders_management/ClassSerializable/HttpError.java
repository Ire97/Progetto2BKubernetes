package com.baldacchino_gambadoro.orders_management.ClassSerializable;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

public class HttpError implements Serializable {

    private LocalDateTime timestamp;
    private String sourceIp;
    private String services;
    private String request;
    private Object error;

    //Costruttore per il messaggio di errore di tipo 40x
    public HttpError(HttpStatus status, HttpServletRequest request) {
        this.timestamp = LocalDateTime.now();
        this.sourceIp = request.getHeader("X-FORWARDED-FOR");
        if(this.sourceIp == null){
            this.sourceIp = request.getRemoteAddr();
        }
        this.services = "order_management_service";
        this.request = request.getRequestURI().concat(" " + request.getMethod());
        this.error = status.value();
    }

    //Costruttore per il messaggio di errore di tipo 50x
    public HttpError(HttpServletRequest request, Exception ex){
        this.timestamp = LocalDateTime.now();
        this.sourceIp = request.getHeader("X-FORWARDED-FOR");
        if(this.sourceIp == null){
            this.sourceIp = request.getRemoteAddr();
        }
        this.services = "order_management_service";
        this.request = request.getRequestURI().concat(" " + request.getMethod());
        this.error = ex.getStackTrace();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpError setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public HttpError setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }

    public String getServices() {
        return services;
    }

    public HttpError setServices(String services) {
        this.services = services;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public HttpError setRequest(String request) {
        this.request = request;
        return this;
    }

    public Object getError() {
        return error;
    }

    public HttpError setError(Object error) {
        this.error = error;
        return this;
    }
}
