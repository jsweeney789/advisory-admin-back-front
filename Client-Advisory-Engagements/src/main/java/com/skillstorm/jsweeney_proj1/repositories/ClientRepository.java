package com.skillstorm.jsweeney_proj1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.jsweeney_proj1.Dtos.ClientDto;
import com.skillstorm.jsweeney_proj1.Dtos.EngagementDto;
import com.skillstorm.jsweeney_proj1.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    @Query(value = """
            select c.client_id AS clientId,
                c.first_name,
                c.last_name,
                c.email,
                c.phone,
                c.tier,
                c.net_worth,
                COALESCE(SUM(a.annual_fee), 0),
                COUNT(e.advisory_service_id)
            from client as c
            LEFT JOIN engagement as e ON c.client_id=e.client_id
            LEFT JOIN advisory_service as a ON e.advisory_service_id=a.advisory_service_id
            GROUP BY c.client_id
            """,
            nativeQuery = true

    ) List<ClientDto> getAllClientsWithObligations();

    @Query(value = """
            select c.client_id,
                c.first_name,
                c.last_name,
                c.email,
                c.phone,
                c.tier,
                c.net_worth,
                COALESCE(SUM(a.annual_fee), 0),
                COUNT(e.advisory_service_id)
            from client as c
            LEFT JOIN engagement as e ON c.client_id=e.client_id
            LEFT JOIN advisory_service as a ON e.advisory_service_id=a.advisory_service_id
            where c.client_id = :id
            GROUP BY c.client_id, c.last_name, c.email, c.phone, c.tier, c.net_worth
            """,
            nativeQuery = true

    ) ClientDto findClientInfoById(@Param("id") Long id);

    @Query(value = """
            select 
                   e.engagement_id,
                   a.service_name,
                   e.start_date,
                   e.engagement_status,
                   a.annual_fee
            FROM client as c 
            JOIN engagement as e on c.client_id=e.client_id
            JOIN advisory_service as a on e.advisory_service_id=a.advisory_service_id
            WHERE c.client_id=:id
            """,
            nativeQuery = true

    ) List<EngagementDto> findRelatedEngagements(@Param("id") Long id);

}
