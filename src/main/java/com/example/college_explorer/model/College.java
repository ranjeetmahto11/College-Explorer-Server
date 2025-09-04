package com.example.college_explorer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollegeType type;

    private String naacRating;
    private String approval;
    private String contactEmail;
    private String contactPhone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String logoUrl;
    private String bannerUrl;
    private String virtualTourUrl;
    private String videoUrl;

    private String metaTitle;
    private String keywords;

    @Column(unique = true)
    private String slug;

    private String canonicalUrl;
    private String socialDescription;
    private String socialImageUrl;

    @Column(name = "is_featured")
    private Boolean featured;

    @Enumerated(EnumType.STRING)
    private FeaturedSection featuredSection;
    private Integer featuredPriority;
    private String featuredLabel;

    private boolean showInHomepageBanner = false;
    private String bannerTitle;
    private String bannerDescription;
    private Integer bannerPriority;

    private boolean isSponsored;

    @Column(name = "is_show_in_homepage_banner")
    private Date sponsorshipEndDate;

    @Enumerated(EnumType.STRING)
    private CollegeStatus status = CollegeStatus.DRAFT;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GalleryImage> galleryImages = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    // ===== ENUMS =====
    public enum CollegeType {
        GOVERNMENT, PRIVATE, AUTONOMOUS, OTHER
    }

    public enum CollegeStatus {
        DRAFT, PUBLISHED
    }

    public enum FeaturedSection {
        TOP_COLLEGES,
        TRENDING,
        RECOMMENDED,
        POPULAR,
        NEW_ADDITIONS
    }

    // ===== GETTERS AND SETTERS =====

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

    public CollegeType getType() {
        return type;
    }

    public void setType(CollegeType type) {
        this.type = type;
    }

    public String getNaacRating() {
        return naacRating;
    }

    public void setNaacRating(String naacRating) {
        this.naacRating = naacRating;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getVirtualTourUrl() {
        return virtualTourUrl;
    }

    public void setVirtualTourUrl(String virtualTourUrl) {
        this.virtualTourUrl = virtualTourUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getSocialDescription() {
        return socialDescription;
    }

    public void setSocialDescription(String socialDescription) {
        this.socialDescription = socialDescription;
    }

    public String getSocialImageUrl() {
        return socialImageUrl;
    }

    public void setSocialImageUrl(String socialImageUrl) {
        this.socialImageUrl = socialImageUrl;
    }

    public boolean Featured() {
        return featured;
    }

    public void setFeatured(boolean Featured) {
        this.featured = Featured;
    }

    public FeaturedSection getFeaturedSection() {
        return featuredSection;
    }

    public void setFeaturedSection(FeaturedSection featuredSection) {
        this.featuredSection = featuredSection;
    }

    public Integer getFeaturedPriority() {
        return featuredPriority;
    }

    public void setFeaturedPriority(Integer featuredPriority) {
        this.featuredPriority = featuredPriority;
    }

    public String getFeaturedLabel() {
        return featuredLabel;
    }

    public void setFeaturedLabel(String featuredLabel) {
        this.featuredLabel = featuredLabel;
    }

    public boolean isShowInHomepageBanner() {
        return showInHomepageBanner;
    }

    public void setShowInHomepageBanner(boolean showInHomepageBanner) {
        this.showInHomepageBanner = showInHomepageBanner;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public void setBannerDescription(String bannerDescription) {
        this.bannerDescription = bannerDescription;
    }

    public Integer getBannerPriority() {
        return bannerPriority;
    }

    public void setBannerPriority(Integer bannerPriority) {
        this.bannerPriority = bannerPriority;
    }

    public boolean isSponsored() {
        return isSponsored;
    }

    public void setSponsored(boolean isSponsored) {
        this.isSponsored = isSponsored;
    }

    public Date getSponsorshipEndDate() {
        return sponsorshipEndDate;
    }

    public void setSponsorshipEndDate(Date sponsorshipEndDate) {
        this.sponsorshipEndDate = sponsorshipEndDate;
    }

    public CollegeStatus getStatus() {
        return status;
    }

    public void setStatus(CollegeStatus status) {
        this.status = status;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
