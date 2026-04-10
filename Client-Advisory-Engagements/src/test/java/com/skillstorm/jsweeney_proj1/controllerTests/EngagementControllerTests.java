package com.skillstorm.jsweeney_proj1.controllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.jsweeney_proj1.controllers.EngagementController;
import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Engagement;
import com.skillstorm.jsweeney_proj1.models.Engagement.engagementStatus;
import com.skillstorm.jsweeney_proj1.services.EngagementService;

@AutoConfigureMockMvc
@WebMvcTest(controllers = EngagementController.class)
public class EngagementControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EngagementService service;

    @Test
    @DisplayName("Test Engagement Controller POST")
    public void controllerPostEngagementTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);
        
        when(service.saveEngagement(exampleEngagement)).thenReturn(exampleEngagement);
        mockMvc.perform(post("/engagements").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleEngagement)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleEngagement)));
    }

    @Test
    @DisplayName("Test Engagement Controller GET")
    public void controllerGetEngagementTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);

        // testing successful get
        when(service.getEngagementById(exampleEngagement.getEngagementId())).thenReturn(exampleEngagement);
        mockMvc.perform(get("/engagements/{id}", exampleEngagement.getEngagementId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleEngagement)));

        // testing the failure of getting an id that doesn't exist
        mockMvc.perform(get("/engagements/{id}", 10L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Engagement Controller PUT")
    public void controllerPutEngagementTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);
        
        when(service.saveEngagement(exampleEngagement)).thenReturn(exampleEngagement);
        mockMvc.perform(post("/engagements").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleEngagement)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleEngagement)));
        // lowkey just doing the post test and then changing exampleEngagement and then doing the post test again with put
        exampleEngagement.setNotes("These notes are different than before!");

        when(service.saveEngagement(exampleEngagement)).thenReturn(exampleEngagement);
        mockMvc.perform(put("/engagements/{id}", exampleEngagement.getEngagementId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleEngagement)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleEngagement)));
    }

    @Test
    @DisplayName("Test Engagement Controller DELETE")
    public void controllerDeleteEngagementTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);
        
        when(service.deleteEngagement(exampleClient.getClientId())).thenReturn(true).thenThrow(NoSuchElementException.class);
        // testing successful delete
        mockMvc.perform(delete("/engagements/{id}", exampleEngagement.getEngagementId()))
            .andExpect(status().isNoContent());

        // testing attempt to delete client that doesn't exist
        mockMvc.perform(delete("/engagements/{id}", 10L))
            .andExpect(status().isNotFound());
    }
}
