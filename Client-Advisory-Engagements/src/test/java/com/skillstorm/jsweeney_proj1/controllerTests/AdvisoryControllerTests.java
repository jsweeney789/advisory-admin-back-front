package com.skillstorm.jsweeney_proj1.controllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.jsweeney_proj1.controllers.AdvisoryController;
import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;
import com.skillstorm.jsweeney_proj1.services.AdvisoryService;


@AutoConfigureMockMvc
@WebMvcTest(controllers = AdvisoryController.class)
public class AdvisoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdvisoryService service;

    @Test
    @DisplayName("Test Advisory Controller POST")
    public void controllerPostAdvisoryTest() throws Exception {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        
        when(service.saveAdvisory(exampleAdvisory)).thenReturn(exampleAdvisory);
        mockMvc.perform(post("/advisories").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleAdvisory)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleAdvisory)));
    }

    @Test
    @DisplayName("Test Advisory Controller GET")
    public void controllerGetAdvisoryTest() throws Exception {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        
        when(service.getAdvisoryById(exampleAdvisory.getAdvisoryId())).thenReturn(exampleAdvisory);
        mockMvc.perform(get("/advisories/{id}", exampleAdvisory.getAdvisoryId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleAdvisory)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleAdvisory)));

        // testing the failure of getting an id that doesn't exist
        mockMvc.perform(get("/advisories/{id}", 10L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Advisory Controller PUT")
    public void controllerPutClientTest() throws Exception {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        
        when(service.saveAdvisory(exampleAdvisory)).thenReturn(exampleAdvisory);
        mockMvc.perform(post("/advisories").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleAdvisory)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleAdvisory)));
        // lowkey just doing the post test and then changing exampleAdvisory and then doing the post test again with put
        exampleAdvisory.setName("Corporate Advisory Services LLC");
        exampleAdvisory.setAnnualFee(1_200.00);

        when(service.saveAdvisory(exampleAdvisory)).thenReturn(exampleAdvisory);
        mockMvc.perform(put("/advisories/{id}", exampleAdvisory.getAdvisoryId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleAdvisory)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleAdvisory)));
    }

    @Test
    @DisplayName("Test Advisory Controller DELETE")
    public void controllerDeleteClientTest() throws Exception {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);

        
        when(service.deleteAdvisory(exampleAdvisory.getAdvisoryId())).thenReturn(true).thenThrow(NoSuchElementException.class);
        // testing successful delete
        mockMvc.perform(delete("/advisories/{id}", exampleAdvisory.getAdvisoryId()))
            .andExpect(status().isNoContent());

        // testing attempt to delete client that doesn't exist
        mockMvc.perform(delete("/advisories/{id}", 10L))
            .andExpect(status().isNotFound());
    }
}
