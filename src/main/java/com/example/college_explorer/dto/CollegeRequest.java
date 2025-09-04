package com.example.college_explorer.dto;

import java.util.List;

import com.example.college_explorer.model.CollegeType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CollegeRequest {

    private String name;
    private String location;
    private String city;
    private String state;
    private Integer ranking;
    private String establishedYear;
    private String website;
    private CollegeType type;
    private List<CourseDto> courses;
    private List<FacilityDto> facilities;

@JsonProperty("isFeatured")
private boolean isFeatured;

@JsonProperty("isBanner")
private boolean isBanner = false;

    private String featuredSection;

    private String category; // Will come as string: "TOP_COLLEGE", etc.

    // Getters and Setters
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    // Getter
    public String getCategory() {
        return category;
    }

// Setter
    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        this.isFeatured = featured;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        this.isBanner = banner;
    }

    public String getFeaturedSection() {
        return featuredSection;
    }

    public void setFeaturedSection(String featuredSection) {
        this.featuredSection = featuredSection;
    }
}
