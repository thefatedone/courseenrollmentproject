package com.alex.enrollment.course.service;

import com.alex.enrollment.course.dto.CourseEnrollmentCreationDTO;
import com.alex.enrollment.course.dto.CourseEnrollmentDTO;
import com.alex.enrollment.course.mapper.CourseMapper;
import com.alex.enrollment.course.model.CourseEnrollment;
import com.alex.enrollment.course.repository.CourseEnrollmentRepository;
import com.alex.enrollment.course.validator.CourseValidator;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseEnrollmentService {

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private CourseMapper courseMapper;

    public CourseEnrollmentDTO createCourseEnrollment(CourseEnrollmentCreationDTO courseEnrollmentCreationDTO) {

        courseValidator.validateCourseEnrollment(courseEnrollmentCreationDTO);

        CourseEnrollment newCourseEnrollment = courseMapper.toCourseEnrollment(courseEnrollmentCreationDTO);

        newCourseEnrollment = courseEnrollmentRepository.save(newCourseEnrollment);

        CourseEnrollmentDTO newCourseEnrollmentDTO = courseMapper.courseEnrollmentToCourseEnrollmentDTO(newCourseEnrollment);

        return newCourseEnrollmentDTO;
    }

    public CourseEnrollmentDTO findCourseEnrollmentById(int id) throws ResourceNotFoundException {

        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Enrollment not found"));

        return courseMapper.courseEnrollmentToCourseEnrollmentDTO(courseEnrollment);
    }

    public List<CourseEnrollmentDTO> findAllCourseEnrollments() {

        Iterable<CourseEnrollment> iterable = courseEnrollmentRepository.findAll();
        List<CourseEnrollment> courseEnrollments = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        return courseMapper.courseEnrollmentsToCourseEnrollmentDTOs(courseEnrollments);
    }

    public CourseEnrollmentDTO updateCourseEnrollment(int id, CourseEnrollmentCreationDTO courseEnrollmentCreationDTO) throws ResourceNotFoundException {

        courseValidator.validateCourseEnrollment(courseEnrollmentCreationDTO);

        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Enrollment not found with id: " + id));

        courseEnrollment.setEnrollmentStudentId(courseEnrollmentCreationDTO.enrollmentStudentId());
        courseEnrollment.setEnrollmentCourseId(courseEnrollmentCreationDTO.enrollmentCourseId());
        courseEnrollment.setEnrollmentStartDate(courseEnrollmentCreationDTO.enrollmentStartDate());
        courseEnrollment.setEnrollmentEndDate(courseEnrollmentCreationDTO.enrollmentEndDate());

        CourseEnrollment updatedCourseEnrollment = courseEnrollmentRepository.save(courseEnrollment);

        return courseMapper.courseEnrollmentToCourseEnrollmentDTO(updatedCourseEnrollment);

    }

}
