package com.skillstorm.jsweeney_proj1.Dtos;

import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;

public class AdvisoryDto {
    private Long advisoryId;
    private String name;
    private serviceType serviceType;
    private deliveryFormatOptions deliveryFormat;
    private boolean active;
    private double annualFee;
    private long numClients;
    
    public AdvisoryDto(Long advisoryServiceId, String serviceName,
            String serviceType, String deliveryFormat,
            boolean active, double annualFee, long numClients) {
        this.advisoryId = advisoryServiceId;
        this.name = serviceName;
        this.serviceType = Advisory.serviceType.valueOf(serviceType);
        this.deliveryFormat = deliveryFormatOptions.valueOf(deliveryFormat);
        this.active = active;
        this.annualFee = annualFee;
        this.numClients = numClients;
    }

    public Long getAdvisoryId() {
        return advisoryId;
    }

    public void setAdvisoryId(Long advisoryServiceId) {
        this.advisoryId = advisoryServiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String serviceName) {
        this.name = serviceName;
    }

    public serviceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(serviceType serviceType) {
        this.serviceType = serviceType;
    }

    public deliveryFormatOptions getDeliveryFormat() {
        return deliveryFormat;
    }

    public void setDeliveryFormat(deliveryFormatOptions deliveryFormat) {
        this.deliveryFormat = deliveryFormat;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(double annualFee) {
        this.annualFee = annualFee;
    }

    public long getNumClients() {
        return numClients;
    }

    public void setNumClients(long numClients) {
        this.numClients = numClients;
    }
}

