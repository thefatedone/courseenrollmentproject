package com.alex.enrollment.course.mapper;

import com.alex.enrollment.course.dto.CourseCreationDTO;
import com.alex.enrollment.course.dto.CourseDTO;
import com.alex.enrollment.course.dto.CourseEnrollmentCreationDTO;
import com.alex.enrollment.course.dto.CourseEnrollmentDTO;
import com.alex.enrollment.course.model.Course;
import com.alex.enrollment.course.model.CourseEnrollment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toCourse(CourseCreationDTO courseCreationDTO) {

        Course course = new Course();

        course.setCourseName(courseCreationDTO.courseName());
        course.setCourseCapacity(courseCreationDTO.courseCapacity());
        course.setCourseTeacher(courseCreationDTO.courseTeacher());

        return course;

    }

    public CourseDTO courseToCourseDTO(Course course) {
        return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseCapacity(), course.getCourseTeacher());
    }

    public List<CourseDTO> coursesToCourseDTOs(List<Course> courses) {
        return courses.stream()
                .map(course -> courseToCourseDTO(course))
                .collect(Collectors.toList());
    }

    public CourseEnrollment toCourseEnrollment(CourseEnrollmentCreationDTO courseEnrollmentCreationDTO) {

        CourseEnrollment courseEnrollment = new CourseEnrollment();

        courseEnrollment.setEnrollmentStudentId(courseEnrollmentCreationDTO.enrollmentStudentId());
        courseEnrollment.setEnrollmentCourseId(courseEnrollmentCreationDTO.enrollmentCourseId());
        courseEnrollment.setEnrollmentStartDate(courseEnrollmentCreationDTO.enrollmentStartDate());
        courseEnrollment.setEnrollmentEndDate(courseEnrollmentCreationDTO.enrollmentEndDate());
        courseEnrollment.setEnrollmentDate(new Date());

        return courseEnrollment;

    }

    public CourseEnrollmentDTO courseEnrollmentToCourseEnrollmentDTO(CourseEnrollment courseEnrollment) {
        return new CourseEnrollmentDTO(courseEnrollment.getEnrollmentId(), courseEnrollment.getEnrollmentDate());
    }

    public List<CourseEnrollmentDTO> courseEnrollmentsToCourseEnrollmentDTOs(List<CourseEnrollment> courseEnrollments) {
        return courseEnrollments.stream()
                .map(courseEnrollment -> courseEnrollmentToCourseEnrollmentDTO(courseEnrollment))
                .collect(Collectors.toList());
    }
}