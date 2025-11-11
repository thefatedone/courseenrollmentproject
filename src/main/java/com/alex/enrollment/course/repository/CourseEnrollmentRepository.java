package com.alex.enrollment.course.repository;

import com.alex.enrollment.course.model.CourseEnrollment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends CrudRepository<CourseEnrollment, Integer> {

    Optional<CourseEnrollment> findByEnrollmentStudentIdAndEnrollmentCourseId(Integer studentId, Integer courseId);

}
