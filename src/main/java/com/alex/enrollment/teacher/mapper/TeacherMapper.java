package com.alex.enrollment.teacher.mapper;

import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.dto.TeacherDTO;
import com.alex.enrollment.teacher.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toTeacher(TeacherCreationDTO teacherCreationDTO) {

        Teacher teacher = new Teacher();

        teacher.setFirstName(teacherCreationDTO.firstName());
        teacher.setLastName(teacherCreationDTO.lastName());
        teacher.setSubject(teacherCreationDTO.subject());
        teacher.setDateOfBirth(teacherCreationDTO.dateOfBirth());

        return teacher;
    }

    public TeacherDTO teachertoTeacherDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getTeacherId(), teacher.getFirstName(), teacher.getLastName());
    }

}
