package com.alex.enrollment.student.service;

import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.shared.model.Gender;
import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import com.alex.enrollment.student.mapper.StudentMapper;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import com.alex.enrollment.student.validation.StudentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;


    @Test
    public void givenValidationError_whenCreatingStudent_expectException() {
        String name = "John";
        String lastName = "Doe";
        String email = "email@email.com";
        Date dob = new Date();

        StudentCreationDTO studentCreationDTO = createStudentCreationDTO(name, lastName, dob, email);

        doThrow(new IllegalArgumentException("Validation error"))
                .when(studentValidator).validateStudent(studentCreationDTO, null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(studentCreationDTO));

        assertEquals("Validation error", ex.getMessage());

        verify(studentMapper, never()).toStudent(studentCreationDTO);
        verify(studentRepository, never()).save(any());
        verify(studentMapper, never()).studentToStudentDTO(any());

    }


    @Test
    public void givenValidCreateRequest_whenCreatingStudent_expectSuccess() {
        String name = "John";
        String lastName = "Doe";
        String email = "email@email.com";
        Date dob = new Date();
        Integer id = 1;

        StudentCreationDTO studentCreationDTO = createStudentCreationDTO(name, lastName, dob, email);
        Student student = createStudent(id, name, lastName, email, dob);
        StudentDTO studentDTO = createStudentDTO(id, name, lastName, email);

        doNothing().when(studentValidator).validateStudent(studentCreationDTO, null);
        when(studentMapper.toStudent(studentCreationDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.studentToStudentDTO(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.createStudent(studentCreationDTO);

        assertEquals(studentDTO.studentId(), result.studentId());
        assertEquals(studentDTO.firstName(), result.firstName());
        assertEquals(studentDTO.lastName(), result.lastName());
        assertEquals(studentDTO.email(), result.email());

        verify(studentMapper, times(1)).toStudent(studentCreationDTO);
        verify(studentRepository, times(1)).save(any());
        verify(studentMapper, times(1)).studentToStudentDTO(any());
    }

    @Test
    public void givenNonExistingStudent_whenFindStudentById_expectException() {

        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> studentService.findStudentById(999));

        assertEquals("Student with id 999 not found", ex.getMessage());
    }

    @Test
    public void givenExistingStudent_whenFindStudentById_expectSuccess() throws ResourceNotFoundException {

        Integer id = 1;
        Student entity = createStudentEntity(1, "Alexander", "Pepanian", "mail@mail.com");
        StudentDTO dto = createStudentDTO(1, "Alexander", "Pepanian", "mail@mail.com");

        when(studentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(studentMapper.studentToStudentDTO(entity)).thenReturn(dto);

        StudentDTO result = studentService.findStudentById(id);

        assertEquals("Alexander", result.firstName());
        assertEquals("Pepanian", result.lastName());
        assertEquals("mail@mail.com", result.email());
    }

    @Test
    public void givenNonExistingStudent_whenUpdateStudent_expectException() {

        Integer id = 999;

        StudentCreationDTO studentCreationDTO = createStudentCreationDTO("John", "Doe", new Date(), "mail@mail.com");
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(id, studentCreationDTO));

        assertEquals("Student with id 999 not found", ex.getMessage());
    }

    @Test
    public void givenExistingStudent_whenUpdateStudent_expectSuccess() throws ResourceNotFoundException {

        Integer id = 1;

        StudentCreationDTO creationDTO =
                createStudentCreationDTO("Saul", "Goodman", new Date(), "mail@mail.com");

        Student existEntity =
                createStudentEntity(id, "Some", "Lastname", "somelast@mail.com");

        StudentDTO returnDTO =
                createStudentDTO(id, "Saul", "Goodman", "mail@mail.com");

        doNothing().when(studentValidator).validateStudent(creationDTO, id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(existEntity));
        when(studentRepository.save(existEntity)).thenReturn(existEntity);
        when(studentMapper.studentToStudentDTO(existEntity)).thenReturn(returnDTO);

        StudentDTO result = studentService.updateStudent(id, creationDTO);

        assertEquals("Saul", result.firstName());
        assertEquals("Goodman", result.lastName());
        assertEquals("mail@mail.com", result.email());

        verify(studentRepository, times(1)).save(existEntity);
    }




    private Student createStudent(Integer id, String name, String lastname, String email, Date dob) {
        Student student = new Student();
        student.setStudentId(id);
        student.setFirstName(name);
        student.setLastName(lastname);
        student.setEmail(email);
        student.setDateOfBirth(dob);

        return student;
    }

    private StudentCreationDTO createStudentCreationDTO(String name, String lastname, Date dob, String email) {
        StudentCreationDTO scdto = new StudentCreationDTO(name, lastname, 1, dob, email, Gender.MALE);
        return scdto;
    }

    private StudentDTO createStudentDTO(Integer id, String name, String lastname, String email) {
        return new StudentDTO(id, name, lastname, email);
    }

    private Student createStudentEntity(Integer id, String name, String lastname, String email) {
        Student s = new Student();
        s.setStudentId(id);
        s.setFirstName(name);
        s.setLastName(lastname);
        s.setEmail(email);
        return s;
    }

}
