package com.alex.enrollment.course.validator;

import com.alex.enrollment.course.dto.CourseCreationDTO;
import com.alex.enrollment.course.dto.CourseEnrollmentCreationDTO;
import com.alex.enrollment.course.model.Course;
import com.alex.enrollment.course.model.CourseEnrollment;
import com.alex.enrollment.course.repository.CourseEnrollmentRepository;
import com.alex.enrollment.course.repository.CourseRepository;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CourseValidator {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void validateCourse(CourseCreationDTO courseCreationDTO, Integer courseId) {

        Optional<Course> t = courseRepository.findByCourseName(courseCreationDTO.courseName());

        if (t.isPresent() && (courseId == null || !courseId.equals(t.get().getCourseId()))) {
            throw new IllegalArgumentException(courseCreationDTO.courseName() + " already exists");
        }
    }

    public void validateCourseEnrollment(CourseEnrollmentCreationDTO courseEnrollmentCreationDTO) {

        Optional<Course> course = courseRepository.findById(courseEnrollmentCreationDTO.enrollmentCourseId());

        if (!course.isPresent()) {
            throw new IllegalArgumentException(courseEnrollmentCreationDTO.enrollmentCourseId() + " does not exist");
        }

        Optional<Student> student = studentRepository.findById(courseEnrollmentCreationDTO.enrollmentStudentId());

        if (!student.isPresent()) {
            throw new IllegalArgumentException(courseEnrollmentCreationDTO.enrollmentStudentId() + " does not exist");
        }

        Optional<CourseEnrollment> courseEnrollment = courseEnrollmentRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(courseEnrollmentCreationDTO.enrollmentStudentId(),
                courseEnrollmentCreationDTO.enrollmentCourseId());

        if (courseEnrollment.isPresent()) {
            throw new IllegalArgumentException(courseEnrollmentCreationDTO.enrollmentStudentId() + " already enrolled to this course "
                    + courseEnrollmentCreationDTO.enrollmentCourseId());
        }

        if (courseEnrollmentCreationDTO.enrollmentEndDate().before(courseEnrollmentCreationDTO.enrollmentStartDate()) ) {
            throw new IllegalArgumentException("Enrollment end date cannot be before start date");
        }

    }
}
