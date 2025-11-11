package com.alex.enrollment.course.repository;

import com.alex.enrollment.course.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {


    Optional<Course> findByCourseName(String courseName);


}
