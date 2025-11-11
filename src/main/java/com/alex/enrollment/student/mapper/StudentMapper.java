package com.alex.enrollment.student.mapper;

import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import com.alex.enrollment.student.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {

    public Student toStudent(StudentCreationDTO studentCreationDTO) {

        Student student = new Student();

        student.setFirstName(studentCreationDTO.name());
        student.setLastName(studentCreationDTO.lastName());
        student.setEmail(studentCreationDTO.email());
        student.setDateOfBirth(studentCreationDTO.dateOfBirth());
        student.setYear(studentCreationDTO.year());
        student.setGender(studentCreationDTO.gender());
        return student;

    }

    public StudentDTO studentToStudentDTO(Student student) {
        return new StudentDTO(student.getStudentId(), student.getFirstName(), student.getLastName(), student.getEmail());
    }

    public List<StudentDTO> studentsToStudentDTOs(List<Student> students) {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(studentToStudentDTO(student));
        }
        return studentDTOS;
    }
}
