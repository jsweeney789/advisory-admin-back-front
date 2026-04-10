package com.skillstorm.jsweeney_proj1.IntegrationTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Engagement;
import com.skillstorm.jsweeney_proj1.models.Engagement.engagementStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// won't lie I looked it up and Gemini told me about this test stuff for connecting to a test db
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class controllerThroughDBTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    
    @Test
    @DisplayName("Single Client CRUD Test")
    public void clientCRUDTest () throws Exception {
        Client example = new Client(null, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        
        // Create (save) test
        MvcResult createResult = mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(example)))
            .andExpect(status().isCreated())
            .andReturn();
        // need to check fields now that we set up DB to handle IDs for us
        Client returnClient = objectMapper.readValue(createResult.getResponse().getContentAsString(), Client.class);
        assertNotNull(returnClient.getClientId());
        assertEquals(returnClient.getFirstName(), example.getFirstName());
        assertEquals(returnClient.getLastName(), example.getLastName());
        assertEquals(returnClient.getEmail(), example.getEmail());
        assertEquals(returnClient.getPhone(), example.getPhone());
        assertEquals(returnClient.getTier(), example.getTier());
        assertEquals(returnClient.getEstNetWorth(), example.getEstNetWorth());
        

        // Read (get) test - only testing one at a time for now, will test getAll() along with other list stuff later
        mockMvc.perform(get("/clients/{id}", returnClient.getClientId())).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnClient)));
        // test read fail
        mockMvc.perform(get("/clients/{id}", 100000L))
            .andExpect(status().isNotFound());

        // Update (post) test 
        String oldEmail = returnClient.getEmail();
        returnClient.setEmail("jsmith@skillstorm.com");
        MvcResult result = mockMvc.perform(put("/clients/{id}", returnClient.getClientId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(returnClient)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnClient)))
            .andReturn();
        
        // I only learned about mvc result and .andReturn() while doing this. I think the tests are fine without it but I want to try it out.
        //https://www.baeldung.com/spring-mockmvc-fetch-json
        String json = result.getResponse().getContentAsString();
        Client resultClient = objectMapper.readValue(json, new TypeReference<>(){});
        assertNotEquals(resultClient.getEmail(), oldEmail);


        // Delete test (and teardown)
        mockMvc.perform(delete("/clients/{id}", returnClient.getClientId()))
            .andExpect(status().isNoContent());
        
        // test delete fail
        mockMvc.perform(delete("/clients/{id}", 1000000L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Single Advisory CRUD Test")
    public void advisoryCRUDTest () throws Exception {
        Advisory example = new Advisory(null, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        
        // Create (save) test
        MvcResult createResult = mockMvc.perform(post("/advisories").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(example)))
            .andExpect(status().isCreated())
            .andReturn();
            // need to check fields now that we set up DB to handle IDs for us
        Advisory returnAdvisory = objectMapper.readValue(createResult.getResponse().getContentAsString(), Advisory.class);
        assertNotNull(returnAdvisory.getAdvisoryId());
        assertEquals(returnAdvisory.getName(), example.getName());
        assertEquals(returnAdvisory.getServiceType(), example.getServiceType());
        assertEquals(returnAdvisory.getDeliveryFormat(), example.getDeliveryFormat());
        assertEquals(returnAdvisory.getAnnualFee(), example.getAnnualFee());
        assertEquals(returnAdvisory.isActive(), example.isActive());


        // Read (get) test - only testing one at a time for now, will test getAll() along with other list stuff later
        mockMvc.perform(get("/advisories/{id}", returnAdvisory.getAdvisoryId())).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnAdvisory)));
        // test read fail
        mockMvc.perform(get("/advisories/{id}", 10000L))
            .andExpect(status().isNotFound());

        // Update (post) test 
        String oldName = returnAdvisory.getName();
        returnAdvisory.setName("Small Business Advisory Services LLC");
        MvcResult result = mockMvc.perform(put("/advisories/{id}", returnAdvisory.getAdvisoryId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(returnAdvisory)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnAdvisory)))
            .andReturn();
        
        String json = result.getResponse().getContentAsString();
        Advisory resultAdvisory = objectMapper.readValue(json, new TypeReference<>(){});
        assertNotEquals(resultAdvisory.getName(), oldName);


        // Delete test (and teardown)
        mockMvc.perform(delete("/advisories/{id}", returnAdvisory.getAdvisoryId()))
            .andExpect(status().isNoContent());
        
        // test delete fail
        mockMvc.perform(delete("/advisories/{id}", 10000L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Single Engagement CRUD Test")
    public void engagementCRUDTest () throws Exception {
        Client exampleClient = new Client(null, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(null, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        
        // adding the client and advisory to the db as well as the engagement depends on them
        MvcResult createClientResult = mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(exampleClient))).andReturn();
        MvcResult createAdvisoryResult = mockMvc.perform(post("/advisories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(exampleAdvisory))).andReturn();
        Client returnClient = objectMapper.readValue(createClientResult.getResponse().getContentAsString(), Client.class);
        Advisory returnAdvisory = objectMapper.readValue(createAdvisoryResult.getResponse().getContentAsString(), Advisory.class);

        Engagement example = new Engagement(null, returnClient, returnAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);

        // Create (save) test
        MvcResult createEngagementResult = mockMvc.perform(post("/engagements").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(example)))
            .andExpect(status().isCreated())
            .andReturn();
        Engagement returnEngagement = objectMapper.readValue(createEngagementResult.getResponse().getContentAsString(), Engagement.class);

        // Read (get) test - only testing one at a time for now, will test getAll() along with other list stuff later
        mockMvc.perform(get("/engagements/{id}", returnEngagement.getEngagementId())).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnEngagement)));
        // test read fail
        mockMvc.perform(get("/engagements/{id}", 10000L))
            .andExpect(status().isNotFound());

        // Update (post) test 
        String oldNotes = returnEngagement.getNotes();
        returnEngagement.setNotes("These are new notes!");
        MvcResult result = mockMvc.perform(put("/engagements/{id}", returnEngagement.getEngagementId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(returnEngagement)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(returnEngagement)))
            .andReturn();
        
        String json = result.getResponse().getContentAsString();
        Engagement resultEngagement = objectMapper.readValue(json, new TypeReference<>(){});
        assertNotEquals(resultEngagement.getNotes(), oldNotes);


        // Delete test (and teardown)
        mockMvc.perform(delete("/engagements/{id}", returnEngagement.getEngagementId()))
            .andExpect(status().isNoContent());
        
        // test delete fail
        mockMvc.perform(delete("/engagements/{id}", 10000L))
            .andExpect(status().isNotFound());

        // tearing down the client and advisory we made too
        mockMvc.perform(delete("/clients/{id}", returnClient.getClientId())).andExpect(status().isNoContent());
        mockMvc.perform(delete("/advisories/{id}", returnAdvisory.getAdvisoryId())).andExpect(status().isNoContent());
    }
}
