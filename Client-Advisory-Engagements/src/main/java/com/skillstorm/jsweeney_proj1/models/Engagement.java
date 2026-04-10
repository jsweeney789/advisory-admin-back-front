package com.skillstorm.jsweeney_proj1.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Engagements are relationships between a client and an advisoryService
 */
@Entity
@Table(name="engagement")
public class Engagement {
    public enum engagementStatus{ACTIVE, PAUSED, COMPLETED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="engagement_id")
    private Long engagementId;

    @NotNull
    @ManyToOne
    @JoinColumn(name ="client_id")
    private Client client; // the id of the client associated with the engagement

    @NotNull
    @ManyToOne
    @JoinColumn(name="advisory_service_id")
    private Advisory advisory; // the id of the advisory Service associated with the engagement

    @NotNull
    @Column(name="start_date")
    private LocalDate startDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="engagement_status")
    private engagementStatus status;

    @Column(name="notes")
    private String notes;

    
    public Engagement(Long engagementId, Client client, Advisory advisory, LocalDate start_date) {
        this.engagementId = engagementId;
        this.client = client;
        this.advisory = advisory;
        this.startDate = start_date; // will use backend timer for Date, though DB also sets NOW() itself as default
        this.status = engagementStatus.ACTIVE; // assuming engagements are active on creation for now
    }

    // with notes but no status
    public Engagement(Long engagementId, Client client, Advisory advisory, LocalDate start_date, String notes) {
        this.engagementId = engagementId;
        this.client = client;
        this.advisory = advisory;
        this.startDate = start_date; // will use backend timer for Date, though DB also sets NOW() itself as default
        this.status = engagementStatus.ACTIVE; // assuming engagements are active on creation for now
        this.notes = notes;
    }

    // with status but no notes
    public Engagement(Long engagementId, Client client, Advisory advisory, LocalDate start_date, engagementStatus status) {
        this.engagementId = engagementId;
        this.client = client;
        this.advisory = advisory;
        this.startDate = start_date; // will use backend timer for Date, though DB also sets NOW() itself as default
        this.status = engagementStatus.ACTIVE; // assuming engagements are active on creation for now

    }

    // with status and notes
    public Engagement(Long engagementId, Client client, Advisory advisory, LocalDate start_date, String notes, engagementStatus status) {
        this.engagementId = engagementId;
        this.client = client;
        this.advisory = advisory;
        this.startDate = LocalDate.now(); // will use backend timer for Date, though DB also sets NOW() itself as default
        this.notes = notes;
        this.status = status;
    }

    public Engagement() {
    }

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Advisory getAdvisory() {
        return advisory;
    }

    public void setAdvisory(Advisory advisory) {
        this.advisory = advisory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate start_date) {
        this.startDate = start_date;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(engagementStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (engagementId ^ (engagementId >>> 32));
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((advisory == null) ? 0 : advisory.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Engagement other = (Engagement) obj;
        if (engagementId != other.engagementId)
            return false;
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (!client.equals(other.client))
            return false;
        if (advisory == null) {
            if (other.advisory != null)
                return false;
        } else if (!advisory.equals(other.advisory))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (status != other.status)
            return false;
        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (!notes.equals(other.notes))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Engagement [engagementId=" + engagementId + ", client=" + client + ", advisory=" + advisory
                + ", startDate=" + startDate + ", status=" + status + ", notes=" + notes + "]";
    }

    
}

