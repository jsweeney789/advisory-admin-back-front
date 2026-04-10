package com.skillstorm.jsweeney_proj1.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.skillstorm.jsweeney_proj1.models.Engagement;
import com.skillstorm.jsweeney_proj1.services.EngagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/engagements")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EngagementController {
    private final EngagementService service; 

    public EngagementController(EngagementService service) {
        this.service = service;
    }

    // haven't made tester for this yet because this isn't the most basic CRUD operation, will look into as beginning of creating real functionalities.
    @GetMapping()
    public ResponseEntity<List<Engagement>> getAllEngagements() {
        List<Engagement> engagements = service.getAllEngagements();
        return new ResponseEntity<List<Engagement>>(engagements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Engagement> getEngagementById(@PathVariable Long id) {
        
        Engagement engagement;
        try {
            engagement = service.getEngagementById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if (engagement == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(engagement, HttpStatus.OK);
    }    

    @PostMapping()
    public ResponseEntity<Engagement> createEngagement(@Valid @RequestBody Engagement newEngagement) {
        Engagement engagement = service.saveEngagement(newEngagement);
        if (engagement == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(engagement, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Engagement> updateEngagement(@PathVariable Long id, @Valid @RequestBody Engagement newEngagement) {
        Engagement engagement = service.saveEngagement(newEngagement);
        if (engagement == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(engagement, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEngagement(@PathVariable Long id) {
        boolean foundEngagement;
        try {
            foundEngagement = service.deleteEngagement(id);
        } catch (NoSuchElementException e) {
            foundEngagement = false;
            return new ResponseEntity<>(foundEngagement, HttpStatus.NOT_FOUND);
        }
        if (!foundEngagement) {
            return new ResponseEntity<>(foundEngagement, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundEngagement, HttpStatus.NO_CONTENT);
    }
}
