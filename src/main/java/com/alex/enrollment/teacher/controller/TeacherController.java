package com.alex.enrollment.teacher.controller;

import com.alex.enrollment.shared.dto.ListResponseDTO;
import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.dto.TeacherDTO;
import com.alex.enrollment.teacher.service.TeacherService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<TeacherDTO> createTeacher(@RequestBody @Valid TeacherCreationDTO teacherCreationDTO) {
        TeacherDTO teacherDTO = teacherService.createTeacher(teacherCreationDTO);
        ResponseDTO<TeacherDTO> responseDTO = new ResponseDTO<>(teacherDTO);
        return responseDTO;
    }

    @GetMapping
    public ListResponseDTO<TeacherDTO> getAllTeachers() {
        return new ListResponseDTO<>(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseDTO<TeacherDTO> getTeacherById(@PathVariable @Positive Integer id) throws ResourceNotFoundException {
        return new ResponseDTO<>(teacherService.getTeacherById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public ResponseDTO<TeacherDTO> updateTeacherById(@PathVariable int id, @RequestBody TeacherCreationDTO teacherCreationDTO) throws ResourceNotFoundException {
        return new ResponseDTO<>(teacherService.updateTeacher(id, teacherCreationDTO));
    }

}
