package com.skillstorm.jsweeney_proj1.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.repositories.ClientRepository;
import com.skillstorm.jsweeney_proj1.services.ClientService;

// The service layer interacts with the controller and the repository - handles "business logic"
// this class is meant to test them as a single component
@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {
    @Mock
    private ClientRepository repo;

    @InjectMocks
    ClientService clientService;

    

    @Test
    @DisplayName("Test Client Service Create")
    public void serviceCreateClientTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);

        when(repo.save(exampleClient)).thenReturn(exampleClient);

        Client resultClient = clientService.saveClient(exampleClient);
        assertNotNull(resultClient);
        assertEquals(exampleClient.getClientId(), resultClient.getClientId());
        assertEquals(exampleClient.getFirstName(), resultClient.getFirstName());
        assertEquals(exampleClient.getLastName(), resultClient.getLastName());
        assertEquals(exampleClient.getEmail(), resultClient.getEmail());
        assertEquals(exampleClient.getPhone(), resultClient.getPhone());
        assertEquals(exampleClient.getTier(), resultClient.getTier());
        assertEquals(exampleClient.getEstNetWorth(), resultClient.getEstNetWorth());
    }

    @Test
    @DisplayName("Test Client Service Read")
    public void serviceGetClientTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        
        when(repo.findById(exampleClient.getClientId())).thenReturn(Optional.of(exampleClient));

        Client resultClient = clientService.getClientById(1L);
        assertNotNull(resultClient);
        assertEquals(exampleClient.getClientId(), resultClient.getClientId());
        assertEquals(exampleClient.getFirstName(), resultClient.getFirstName());
        assertEquals(exampleClient.getLastName(), resultClient.getLastName());
        assertEquals(exampleClient.getEmail(), resultClient.getEmail());
        assertEquals(exampleClient.getPhone(), resultClient.getPhone());
        assertEquals(exampleClient.getTier(), resultClient.getTier());
        assertEquals(exampleClient.getEstNetWorth(), resultClient.getEstNetWorth());

        // test a fail case
        when(repo.findById(5L)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> clientService.getClientById(5L));
    }

    @Test
    @DisplayName("Test Client Service Update")
    public void serviceUpdateClientTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        // tell Mockito to have the repo save the original example
        when(repo.save(exampleClient)).thenReturn(exampleClient);

        String preUpdateEmail = clientService.saveClient(exampleClient).getEmail();
        exampleClient.setEmail("differentEmail@gmail.com");

        // tell Mockito to have the repo save over the first example, and also read it back when we getId it
        when(repo.save(exampleClient)).thenReturn(exampleClient);
        when(repo.findById(exampleClient.getClientId())).thenReturn(Optional.of(exampleClient));

        Client postUpdate = clientService.saveClient(exampleClient);
        Client postUpdateRead = clientService.getClientById(1L);

        assertNotNull(postUpdate);
        assertNotNull(postUpdateRead);
        assertEquals(exampleClient.getEmail(), postUpdate.getEmail());
        assertEquals(exampleClient.getEmail(), postUpdateRead.getEmail());
        assertNotEquals(postUpdate.getEmail(), preUpdateEmail);
    }

    @Test
    @DisplayName("Test Client Service Delete")
    public void serviceDeleteClientTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);

        // our delete method tries reading the client first to make sure it exists, so we have to simulate that, then we simulate reading it after it was deleted with an error
        when(repo.findById(exampleClient.getClientId())).thenReturn(Optional.of(exampleClient)).thenThrow(NoSuchElementException.class);
        // tell Mockito to do nothing the first delete as repo delete returns void, and then throw an error when we delete again
        // finding out how to do this sucked
        doNothing().doThrow(new NoSuchElementException()).when(repo).deleteById(exampleClient.getClientId());



        assertTrue(clientService.deleteClient(1L)); // will likely implement both a by id or by full object system, for now just testing id
        assertThrows(NoSuchElementException.class, () -> clientService.deleteClient(1L)); // throw illegal argument for trying to delete an invalid id
        assertThrows(NoSuchElementException.class, () -> clientService.getClientById(1L)); // throw illegal argument for trying to read an invalid id

        // this was also annoying to find but is way cooler, I don't feel like using it for every test though
        // remember that the delete method will only be called once (on finding a record to delete)
        verify(repo, times(1)).deleteById(1L);

        // this gets called once per delete (twice total) and then once for the read test
        verify(repo, times(3)).findById(1L);
        
        
    }
}
