package com.alex.enrollment.student.validation;

import com.alex.enrollment.shared.model.Gender;
import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentValidatorTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentValidator studentValidator;

    @Test
    public void givenStudentYoungerThan16_whenValidatingStudent_expectException() {

        StudentCreationDTO studentCreationDTO = createStudent(new Date(), "mail@mail.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> studentValidator.validateStudent(studentCreationDTO, null));

        assertEquals("Student must be at least 16 yo", ex.getMessage());
    }

    @Test
    public void givenStudentByEmailExist_whenValidatingStudent_expectException() throws ParseException {

        String email = "mail@mail.com";
        Date dob = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000");
        StudentCreationDTO studentCreationDTO = createStudent(dob, email);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(new Student()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> studentValidator.validateStudent(studentCreationDTO, null));

        assertEquals(email + " already exists", ex.getMessage());
    }

    @Test
    public void givenValidStudent_whenValidatingStudent_expectSuccess() throws ParseException {

        String email = "mail@mail.com";
        Date dob = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000");
        StudentCreationDTO studentCreationDTO = createStudent(dob, email);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));

        assertDoesNotThrow(() -> studentValidator.validateStudent(studentCreationDTO, null));

    }

    private StudentCreationDTO createStudent(Date dob, String email) {
        StudentCreationDTO scdto = new StudentCreationDTO("John", "Doe", 1, dob, email, Gender.MALE);
        return scdto;
    }

}
