package com.alex.enrollment.course.controller;

import com.alex.enrollment.course.dto.CourseEnrollmentCreationDTO;
import com.alex.enrollment.course.dto.CourseEnrollmentDTO;
import com.alex.enrollment.course.service.CourseEnrollmentService;
import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/enrollment")
public class CourseEnrollmentController {

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<CourseEnrollmentDTO> createCourseEnrollment(@RequestBody @Valid final CourseEnrollmentCreationDTO courseEnrollmentCreationDTO) {
        return new ResponseDTO<>(courseEnrollmentService.createCourseEnrollment(courseEnrollmentCreationDTO));
    }

    @GetMapping
    public ResponseDTO<List<CourseEnrollmentDTO>> getAllCourseEnrollments() {
        return new ResponseDTO<>(courseEnrollmentService.findAllCourseEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseDTO<CourseEnrollmentDTO> getCourseEnrollmentById(@PathVariable final int id) throws ResourceNotFoundException {
        return new ResponseDTO<>(courseEnrollmentService.findCourseEnrollmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseDTO<CourseEnrollmentDTO> updateCourseEnrollmentById(
            @PathVariable("id") int id,
            @RequestBody CourseEnrollmentCreationDTO courseEnrollmentCreationDTO
    ) throws ResourceNotFoundException {

        CourseEnrollmentDTO updatedCourseEnrollment =
                courseEnrollmentService.updateCourseEnrollment(id, courseEnrollmentCreationDTO);

        return new ResponseDTO<>(updatedCourseEnrollment);
    }

}
