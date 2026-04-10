package com.skillstorm.jsweeney_proj1.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.jsweeney_proj1.models.Engagement;
import com.skillstorm.jsweeney_proj1.repositories.EngagementRepository;

@Service
public class EngagementService {
    
    private final EngagementRepository repository;

    public EngagementService(EngagementRepository repository) {
        this.repository = repository;
    }

    public List<Engagement> getAllEngagements() {
        return repository.findAll();
    }

    public Engagement getEngagementById(Long id) throws NoSuchElementException {
        Optional<Engagement> engagement = repository.findById(id);
        if (engagement.isPresent()) {
            return engagement.get();
        } else {
            throw new NoSuchElementException("No engagement with id: " + id);
        }
    }

    public Engagement saveEngagement(Engagement engagement) {
        return repository.save(engagement);
    }

    public boolean deleteEngagement(Long id) throws NoSuchElementException {
        getEngagementById(id); // will throw the exception for us if it doesn't exist.
        repository.deleteById(id);
        return true;
    }
}
