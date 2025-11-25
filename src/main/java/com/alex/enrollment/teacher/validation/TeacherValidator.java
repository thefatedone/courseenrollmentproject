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

    public void validateTeacher(TeacherCreationDTO teacherCreationDTO, Integer teacherId) {

        if (teacherId == null) {
            return;
        }

        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("Teacher with id " + teacherId + " not exists");
        }

    }

}
