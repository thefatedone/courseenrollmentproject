package com.alex.enrollment.course.service;

import com.alex.enrollment.course.mapper.CourseMapper;
import com.alex.enrollment.course.dto.CourseCreationDTO;
import com.alex.enrollment.course.dto.CourseDTO;
import com.alex.enrollment.course.model.Course;
import com.alex.enrollment.course.repository.CourseRepository;
import com.alex.enrollment.course.validator.CourseValidator;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseValidator courseValidator;

    public CourseDTO createCourse(CourseCreationDTO courseCreationDTO) {

        courseValidator.validateCourse(courseCreationDTO, null);

        Course newCourse = courseMapper.toCourse(courseCreationDTO);

        newCourse = courseRepository.save(newCourse);

        return courseMapper.courseToCourseDTO(newCourse);

    }

    public List<CourseDTO> findAllCourses() {

        Iterable<Course> iterable = courseRepository.findAll();
        List<Course> courses = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        return courseMapper.coursesToCourseDTOs(courses);

    }

    public CourseDTO findCourseById(int id) throws ResourceNotFoundException {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return courseMapper.courseToCourseDTO(course);
    }

    public CourseDTO updateCourse(int id, CourseCreationDTO courseCreationDTO) throws ResourceNotFoundException {

        courseValidator.validateCourse(courseCreationDTO, id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        course.setCourseName(courseCreationDTO.courseName());
        course.setCourseCapacity(courseCreationDTO.courseCapacity());
        course.setCourseTeacher(courseCreationDTO.courseTeacher());

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.courseToCourseDTO(updatedCourse);
    }
}
