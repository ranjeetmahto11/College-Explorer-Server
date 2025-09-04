package com.example.college_explorer.dto;

import java.util.List;

import com.example.college_explorer.model.CollegeType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class CollegeResponse {

    private Long id;
    private String name;
    private String location;
    private String establishedYear;
    private String website;
    private String imageUrl; // Full URL to access image
    private CollegeType type;
    private List<CourseDto> courses;
    private List<FacilityDto> facilities;

  
    private String featuredSection;
    
    @JsonProperty("isFeatured")
private boolean isFeatured;

@JsonProperty("isBanner")
private boolean isBanner;


    public CollegeResponse() {}

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(String establishedYear) {
        this.establishedYear = establishedYear;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CollegeType getType() {
        return type;
    }

    public void setType(CollegeType type) {
        this.type = type;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    public List<FacilityDto> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityDto> facilities) {
        this.facilities = facilities;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getFeaturedSection() {
        return featuredSection;
    }

    public void setFeaturedSection(String featuredSection) {
        this.featuredSection = featuredSection;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    public void setSponsored(boolean sponsored) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
