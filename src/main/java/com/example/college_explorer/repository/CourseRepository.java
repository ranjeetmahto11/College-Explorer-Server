package com.example.college_explorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.college_explorer.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}