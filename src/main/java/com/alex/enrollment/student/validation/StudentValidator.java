package com.alex.enrollment.student.validation;

import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class StudentValidator {

    @Autowired
    private StudentRepository studentRepository;

    public void validateStudent(StudentCreationDTO studentCreationDTO, Integer studentId) {

        validateStudentAge(studentCreationDTO.dateOfBirth());

        validateEmail(studentCreationDTO.email(), studentId);

    }

    private void validateEmail(String email, Integer studentId) {

        Optional<Student> s = studentRepository.findByEmail(email);

        if (s.isPresent() && (studentId == null || s.get().getStudentId() != studentId)) {
            throw new IllegalArgumentException(email + " already exists");
        }

    }

    private void validateStudentAge(Date dob) {

        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < 16) {
            throw new IllegalArgumentException("Student must be at least 16 yo");
        }

    }
}
