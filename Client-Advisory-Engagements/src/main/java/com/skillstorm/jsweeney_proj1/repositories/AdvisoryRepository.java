package com.skillstorm.jsweeney_proj1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.jsweeney_proj1.Dtos.AdvisoryDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Advisory;

@Repository
public interface AdvisoryRepository extends JpaRepository<Advisory, Long> {
    @Query(value = """
        select a.advisory_service_id,
            a.service_name,
            a.service_type,
            a.delivery_format,
            a.is_active_status,
            a.annual_fee,
            COUNT(e.client_id)
        from advisory_service as a
        LEFT JOIN engagement as e ON a.advisory_service_id=e.advisory_service_id 
        GROUP BY a.advisory_service_id;
            """, nativeQuery = true // used left join because we need to keep advisories with 0 engagements
        ) List<AdvisoryDto> getAllAdvisoriesWithClientCounts(); 

        @Query(value = """
        select a.advisory_service_id,
            a.service_name,
            a.service_type,
            a.delivery_format,
            a.is_active_status,
            a.annual_fee,
            COUNT(e.client_id)
        from advisory_service as a
        LEFT JOIN engagement as e ON a.advisory_service_id=e.advisory_service_id 
        WHERE a.advisory_service_id=:id
        GROUP BY a.advisory_service_id, a.service_name, a.service_type, a.delivery_format, a.is_active_status, a.annual_fee;
            """, nativeQuery = true // used left join because we need to keep advisories with 0 engagements
        ) AdvisoryDto findAdvisoryInfoById(@Param("id") Long id); 

    @Query( value = """
            select 
                   e.engagement_id,
                   c.first_name,
                   c.last_name,
                   c.email,
                   c.phone,
				   c.tier
            FROM advisory_service as a 
            JOIN engagement as e on a.advisory_service_id=e.advisory_service_id
            JOIN client as c on e.client_id=c.client_id
            WHERE a.advisory_service_id=:id
            """, nativeQuery = true
    ) List<EngagementDto> findRelatedEngagements(@Param("id") Long id);
}
