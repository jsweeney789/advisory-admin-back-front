package com.skillstorm.jsweeney_proj1.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.jsweeney_proj1.Dtos.ClientDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.services.ClientService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ClientController {
    private final ClientService service; 

    public ClientController(ClientService service) {
        this.service = service;
    }

    
    @GetMapping()
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = service.getAllClientsWithObligations();
        return new ResponseEntity<List<ClientDto>>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        
        ClientDto client;
        try {
            client = service.getFullClientInfoById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }    

    @GetMapping("/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> getClientEngagements(@PathVariable Long id) {
        List<EngagementDto> engagements;
        
        try {
            engagements = service.getRelatedEngagementsById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if (engagements == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(engagements, HttpStatus.OK);
    }    

    @PostMapping()
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client newClient) {
        Client client = service.saveClient(newClient);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client newClient) {
        Client client = service.saveClient(newClient);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(client, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        boolean foundClient;
        try {
            foundClient = service.deleteClient(id);
        } catch (NoSuchElementException e) {
            foundClient = false;
            return new ResponseEntity<>(foundClient, HttpStatus.NOT_FOUND);
        }
        if (!foundClient) {
            return new ResponseEntity<>(foundClient, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundClient, HttpStatus.NO_CONTENT);
    }
    
}
