package com.alex.enrollment.student.validation;

import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.model.Teacher;
import com.alex.enrollment.teacher.repository.TeacherRepository;
import com.alex.enrollment.teacher.validation.TeacherValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherValidatorTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherValidator teacherValidator;

    @Test
    public void givenTeacherDoesNotExist_whenValidatingTeacher_expectException() {

        Integer teacherId = 1;
        TeacherCreationDTO teacherCreationDTO = createTeacher();

        when(teacherRepository.findById(teacherId))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> teacherValidator.validateTeacher(teacherCreationDTO, teacherId)
        );

        assertEquals("1 already exists", ex.getMessage());
    }

    @Test
    public void givenTeacherExists_whenValidatingTeacher_expectSuccess() {

        Integer teacherId = 1;
        TeacherCreationDTO teacherCreationDTO = createTeacher();

        when(teacherRepository.findById(teacherId))
                .thenReturn(Optional.of(new Teacher()));

        assertDoesNotThrow(() -> teacherValidator.validateTeacher(teacherCreationDTO, teacherId));
    }

    private TeacherCreationDTO createTeacher() {
        return new TeacherCreationDTO("Alexander", "Pepanian", "French Language", new Date());
    }
}
