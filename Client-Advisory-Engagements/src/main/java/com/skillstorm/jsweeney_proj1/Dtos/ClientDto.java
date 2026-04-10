package com.skillstorm.jsweeney_proj1.Dtos;

import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.networthRange;

public class ClientDto {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private tier tier;
    private networthRange estNetWorth;
    private double annualFeeObligation;
    private double serviceCount;


    // must take in strings from DB and convert to enums
    public ClientDto(Long clientId, String firstName, String lastName, String email, String phone, String tier, String estNetWorth, double annualFeeObligation, double serviceCount) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.tier = Client.tier.valueOf(tier);
        this.estNetWorth = Client.networthRange.valueOf(estNetWorth);
        this.annualFeeObligation = annualFeeObligation;
        this.serviceCount = serviceCount;
    }

    public static ClientDto convertToDto(Client client) {
        return new ClientDto(client.getClientId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone(), 
                                client.getTier().toString(), client.getEstNetWorth().toString(), 0L, 0L);
    }

    public static Client convertToDto(ClientDto client) {
        return new Client(client.getClientId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone(), 
                                client.getTier(), client.getEstNetWorth());
    }



    public Long getClientId() {
        return clientId;
    }



    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }



    public String getFirstName() {
        return firstName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhone() {
        return phone;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public tier getTier() {
        return tier;
    }



    public void setTier(tier tier) {
        this.tier = tier;
    }



    public networthRange getEstNetWorth() {
        return estNetWorth;
    }



    public void setEstNetWorth(networthRange estNetWorth) {
        this.estNetWorth = estNetWorth;
    }



    public double getAnnualFeeObligation() {
        return annualFeeObligation;
    }



    public void setAnnualFeeObligation(double annualFeeObligation) {
        this.annualFeeObligation = annualFeeObligation;
    }

    public double getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(double serviceCount) {
        this.serviceCount = serviceCount;
    }

    
}
