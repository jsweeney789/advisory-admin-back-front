package com.skillstorm.jsweeney_proj1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.jsweeney_proj1.models.Engagement;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {

}
