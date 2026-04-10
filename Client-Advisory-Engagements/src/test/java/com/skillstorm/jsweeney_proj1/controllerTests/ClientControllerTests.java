package com.skillstorm.jsweeney_proj1.controllerTests;
   
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.NoSuchElementException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.jsweeney_proj1.controllers.ClientController;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.services.ClientService;

/** SOURCES
 * https://spring.io/guides/gs/testing-web
 * https://www.youtube.com/watch?v=7frnXmdJF98
 * https://www.baeldung.com/spring-mockmvc-vs-webmvctest
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/StatusResultMatchers.html?utm_source=chatgpt.com
 */

@AutoConfigureMockMvc
@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService service;
    
    @Test
    @DisplayName("Test Client Controller POST")
    public void controllerPostClientTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        
        when(service.saveClient(exampleClient)).thenReturn(exampleClient);
        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleClient)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleClient)));
    }

    @Test
    @DisplayName("Test Client Controller GET")
    public void controllerGetClientTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        // testing successful get
        when(service.getClientById(exampleClient.getClientId())).thenReturn(exampleClient);
        mockMvc.perform(get("/clients/{id}", exampleClient.getClientId())).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleClient)));

        // testing the failure of getting an id that doesn't exist
        mockMvc.perform(get("/clients/{id}", 10L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Client Controller PUT")
    public void controllerPutClientTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        
        when(service.saveClient(exampleClient)).thenReturn(exampleClient);
        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleClient)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleClient)));
        // lowkey just doing the post test and then changing exampleClient and then doing the post test again with put
        exampleClient.setFirstName("Bobby");
        exampleClient.setEmail("bsmith@gmail.com");

        when(service.saveClient(exampleClient)).thenReturn(exampleClient);
        mockMvc.perform(put("/clients/{id}", exampleClient.getClientId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(exampleClient)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(exampleClient)));
    }

    @Test
    @DisplayName("Test Client Controller DELETE")
    public void controllerDeleteClientTest() throws Exception {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        
        when(service.deleteClient(exampleClient.getClientId())).thenReturn(true).thenThrow(NoSuchElementException.class);
        // testing successful delete
        mockMvc.perform(delete("/clients/{id}", exampleClient.getClientId()))
            .andExpect(status().isNoContent());

        // testing attempt to delete client that doesn't exist
        mockMvc.perform(delete("/clients/{id}", 10L))
            .andExpect(status().isNotFound());
    }
}
