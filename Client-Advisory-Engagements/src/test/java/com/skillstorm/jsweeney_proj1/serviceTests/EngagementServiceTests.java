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

import java.time.LocalDate;
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
import com.skillstorm.jsweeney_proj1.models.Engagement.engagementStatus;
import com.skillstorm.jsweeney_proj1.models.Client;
import com.skillstorm.jsweeney_proj1.models.Client.tier;
import com.skillstorm.jsweeney_proj1.models.Engagement;
import com.skillstorm.jsweeney_proj1.repositories.EngagementRepository;
import com.skillstorm.jsweeney_proj1.services.EngagementService;


// The service layer interacts with the controller and the repository - handles "business logic"
// this class is meant to test them as a single component
@ExtendWith(MockitoExtension.class)
public class EngagementServiceTests {
    @Mock
    private EngagementRepository repo;

    @InjectMocks
    EngagementService engagementService;



    @Test
    @DisplayName("Test Engagement Service Create")
    public void serviceCreateEngagementTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);

        when(repo.save(exampleEngagement)).thenReturn(exampleEngagement);

        Engagement resultEngagement = engagementService.saveEngagement(exampleEngagement);
        assertNotNull(resultEngagement);
        assertEquals(exampleEngagement.getClient(), resultEngagement.getClient());
        assertEquals(exampleEngagement.getAdvisory(), resultEngagement.getAdvisory());
        assertEquals(exampleEngagement.getEngagementId(), resultEngagement.getEngagementId());
        assertEquals(exampleEngagement.getStartDate(), resultEngagement.getStartDate());
        assertEquals(exampleEngagement.getNotes(), resultEngagement.getNotes());
        assertEquals(exampleEngagement.getStatus(), resultEngagement.getStatus());
    }

    @Test
    @DisplayName("Test Engagement Service Read")
    public void serviceReadEngagementTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);

        when(repo.findById(exampleEngagement.getEngagementId())).thenReturn(Optional.of(exampleEngagement));
        
        Engagement resultEngagement = engagementService.getEngagementById(exampleEngagement.getEngagementId());
        assertNotNull(resultEngagement);
        assertEquals(exampleEngagement.getClient(), resultEngagement.getClient());
        assertEquals(exampleEngagement.getAdvisory(), resultEngagement.getAdvisory());
        assertEquals(exampleEngagement.getEngagementId(), resultEngagement.getEngagementId());
        assertEquals(exampleEngagement.getStartDate(), resultEngagement.getStartDate());
        assertEquals(exampleEngagement.getNotes(), resultEngagement.getNotes());
        assertEquals(exampleEngagement.getStatus(), resultEngagement.getStatus());
    }

    @Test
    @DisplayName("Test Engagement Service Update")
    public void serviceUpdateEngagementTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                    "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);
        String firstNotes = exampleEngagement.getNotes();

        // save original example in repo
        when(repo.save(exampleEngagement)).thenReturn(exampleEngagement);

        String preUpdateEngagementNotes = engagementService.saveEngagement(exampleEngagement).getNotes();
        exampleEngagement.setNotes("these notes are different!");
        
        // simluate saving over with the new data
        when(repo.save(exampleEngagement)).thenReturn(exampleEngagement);
        when(repo.findById(exampleEngagement.getEngagementId())).thenReturn(Optional.of(exampleEngagement));

        Engagement postUpdateEngagement = engagementService.saveEngagement(exampleEngagement);
        Engagement postUpdateReadEngagement = engagementService.getEngagementById(exampleEngagement.getEngagementId());

        assertNotNull(postUpdateEngagement);
        assertNotNull(postUpdateReadEngagement);
        assertNotEquals(postUpdateEngagement.getNotes(), firstNotes);
        assertNotEquals(postUpdateEngagement.getNotes(), firstNotes);
        assertEquals(postUpdateEngagement, postUpdateReadEngagement);
        assertEquals(postUpdateEngagement, exampleEngagement);
        assertEquals(exampleEngagement, postUpdateReadEngagement);
        assertNotEquals(postUpdateEngagement.getNotes(), preUpdateEngagementNotes);
    }

    @Test
    @DisplayName("Test Engagement Service Delete")
    public void serviceDeleteEngagementTest() {
        Client exampleClient = new Client(1L, "John ", "Smith", "jsmith@gmail.com",
                                                 "1234567890", tier.STANDARD, 750_000.00);
        Advisory exampleAdvisory = new Advisory(1L, "Business Advisory Services LLC ", serviceType.TAX_PLANNING, deliveryFormatOptions.HYBRID, 1_000.00);
        LocalDate exampleDate = LocalDate.of(2026, 3, 27);
        Engagement exampleEngagement = new Engagement(1L, exampleClient, exampleAdvisory, exampleDate, "example notes!.", engagementStatus.PAUSED);

        // our deletes use findById to make sure there is something to delete, so we need to mimic that functionality
        when(repo.findById(exampleEngagement.getEngagementId())).thenReturn(Optional.of(exampleEngagement)).thenThrow(NoSuchElementException.class);
        doNothing().doThrow(new NoSuchElementException()).when(repo).deleteById(exampleEngagement.getEngagementId());

        assertTrue(engagementService.deleteEngagement(1L));
        assertThrows(NoSuchElementException.class, () -> engagementService.deleteEngagement(1L));
        assertThrows(NoSuchElementException.class, () -> engagementService.getEngagementById(1L));
        

        verify(repo, times(1)).deleteById(1L);
        verify(repo, times(3)).findById(1L);
    }
}
