package com.alex.enrollment.teacher.validation;

import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.model.Teacher;
import com.alex.enrollment.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TeacherValidator {

    @Autowired
    private TeacherRepository teacherRepository;

    public void validateTeacher(TeacherCreationDTO teacherCreationDTO, int teacherId) {

        Optional<Teacher> t = teacherRepository.findById(teacherId);

        if (!t.isPresent()) {
            throw new IllegalArgumentException(teacherId + " already exists");
        }

    }

}
