package com.example.college_explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.college_explorer.dto.CollegeRequest;
import com.example.college_explorer.dto.CollegeResponse;
import com.example.college_explorer.service.CollegeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/test")
    public String testAdmin() {
        return "Admin access granted!";
    }

    @PostMapping(value = "/colleges", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CollegeResponse> addCollege(
            @RequestPart("college") String collegeJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "banner", required = false) MultipartFile banner) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollegeRequest collegeRequest = mapper.readValue(collegeJson, CollegeRequest.class);
            CollegeResponse response = collegeService.addCollege(collegeRequest, logo, banner);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/colleges/{id}/featured")
    public ResponseEntity<CollegeResponse> markAsFeatured(
            @PathVariable Long id,
            @RequestParam String section,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String label) {
        return ResponseEntity.ok(collegeService.markAsFeatured(id, section, priority, label));
    }

    @PutMapping("/colleges/{id}/banner")
    public ResponseEntity<CollegeResponse> markAsBanner(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer priority) {
        return ResponseEntity.ok(collegeService.markAsBanner(id, title, description, priority));
    }

    @DeleteMapping("/colleges/{id}/featured")
    public ResponseEntity<CollegeResponse> removeFromFeatured(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.removeFromFeatured(id));
    }

    @DeleteMapping("/colleges/{id}/banner")
    public ResponseEntity<CollegeResponse> removeFromBanner(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.removeFromBanner(id));
    }
}