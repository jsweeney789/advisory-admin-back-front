package com.skillstorm.jsweeney_proj1.Dtos;

import java.time.LocalDate;

import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Engagement.engagementStatus;

// this is not yet being used as a full engagement Dto, it's actually just meant for the client detail view
public class EngagementDto {
    private Long engagementId;
    private String advisoryName;
    private LocalDate startDate;
    private engagementStatus status;
    private double annualFee;
    private String clientFirst;
    private String clientLast;
    private String clientPhone;
    private String clientEmail;
    private tier clientTier;

    public EngagementDto() {}
    
    public EngagementDto(Long engagementId, String advisoryName, java.sql.Date startDate, String status, double annualFee) {
        this.engagementId = engagementId;
        this.advisoryName = advisoryName;
        this.startDate = startDate.toLocalDate();
        this.status = engagementStatus.valueOf(status);
        this.annualFee = annualFee;
    }

    


    public EngagementDto(Long engagementId, String clientFirst, String clientLast, String clientPhone,
            String clientEmail, String clientTier) {
        this.engagementId = engagementId;
        this.clientFirst = clientFirst;
        this.clientLast = clientLast;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.clientTier = tier.valueOf(clientTier);
    }




    public Long getEngagementId() {
        return engagementId;
    }


    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }


    public String getAdvisoryName() {
        return advisoryName;
    }


    public void setAdvisoryName(String advisoryName) {
        this.advisoryName = advisoryName;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public engagementStatus getStatus() {
        return status;
    }


    public void setStatus(engagementStatus status) {
        this.status = status;
    }


    public double getAnnualFee() {
        return annualFee;
    }


    public void setAnnualFee(double annualFee) {
        this.annualFee = annualFee;
    }

    public String getClientFirst() {
        return clientFirst;
    }

    public void setClientFirst(String clientFirst) {
        this.clientFirst = clientFirst;
    }

    public String getClientLast() {
        return clientLast;
    }

    public void setClientLast(String clientLast) {
        this.clientLast = clientLast;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public tier getClientTier() {
        return clientTier;
    }

    public void setClientTier(tier clientTier) {
        this.clientTier = clientTier;
    }

    @Override
    public String toString() {
        return "EngagementDto [engagementId=" + engagementId + ", advisoryName=" + advisoryName + ", startDate="
                + startDate + ", status=" + status + ", annualFee=" + annualFee + ", clientFirst=" + clientFirst
                + ", clientLast=" + clientLast + ", clientPhone=" + clientPhone + ", clientEmail=" + clientEmail
                + ", clientTier=" + clientTier + "]";
    }

    

}
