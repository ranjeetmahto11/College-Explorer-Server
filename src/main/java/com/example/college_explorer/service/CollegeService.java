package com.example.college_explorer.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.college_explorer.dto.CollegeRequest;
import com.example.college_explorer.dto.CollegeResponse;
import com.example.college_explorer.exception.ResourceNotFoundException;
import com.example.college_explorer.model.College;
import com.example.college_explorer.model.Course;
import com.example.college_explorer.model.Facility;
import com.example.college_explorer.repository.CollegeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;
    
    @Autowired
    private FileStorageService storageService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Value("${app.base-url}")
    private String baseUrl;

    // ---------------------------- CREATE COLLEGE ----------------------------
    public CollegeResponse addCollege(CollegeRequest collegeRequest, MultipartFile logo, MultipartFile banner) {
        College college = modelMapper.map(collegeRequest, College.class);

        // Handle file uploads
        if (logo != null && !logo.isEmpty()) {
            String logoFilename = storageService.store(logo);
            college.setLogoUrl(baseUrl + "/uploads/" + logoFilename);
        }

        if (banner != null && !banner.isEmpty()) {
            String bannerFilename = storageService.store(banner);
            college.setBannerUrl(baseUrl + "/uploads/" + bannerFilename);
        }

        // Handle featured section
        if (collegeRequest.getFeaturedSection() != null) {
            try {
                college.setFeaturedSection(
                    College.FeaturedSection.valueOf(collegeRequest.getFeaturedSection().toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid featured section: " + collegeRequest.getFeaturedSection());
            }
        }

        // Set boolean flags
        college.setFeatured(collegeRequest.isFeatured());
        college.setShowInHomepageBanner(collegeRequest.isBanner());

        // Handle relationships
        handleCollegeRelationships(college, collegeRequest);

        College savedCollege = collegeRepository.save(college);
        return toResponse(savedCollege);
    }

    // ---------------------------- GET METHODS ----------------------------
    public List<CollegeResponse> getAllColleges() {
        return collegeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CollegeResponse getCollegeById(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));
        return toResponse(college);
    }

    public List<CollegeResponse> getCollegesByCategory(String category) {
        try {
            return collegeRepository.findByType(College.CollegeType.valueOf(category.toUpperCase()))
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid college category: " + category);
        }
    }

    public List<CollegeResponse> getFeaturedColleges(String section) {
        try {
            return collegeRepository.findByFeaturedSectionAndFeatured(
                    College.FeaturedSection.valueOf(section.toUpperCase()),
                    true)
                    .stream()
                    .sorted(Comparator.comparing(College::getFeaturedPriority))
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid featured section: " + section);
        }
    }

    public boolean existsBySlug(String slug) {
        return collegeRepository.existsBySlug(slug);
    }

    // ---------------------------- UPDATE COLLEGE ----------------------------
    public CollegeResponse updateCollege(Long id, CollegeRequest collegeRequest, MultipartFile logo, MultipartFile banner) {
        College existingCollege = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));

        modelMapper.map(collegeRequest, existingCollege);

        // Handle file uploads
        if (logo != null && !logo.isEmpty()) {
            String logoFilename = storageService.store(logo);
            existingCollege.setLogoUrl(baseUrl + "/uploads/" + logoFilename);
        }

        if (banner != null && !banner.isEmpty()) {
            String bannerFilename = storageService.store(banner);
            existingCollege.setBannerUrl(baseUrl + "/uploads/" + bannerFilename);
        }

        // Handle featured section
        if (collegeRequest.getFeaturedSection() != null) {
            try {
                existingCollege.setFeaturedSection(
                    College.FeaturedSection.valueOf(collegeRequest.getFeaturedSection().toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid featured section: " + collegeRequest.getFeaturedSection());
            }
        }

        // Set boolean flags
        existingCollege.setFeatured(collegeRequest.isFeatured());
        existingCollege.setShowInHomepageBanner(collegeRequest.isBanner());

        // Clear and update relationships
        existingCollege.getCourses().clear();
        existingCollege.getFacilities().clear();
        handleCollegeRelationships(existingCollege, collegeRequest);

        return toResponse(collegeRepository.save(existingCollege));
    }

    // ---------------------------- DELETE COLLEGE ----------------------------
    public void deleteCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found"));
        collegeRepository.delete(college);
    }

    // ---------------------------- FEATURED COLLEGE ----------------------------
    public CollegeResponse markAsFeatured(Long id, String section, Integer priority, String label) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));

        try {
            college.setFeatured(true);
            college.setFeaturedSection(College.FeaturedSection.valueOf(section.toUpperCase()));
            college.setFeaturedPriority(priority != null ? priority : 0);
            college.setFeaturedLabel(label);
            
            return toResponse(collegeRepository.save(college));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid featured section: " + section);
        }
    }

    public CollegeResponse removeFromFeatured(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));

        college.setFeatured(false);
        college.setFeaturedSection(null);
        college.setFeaturedPriority(null);
        college.setFeaturedLabel(null);
        
        return toResponse(collegeRepository.save(college));
    }

    // ---------------------------- BANNER COLLEGE ----------------------------
    public CollegeResponse markAsBanner(Long id, String title, String description, Integer priority) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));

        college.setShowInHomepageBanner(true);
        college.setBannerTitle(title);
        college.setBannerDescription(description);
        college.setBannerPriority(priority != null ? priority : 0);
        
        return toResponse(collegeRepository.save(college));
    }

    public CollegeResponse removeFromBanner(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));

        college.setShowInHomepageBanner(false);
        college.setBannerTitle(null);
        college.setBannerDescription(null);
        college.setBannerPriority(null);
        
        return toResponse(collegeRepository.save(college));
    }

    // ---------------------------- STATUS MANAGEMENT ----------------------------
    public CollegeResponse publishCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));
        
        college.setStatus(College.CollegeStatus.PUBLISHED);
        return toResponse(collegeRepository.save(college));
    }

    public CollegeResponse unpublishCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with ID: " + id));
        
        college.setStatus(College.CollegeStatus.DRAFT);
        return toResponse(collegeRepository.save(college));
    }

    // ---------------------------- HELPER METHODS ----------------------------
    private void handleCollegeRelationships(College college, CollegeRequest collegeRequest) {
        if (collegeRequest.getCourses() != null) {
            collegeRequest.getCourses().forEach(courseDto -> {
                Course course = modelMapper.map(courseDto, Course.class);
                course.setCollege(college);
                college.getCourses().add(course);
            });
        }

        if (collegeRequest.getFacilities() != null) {
            collegeRequest.getFacilities().forEach(facilityDto -> {
                Facility facility = modelMapper.map(facilityDto, Facility.class);
                facility.setCollege(college);
                college.getFacilities().add(facility);
            });
        }
    }

    private CollegeResponse toResponse(College college) {
        CollegeResponse response = modelMapper.map(college, CollegeResponse.class);
        
        if (college.getFeaturedSection() != null) {
            response.setFeaturedSection(college.getFeaturedSection().name());
        }
        response.setFeatured(college.Featured());
        response.setBanner(college.isShowInHomepageBanner());
        response.setSponsored(college.isSponsored());
        
        return response;
    }
}