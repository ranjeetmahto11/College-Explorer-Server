package com.example.college_explorer.dto;

import java.util.Date;
import java.util.List;

import com.example.college_explorer.model.College;

import lombok.Data;

@Data
public class CollegeDto {
    private Long id;
    private String name;
    private College.CollegeType type;
    private String naacRating;
    private String approval;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String logoUrl;
    private String bannerUrl;
    private String virtualTourUrl;
    private String videoUrl;
    private String metaTitle;
    private String keywords;
    private String slug;
    private String canonicalUrl;
    private String socialDescription;
    private String socialImageUrl;
    private boolean isFeatured;
    private String featuredSection;
    private Integer featuredPriority;
    private String featuredLabel;
    private boolean showInHomepageBanner;
    private String bannerTitle;
    private String bannerDescription;
    private Integer bannerPriority;
    private boolean isSponsored;
    private Date sponsorshipEndDate;
    private College.CollegeStatus status;
    private List<CourseDto> courses;
    private List<FacilityDto> facilities;
    private List<GalleryImageDto> galleryImages;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isBanner = false; // Add this

}