package com.alex.enrollment.student.controller;

import com.alex.enrollment.shared.dto.ListResponseDTO;
import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import com.alex.enrollment.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<StudentDTO> saveStudent(@RequestBody @Valid StudentCreationDTO studentCreationDTO) {
        return new ResponseDTO<>(studentService.createStudent(studentCreationDTO));
    }

    @GetMapping//("/{id}")
//    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ListResponseDTO<StudentDTO> getAllStudents() {
        return new ListResponseDTO<>(studentService.findAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseDTO<StudentDTO> getStudentById(@PathVariable int id) throws ResourceNotFoundException {
        return new ResponseDTO<>(studentService.findStudentById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public ResponseDTO<StudentDTO> updateStudentById(@PathVariable int id, @RequestBody StudentCreationDTO studentCreationDTO) throws ResourceNotFoundException {
        return new ResponseDTO<>(studentService.updateStudent(id, studentCreationDTO));
    }

}
