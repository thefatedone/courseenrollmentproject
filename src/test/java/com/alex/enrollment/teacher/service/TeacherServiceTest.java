package com.alex.enrollment.teacher.service;

import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.dto.TeacherDTO;
import com.alex.enrollment.teacher.mapper.TeacherMapper;
import com.alex.enrollment.teacher.model.Teacher;
import com.alex.enrollment.teacher.repository.TeacherRepository;
import com.alex.enrollment.teacher.validation.TeacherValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private TeacherValidator teacherValidator;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    public void givenValidTeacher_whenCreateTeacher_expectException() {

        TeacherCreationDTO teacherCreationDTO = getTeacherCreationDTO();

        doThrow(new IllegalArgumentException("Validation error"))
                .when(teacherValidator).validateTeacher(any(), any());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> teacherService.createTeacher(teacherCreationDTO));

        assertEquals("Validation error", ex.getMessage());

        verify(teacherMapper, never()).toTeacher(any());
        verify(teacherRepository, never()).save(any());
        verify(teacherValidator, times(1)).validateTeacher(any(), any());
    }

    @Test
    public void givenValidTeacher_whenCreateTeacher_expectSuccess() {

        TeacherCreationDTO teacherCreationDTO = getTeacherCreationDTO();
        Teacher teacher = getTeacher();
        TeacherDTO teacherDTO = getTeacherDTO();

        when(teacherMapper.toTeacher(teacherCreationDTO)).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.teachertoTeacherDTO(teacher)).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.createTeacher(teacherCreationDTO);

        assertNotNull(result);
        assertEquals(teacherDTO, result);

    }

    @Test
    public void givenValidTeacher_whenFindTeacherById_expectException() {

        when(teacherRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> teacherService.getTeacherById(999));

        assertEquals("Teacher with id 999 not found", ex.getMessage());

    }

    @Test
    public void givenValidTeacher_whenFindTeacherById_expectSuccess() throws ResourceNotFoundException {

        Integer id = 1;
        Teacher entity = createTeacherEntity(1, "Alexander", "Pepanian", "Math", date("2006-06-21"));
        TeacherDTO teacherDTO = createTeacherDTO(1, "Alexander", "Pepanian");

        when(teacherRepository.findById(id)).thenReturn(Optional.of(entity));
        when(teacherMapper.teachertoTeacherDTO(entity)).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.getTeacherById(id);

        assertEquals("Alexander", result.firstName());
        assertEquals("Pepanian", result.lastName());

    }

    @Test
    public void givenValidTeacher_whenUpdateTeacher_expectException() {

        Integer id = 999;

        TeacherCreationDTO teacherCreationDTO = getTeacherCreationDTO();
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> teacherService.updateTeacher(id, teacherCreationDTO));

        assertEquals("Teacher with id 999 not found", ex.getMessage());

    }

    @Test
    public void givenValidTeacher_whenUpdateTeacher_expectSuccess() throws ResourceNotFoundException {

        Integer id = 1;

        TeacherCreationDTO teacherCreationDTO =
                createTeacherCreationDTO("Saul", "Goodman", "French", date("2006-06-21"));

        Teacher existEntity =
                createTeacherEntity(1,"Some", "Name","Math", date("2006-06-21"));

        TeacherDTO returnDTO =
                createTeacherDTO(1,"Saul", "Goodman");

        doNothing().when(teacherValidator).validateTeacher(teacherCreationDTO, id);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(existEntity));
        when(teacherRepository.save(existEntity)).thenReturn(existEntity);
        when(teacherMapper.teachertoTeacherDTO(existEntity)).thenReturn(returnDTO);

        TeacherDTO result = teacherService.updateTeacher(id, teacherCreationDTO);

        assertEquals("Saul", result.firstName());
        assertEquals("Goodman", result.lastName());

        verify(teacherRepository, times(1)).save(existEntity);
    }

    private TeacherCreationDTO createTeacherCreationDTO(String firstName, String lastName, String subject, Date dateOfBirth) {
        TeacherCreationDTO tcdto = new TeacherCreationDTO(firstName, lastName, subject, dateOfBirth);
        return tcdto;
    }

    private TeacherCreationDTO getTeacherCreationDTO() {

        String firstName = "firstName";
        String lastName = "lastName";
        String subject = "subject";
        Date dateOfBirth = new Date();

        return new TeacherCreationDTO(firstName, lastName, subject, dateOfBirth);

    }

    private Teacher getTeacher() {

        Integer teacherId = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        String subject = "subject";
        Date dateOfBirth = new Date();

        return new Teacher(teacherId, firstName, lastName, subject, dateOfBirth);

    }

    private TeacherDTO getTeacherDTO() {

        Integer teacherId = 1;
        String firstName = "firstName";
        String lastName = "lastName";

        return new TeacherDTO(teacherId, firstName, lastName);

    }

    private Teacher createTeacherEntity(Integer id, String firstName, String lastName, String subject, Date dateOfBirth) {
        Teacher t = new Teacher();
        t.setTeacherId();
        t.setFirstName(firstName);
        t.setLastName(lastName);
        t.setSubject(subject);
        t.setDateOfBirth(new Date());
        return t;
    }

    private TeacherDTO createTeacherDTO(Integer id, String firstName, String lastName) {
        return new TeacherDTO(id, firstName, lastName);
    }

    private Date date(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
