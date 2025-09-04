package com.example.college_explorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.college_explorer.dto.CollegeRequest;
import com.example.college_explorer.dto.CollegeResponse;
import com.example.college_explorer.service.CollegeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    // ---------------------------- PUBLIC ENDPOINTS ----------------------------
    @GetMapping
    public ResponseEntity<List<CollegeResponse>> getAllColleges() {
        return ResponseEntity.ok(collegeService.getAllColleges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponse> getCollegeById(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.getCollegeById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CollegeResponse>> getCollegesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(collegeService.getCollegesByCategory(category));
    }

    @GetMapping("/check-slug")
    public ResponseEntity<Boolean> checkSlugAvailability(@RequestParam String slug) {
        return ResponseEntity.ok(!collegeService.existsBySlug(slug));
    }

    @GetMapping("/featured/{section}")
    public ResponseEntity<List<CollegeResponse>> getFeaturedColleges(@PathVariable String section) {
        return ResponseEntity.ok(collegeService.getFeaturedColleges(section));
    }

    // ---------------------------- ADMIN ENDPOINTS ----------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CollegeResponse> addCollege(
            @Valid @RequestPart CollegeRequest collegeRequest,
            @RequestPart(required = false) MultipartFile logo,
            @RequestPart(required = false) MultipartFile banner) {
        CollegeResponse response = collegeService.addCollege(collegeRequest, logo, banner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CollegeResponse> updateCollege(
            @PathVariable Long id,
            @Valid @RequestPart CollegeRequest collegeRequest,
            @RequestPart(required = false) MultipartFile logo,
            @RequestPart(required = false) MultipartFile banner) {
        return ResponseEntity.ok(collegeService.updateCollege(id, collegeRequest, logo, banner));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        collegeService.deleteCollege(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/publish")
    public ResponseEntity<CollegeResponse> publishCollege(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.publishCollege(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/unpublish")
    public ResponseEntity<CollegeResponse> unpublishCollege(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.unpublishCollege(id));
    }
}