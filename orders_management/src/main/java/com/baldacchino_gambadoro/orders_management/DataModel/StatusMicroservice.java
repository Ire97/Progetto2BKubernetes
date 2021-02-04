package com.baldacchino_gambadoro.orders_management.DataModel;

//Classe utilizzata per gestire il ping
public class StatusMicroservice {
    private String serviceStatus;
    private String dbStatus;

    public StatusMicroservice(String serviceStatus, String dbStatus) {
        this.serviceStatus = serviceStatus;
        this.dbStatus = dbStatus;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }
}
