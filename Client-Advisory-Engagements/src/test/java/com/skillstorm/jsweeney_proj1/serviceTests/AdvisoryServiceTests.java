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

import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.models.Advisory.deliveryFormatOptions;
import com.skillstorm.jsweeney_proj1.models.Advisory.serviceType;
import com.skillstorm.jsweeney_proj1.repositories.AdvisoryRepository;
import com.skillstorm.jsweeney_proj1.services.AdvisoryService;


// The service layer interacts with the controller and the repository - handles "business logic"
// this class is meant to test them as a single component
@ExtendWith(MockitoExtension.class)
public class AdvisoryServiceTests {
    @Mock
    private AdvisoryRepository repo;

    @InjectMocks
    AdvisoryService advisoryService;


    @Test
    @DisplayName("Test Advisory Service Create")
    public void serviceCreateAdvisoryTest() { 
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        when(repo.save(exampleAdvisory)).thenReturn(exampleAdvisory);
        Advisory resultAdvisory = advisoryService.saveAdvisory(exampleAdvisory);
        assertNotNull(resultAdvisory);
        assertEquals(exampleAdvisory.getAdvisoryId(), resultAdvisory.getAdvisoryId());
        assertEquals(exampleAdvisory.getName(), resultAdvisory.getName());
        assertEquals(exampleAdvisory.getServiceType(), resultAdvisory.getServiceType());
        assertEquals(exampleAdvisory.getDeliveryFormat(), resultAdvisory.getDeliveryFormat());
        assertEquals(exampleAdvisory.getAnnualFee(), resultAdvisory.getAnnualFee());
    }

    @Test
    @DisplayName("Test Advisory Service Read")
    public void serviceGetAdvisoryTest() {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        
        
        when(repo.findById(exampleAdvisory.getAdvisoryId())).thenReturn(Optional.of(exampleAdvisory));

        Advisory resultAdvisory = advisoryService.getAdvisoryById(1L);
        assertNotNull(resultAdvisory);
        assertEquals(exampleAdvisory.getAdvisoryId(), resultAdvisory.getAdvisoryId());
        assertEquals(exampleAdvisory.getName(), resultAdvisory.getName());
        assertEquals(exampleAdvisory.getServiceType(), resultAdvisory.getServiceType());
        assertEquals(exampleAdvisory.getDeliveryFormat(), resultAdvisory.getDeliveryFormat());
        assertEquals(exampleAdvisory.getAnnualFee(), resultAdvisory.getAnnualFee());

        // test a fail case
        when(repo.findById(5L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> advisoryService.getAdvisoryById(5L));
    }

    @Test
    @DisplayName("Test Advisory Service Update")
    public void serviceUpdateAdvisoryTest() {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        when(repo.save(exampleAdvisory)).thenReturn(exampleAdvisory);
        // tell Mockito to have the repo save the original example
        when(repo.save(exampleAdvisory)).thenReturn(exampleAdvisory);

        double preUpdateFee = advisoryService.saveAdvisory(exampleAdvisory).getAnnualFee();
        exampleAdvisory.setAnnualFee(1_500.00);

         // tell Mockito to have the repo save over the first example, and also read it back when we getId it
        when(repo.save(exampleAdvisory)).thenReturn(exampleAdvisory);
        when(repo.findById(exampleAdvisory.getAdvisoryId())).thenReturn(Optional.of(exampleAdvisory));

        Advisory postUpdate = advisoryService.saveAdvisory(exampleAdvisory);
        Advisory postUpdateRead = advisoryService.getAdvisoryById(1L);

        assertNotNull(postUpdate);
        assertNotNull(postUpdateRead);
        assertEquals(exampleAdvisory.getAnnualFee(), postUpdate.getAnnualFee());
        assertEquals(exampleAdvisory.getAnnualFee(), postUpdateRead.getAnnualFee());
        assertNotEquals(postUpdate.getAnnualFee(), preUpdateFee);
    }

    @Test
    @DisplayName("Test Advisory Service Delete")
    public void serviceDeleteAdvisoryTest() {
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);

        
        when(repo.findById(exampleAdvisory.getAdvisoryId())).thenReturn(Optional.of(exampleAdvisory)).thenThrow(NoSuchElementException.class);
        
        doNothing().doThrow(new NoSuchElementException()).when(repo).deleteById(exampleAdvisory.getAdvisoryId());



        assertTrue(advisoryService.deleteAdvisory(1L)); 
        assertThrows(NoSuchElementException.class, () -> advisoryService.deleteAdvisory(1L)); 
        assertThrows(NoSuchElementException.class, () -> advisoryService.getAdvisoryById(1L)); 

        
        verify(repo, times(1)).deleteById(1L);

        
        verify(repo, times(3)).findById(1L);
        
        
    }
}
