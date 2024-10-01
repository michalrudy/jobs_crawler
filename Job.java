package com.mycompany.jobs_crawler;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Jobs")
public class Job {

    @Id
    private String id;
    private String title;
    private String employer;
    private String location;
    private LocalDateTime publicationDate;
    private LocalDateTime lastUpdated;
    private String site;
    private String description; // Dodane pole opisu

    // Constructors, getters and setters

    public Job() {
    }

    public Job(String title, String employer, String location, String site) {
        this.title = title;
        this.employer = employer;
        this.location = location;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description; // Dodana metoda getDescription
    }

    public void setDescription(String description) {
        this.description = description; // Dodana metoda setDescription
    }
}
