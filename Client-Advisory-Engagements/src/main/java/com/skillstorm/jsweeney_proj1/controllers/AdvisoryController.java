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

import com.skillstorm.jsweeney_proj1.Dtos.AdvisoryDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.services.AdvisoryService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/advisories")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdvisoryController {
    private final AdvisoryService service;


    public AdvisoryController(AdvisoryService service) {
        this.service = service;
    }

    // haven't made tester for this yet because this isn't the most basic CRUD operation, will look into as beginning of creating real functionalities.
    @GetMapping()
    public ResponseEntity<List<AdvisoryDto>> getAllAdvisories() {
        List<AdvisoryDto> advisories = service.getAllAdvisories();
        return new ResponseEntity<List<AdvisoryDto>>(advisories, HttpStatus.OK);
    }

    @GetMapping("/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> getAdvisoryEngagements(@PathVariable Long id) {
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

    @GetMapping("/{id}")
    public ResponseEntity<AdvisoryDto> getAdvisoryById(@PathVariable Long id) {
        
        AdvisoryDto advisory;
        try {
            advisory = service.getFullAdvisoryInfoById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if (advisory == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(advisory, HttpStatus.OK);
    }    

    @PostMapping()
    public ResponseEntity<Advisory> createAdvisory(@Valid @RequestBody Advisory newAdvisory) {
        System.out.println(newAdvisory.toString());
        Advisory advisory = service.saveAdvisory(newAdvisory);
        if (advisory == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(advisory, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Advisory> updateAdvisory(@PathVariable Long id, @Valid @RequestBody Advisory newAdvisory) {
        Advisory advisory = service.saveAdvisory(newAdvisory);
        if (advisory == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(advisory, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdvisory(@PathVariable Long id) {
        boolean foundAdvisory;
        try {
            foundAdvisory = service.deleteAdvisory(id);
        } catch (NoSuchElementException e) {
            foundAdvisory = false;
            return new ResponseEntity<>(foundAdvisory, HttpStatus.NOT_FOUND);
        }
        if (!foundAdvisory) {
            return new ResponseEntity<>(foundAdvisory, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundAdvisory, HttpStatus.NO_CONTENT);
    }
    
}
