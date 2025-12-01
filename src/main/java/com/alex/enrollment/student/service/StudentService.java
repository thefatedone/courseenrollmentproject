package com.alex.enrollment.student.service;

import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import com.alex.enrollment.student.mapper.StudentMapper;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import com.alex.enrollment.student.validation.StudentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentValidator studentValidator;

    public StudentDTO createStudent(StudentCreationDTO studentCreationDTO) {

        LOGGER.info(String.format("Received request to create student: %s", studentCreationDTO.toString()));

        studentValidator.validateStudent(studentCreationDTO, null);

        Student newStudent = studentMapper.toStudent(studentCreationDTO);

        newStudent = studentRepository.save(newStudent);

        StudentDTO newStudentDTO = studentMapper.studentToStudentDTO(newStudent);

        LOGGER.debug(String.format("Created new student with id = %d", newStudentDTO.studentId()));

        return newStudentDTO;
    }

    public List<StudentDTO> findAllStudents() {

        Iterable<Student> iterable = studentRepository.findAll();
        List<Student> students = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        List<StudentDTO> studentDTOS = studentMapper.studentsToStudentDTOs(students);

        return studentDTOS;

    }

    public StudentDTO findStudentById(Integer id) throws ResourceNotFoundException {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        return studentMapper.studentToStudentDTO(student);
    }


    public StudentDTO updateStudent(Integer id, StudentCreationDTO studentCreationDTO) throws ResourceNotFoundException {

        studentValidator.validateStudent(studentCreationDTO, id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        student.setFirstName(studentCreationDTO.name());
        student.setLastName(studentCreationDTO.lastName());
        student.setYear(studentCreationDTO.year());
        student.setDateOfBirth(studentCreationDTO.dateOfBirth());
        student.setEmail(studentCreationDTO.email());
        student.setGender(studentCreationDTO.gender());

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.studentToStudentDTO(updatedStudent);
    }


}
