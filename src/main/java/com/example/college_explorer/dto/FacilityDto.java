package com.example.college_explorer.dto;

import com.example.college_explorer.model.Facility;

public class FacilityDto {

    private Long id;
    private String name;
    private Facility.FacilityType type;
    private String description;
    private boolean isStandard;
    private String standardType;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Facility.FacilityType getType() {
        return type;
    }

    public void setType(Facility.FacilityType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStandard() {
        return isStandard;
    }

    public void setStandard(boolean standard) {
        isStandard = standard;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }
}
