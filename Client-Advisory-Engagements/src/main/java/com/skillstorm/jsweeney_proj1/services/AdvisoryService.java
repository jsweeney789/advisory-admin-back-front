package com.skillstorm.jsweeney_proj1.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.skillstorm.jsweeney_proj1.Dtos.AdvisoryDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Advisory;
import com.skillstorm.jsweeney_proj1.repositories.AdvisoryRepository;

@Service
public class AdvisoryService {
    
    private final AdvisoryRepository repository;

    public AdvisoryService(AdvisoryRepository repository) {
        this.repository = repository;
    }

    public List<AdvisoryDto> getAllAdvisories() {
        return repository.getAllAdvisoriesWithClientCounts();
    }

    public List<EngagementDto> getRelatedEngagementsById(Long id) throws NoSuchElementException {
        List<EngagementDto> engagements = repository.findRelatedEngagements(id);
        
        return engagements;
    }

    public AdvisoryDto getFullAdvisoryInfoById(Long id) throws NoSuchElementException {
        AdvisoryDto advisory = repository.findAdvisoryInfoById(id);
        return advisory;
    }


    public Advisory getAdvisoryById(Long id) throws NoSuchElementException {
        Optional<Advisory> advisory = repository.findById(id);
        if (advisory.isPresent()) {
            return advisory.get();
        } else {
            throw new NoSuchElementException("No advisory with id: " + id);
        }
    }

    public Advisory saveAdvisory(Advisory advisory) {
        return repository.save(advisory);
    }

    public boolean deleteAdvisory(Long id) throws NoSuchElementException {
        getAdvisoryById(id); // this throws an exception if it doesn't exist
        repository.deleteById(id);
        return true;
    }

}
