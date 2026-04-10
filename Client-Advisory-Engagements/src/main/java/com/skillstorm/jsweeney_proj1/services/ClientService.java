package com.skillstorm.jsweeney_proj1.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.springframework.stereotype.Service;


import com.skillstorm.jsweeney_proj1.Dtos.ClientDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.repositories.ClientRepository;

@Service
public class ClientService {
    
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public List<ClientDto> getAllClientsWithObligations() {
        return repository.getAllClientsWithObligations();
    }

    public Client getClientById(Long id) throws NoSuchElementException {
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new NoSuchElementException("No client with id: " + id);
        }
    }
  
    public ClientDto getFullClientInfoById(Long id) throws NoSuchElementException {
        ClientDto client = repository.findClientInfoById(id);
        return client;
    }

    public List<EngagementDto> getRelatedEngagementsById(Long id) throws NoSuchElementException {
        List<EngagementDto> engagements = repository.findRelatedEngagements(id);
        return engagements;
    }

    public Client saveClient(Client client) {
        return repository.save(client);
    }

    public boolean deleteClient(Long id) throws NoSuchElementException {
        getClientById(id); // this will cause our error to be thrown if the client with specified id doesn't exist
        repository.deleteById(id);
        return true;
    }


    // overloading in case I ever need to delete a client with the client object, don't think it'll ever happen
    public boolean deleteClient(Client client) throws NoSuchElementException {
        getClientById(client.getClientId()); // this will cause our error to be thrown if the client with specified id doesn't exist
        repository.delete(client);
        return true;
    }
}
