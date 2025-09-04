package com.example.college_explorer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.college_explorer.model.College;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    boolean existsBySlug(String slug);

    List<College> findByType(College.CollegeType type);

   List<College> findByFeaturedSectionAndFeatured(College.FeaturedSection featuredSection, boolean featured);
}