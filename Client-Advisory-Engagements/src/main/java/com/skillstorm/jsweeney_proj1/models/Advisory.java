package com.skillstorm.jsweeney_proj1.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="advisory_service")
public class Advisory {
    // again, unsure for now if the enums make sense or not
    // using for now because these seem great for dropdown menus in frontend
    public enum serviceType{BUDGETING, CASH_FLOW_ANALYSIS, DEBT_MANAGEMENT, ESTATE_PLANNING, 
        INVESTMENT_MANAGEMENT, RETIREMENT_PLANNING, RISK_MNGMENT_INSURANCE, TAX_PLANNING};
    public enum deliveryFormatOptions{IN_PERSON, VIRTUAL, HYBRID};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="advisory_service_id")
    private Long advisoryId;

    @NotBlank
    @Column(name="service_name")
    private String name;

    @NotNull
    @Column(name="service_type")
    @Enumerated(EnumType.STRING)
    private serviceType serviceType;

    @NotNull
    @Column(name="delivery_format")
    @Enumerated(EnumType.STRING)
    private deliveryFormatOptions deliveryFormat;

    @NotNull
    @PositiveOrZero 
    @Column(name="annual_fee")
    private double annualFee;

    // db has this set as not null but with the default handling assuming its true just like our constructor, so I removed the @NotNull from here
    // even though it doesn't really make sense for a column to be neither active nor inactive, it is just enforced in the db not here
    @Column(name="is_active_status")
    private boolean active;

    @OneToMany(targetEntity = Engagement.class, mappedBy = "advisory")
    Set<Engagement> engagements;

    // this constructor is overloaded with an optional isActive param. For now we assume it's active on creation
    public Advisory(Long advisoryId, String name, serviceType service,
                deliveryFormatOptions deliveryFormat, double annualFee) {
        this.advisoryId = advisoryId;
        this.name = name;
        this.serviceType = service;
        this.deliveryFormat = deliveryFormat;
        this.annualFee = annualFee;
        this.active = true;
    }

    // with explicit isActive assignemnt
    public Advisory(Long advisoryId, String name, serviceType service,
            deliveryFormatOptions deliveryFormat, double annualFee, boolean isActive) {
        this.advisoryId = advisoryId;
        this.name = name;
        this.serviceType = service;
        this.deliveryFormat = deliveryFormat;
        this.annualFee = annualFee;
        this.active = isActive;
    }

    public Advisory() {
    }

    public Long getAdvisoryId() {
        return advisoryId;
    }


    public void setAdvisoryServiceId(Long advisoryServiceId) {
        this.advisoryId = advisoryServiceId;
    }


    public String getName() {
        return name;
    }


    public void setName(String serviceName) {
        this.name = serviceName;
    }


    public String getServiceType() {
        return serviceType.toString();
    }


    public void setServiceType(serviceType serviceType) {
        this.serviceType = serviceType;
    }


    public String getDeliveryFormat() {
        return deliveryFormat.toString();
    }


    public void setDeliveryFormat(deliveryFormatOptions deliveryFormat) {
        this.deliveryFormat = deliveryFormat;
    }


    public double getAnnualFee() {
        return annualFee;
    }


    public void setAnnualFee(double annualFee) {
        this.annualFee = annualFee;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (advisoryId ^ (advisoryId >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
        result = prime * result + ((deliveryFormat == null) ? 0 : deliveryFormat.hashCode());
        long temp;
        temp = Double.doubleToLongBits(annualFee);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Advisory other = (Advisory) obj;
        if (advisoryId != other.advisoryId)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (serviceType != other.serviceType)
            return false;
        if (deliveryFormat != other.deliveryFormat)
            return false;
        if (Double.doubleToLongBits(annualFee) != Double.doubleToLongBits(other.annualFee))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Advisory [advisoryId=" + advisoryId + ", name=" + name + ", serviceType=" + serviceType
                + ", deliveryFormat=" + deliveryFormat + ", annualFee=" + annualFee + ", active=" + active + "]";
    }
    
}
