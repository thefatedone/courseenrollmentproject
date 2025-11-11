package com.alex.enrollment.course.controller;

import com.alex.enrollment.course.dto.CourseCreationDTO;
import com.alex.enrollment.course.dto.CourseDTO;
import com.alex.enrollment.course.service.CourseService;
import com.alex.enrollment.shared.dto.ListResponseDTO;
import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<CourseDTO> createCourse(@RequestBody @Valid CourseCreationDTO courseCreationDTO) {
        return new ResponseDTO<>(courseService.createCourse(courseCreationDTO));
    }

    @GetMapping
    public ListResponseDTO<CourseDTO> getAllCourses() {
        return new ListResponseDTO<>(courseService.findAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseDTO<CourseDTO> getCourseById(@PathVariable int id) throws ResourceNotFoundException {
        return new ResponseDTO<>(courseService.findCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseDTO<CourseDTO> updateCourseById(@PathVariable(value="id") int courseId, @RequestBody CourseCreationDTO courseCreationDTO) throws ResourceNotFoundException {
        return new ResponseDTO<>(courseService.updateCourse(courseId, courseCreationDTO));
    }

}
