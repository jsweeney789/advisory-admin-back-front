package com.skillstorm.jsweeney_proj1.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "client")
public class Client {
    // I made these enums exist but I'm not sure if I want to use them yet, especially the range one
    public enum tier{STANDARD, PREMIUM, PRIVATE_BANKING}
    public enum networthRange{UNDER_500K, BETWEEN_500K_2M, BETWEEN_2M_10M, OVER_10M}

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private Long clientId;

    @NotBlank 
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Column(name="last_name")
    private String lastName;

    @NotBlank
    @Email
    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="phone")
    private String phone;

    @NotNull
    @Column(name="tier")
    @Enumerated(EnumType.STRING)
    private tier tier;

    @NotNull
    @Column(name="net_worth")
    @Enumerated(EnumType.STRING)
    @JsonProperty("estNetWorth") // helps jackson understand this enum as doing the mapping from double to enum made it confused in testing
    private networthRange clientNetWorth;

    @OneToMany(targetEntity = Engagement.class, mappedBy = "client")
    Set<Engagement> engagements;

    /**
     * Helper method that allows our constructor to receive a net worth estimation and then map it to
     * the ranges we care about
     * @param netWorth the estimated net worth of the client
     * @return the range that the client is in which we usually filter on
     */
    private static networthRange mapNetWorth(double netWorth) {
        if (netWorth < 500_000.00) {
            return networthRange.UNDER_500K;

        } else if (netWorth < 2_000_000) {
            return networthRange.BETWEEN_500K_2M;

        } else if (netWorth < 10_000_000) {
            return networthRange.BETWEEN_2M_10M;

        } else {
            return networthRange.OVER_10M;
        }
    }

    /**
     * Constructor method for the client object
     * @param firstName first name of client
     * @param lastName last name of client
     * @param email client's email address
     * @param phone client's contact phone number
     * @param clientTier the client's tier of service with us
     * @param netWorth the estimated net worth of the client
     */
    public Client(Long id, String firstName, String lastName, String email, String phone, tier clientTier,
            double netWorth) {
        this.clientId = id; // generate id somewhere somehow and store it
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.tier = clientTier;
        this.clientNetWorth = mapNetWorth(netWorth);
    }

    // this constructor more closely emulates what the frontend is actually doing for now, though I like having the capabilities of the above one as an option
    public Client(Long id, String firstName, String lastName, String email, String phone, tier clientTier,
            networthRange netWorth) {
        this.clientId = id; // generate id somewhere somehow and store it
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.tier = clientTier;
        this.clientNetWorth = netWorth;
    }
    
    public Client() {
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

    public void setTier(tier clientTier) {
        this.tier = clientTier;
    }

    public networthRange getEstNetWorth() {
        return clientNetWorth;
    }

    public void setClientNetWorth(networthRange clientNetWorth) {
        this.clientNetWorth = clientNetWorth;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (clientId ^ (clientId >>> 32));
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((tier == null) ? 0 : tier.hashCode());
        result = prime * result + ((clientNetWorth == null) ? 0 : clientNetWorth.hashCode());
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
        Client other = (Client) obj;
        if (clientId != other.clientId)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (tier == null) {
            if (other.tier != null)
                return false;
        } else if (!tier.equals(other.tier))
            return false;
        if (clientNetWorth != other.clientNetWorth)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", phone=" + phone + ", tier=" + tier + ", clientNetWorth=" + clientNetWorth + "]";
    }

    
}
