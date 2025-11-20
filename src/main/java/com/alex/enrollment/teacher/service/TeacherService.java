package com.alex.enrollment.teacher.service;

import com.alex.enrollment.shared.exception.ResourceNotFoundException;
import com.alex.enrollment.teacher.dto.TeacherCreationDTO;
import com.alex.enrollment.teacher.dto.TeacherDTO;
import com.alex.enrollment.teacher.mapper.TeacherMapper;
import com.alex.enrollment.teacher.model.Teacher;
import com.alex.enrollment.teacher.repository.TeacherRepository;
import com.alex.enrollment.teacher.validation.TeacherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherValidator teacherValidator;

    public TeacherDTO createTeacher(TeacherCreationDTO teacherCreationDTO) {

        teacherValidator.validateTeacher(teacherCreationDTO, null);

        Teacher teacher = teacherMapper.toTeacher(teacherCreationDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.teachertoTeacherDTO(teacher);
    }

    public List<TeacherDTO> getAllTeachers() {

        Iterable<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherDTOs.add(teacherMapper.teachertoTeacherDTO(teacher));
        }

        return teacherDTOs;

    }

    public TeacherDTO getTeacherById(int id) throws ResourceNotFoundException {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + id + " not found"));

        TeacherDTO teacherDTO = teacherMapper.teachertoTeacherDTO(teacher);

        return teacherDTO;
    }

    public TeacherDTO updateTeacher(Integer id, TeacherCreationDTO teacherCreationDTO) throws ResourceNotFoundException {

        teacherValidator.validateTeacher(teacherCreationDTO, id);

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + id + " not found"));

        teacher.setFirstName(teacherCreationDTO.firstName());
        teacher.setLastName(teacherCreationDTO.lastName());
        teacher.setSubject(teacherCreationDTO.subject());
        teacher.setDateOfBirth(teacherCreationDTO.dateOfBirth());

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return teacherMapper.teachertoTeacherDTO(updatedTeacher);
        
    }

}
