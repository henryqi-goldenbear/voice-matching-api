package com.henryqi.voicematching.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("circles")
public class Circle {
    @Id
    private Long id;
    private Long experienceId;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getExperienceId() { return experienceId; }
    public void setExperienceId(Long experienceId) { this.experienceId = experienceId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
