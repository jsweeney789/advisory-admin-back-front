package com.skillstorm.jsweeney_proj1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.skillstorm.jsweeney_proj1.models.*;
import com.skillstorm.jsweeney_proj1.models.Client.networthRange;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;



/**
 * As we try to do test-driven development I will write some basic tests for the creation of the Java Objects required by my CRUD app first
 * these are Client - Advisory Service - Engagement
 */
@SpringBootTest
public class JavaModelTests {

    @Test
    @DisplayName("Client Object Creation Test")
    public void clientCreationTest() {
        Client client = new Client(1L, "Jacob", "Sweeney", "jsweeney@skillstorm.com", 
                                    "+1-123-456-7890", tier.PREMIUM, 250_000.00);
        assertNotNull(client);
        assertEquals(client.getClientId(), 1);
        assertEquals(client.getFirstName(), "Jacob");
        assertEquals(client.getLastName(), "Sweeney");
        assertEquals(client.getEmail(), "jsweeney@skillstorm.com");
        assertEquals(client.getPhone(), "+1-123-456-7890");
        assertEquals(client.getTier(), tier.PREMIUM);
        assertEquals(client.getEstNetWorth(), networthRange.UNDER_500K);
    }

    @Test
    @DisplayName("Advisory Object Creation Test")
    public void advisoryCreationTest() {
        Advisory advisory = new Advisory(2L, "Business Advisory LLC", serviceType.TAX_PLANNING, deliveryFormatOptions.VIRTUAL, 1_000.00);
        assertNotNull(advisory);
        assertEquals(advisory.getAdvisoryId(), 2);
        assertEquals(advisory.getName(), "Business Advisory LLC");
        assertEquals(advisory.getServiceType(), "TAX_PLANNING");
        assertEquals(advisory.getDeliveryFormat(), "VIRTUAL");
        assertEquals(advisory.getAnnualFee(), 1000.00);
    }
    
    @Test
    @DisplayName("Engagement Service Object Creation Test")
    public void engagementCreationTest() {
        LocalDate exampleDate = LocalDate.parse("2026-03-25");

        Advisory advisory = new Advisory(2L, "Business Advisory LLC", serviceType.TAX_PLANNING, deliveryFormatOptions.VIRTUAL, 1_000.00);

        Client client = new Client(1L, "Jacob", "Sweeney", "jsweeney@skillstorm.com", 
                                    "+1-123-456-7890", tier.STANDARD, 250_000.00);

        Engagement engagement = new Engagement(1L, client, advisory, exampleDate);
        
        assertNotNull(engagement);
        assertEquals(engagement.getEngagementId(), 1);
        assertEquals(engagement.getClient(), client);
        assertEquals(engagement.getAdvisory(), advisory);
        assertEquals(engagement.getStartDate(), exampleDate);
        assertEquals(engagement.getStatus(), "ACTIVE");
    }
}
