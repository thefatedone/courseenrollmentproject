package com.alex.enrollment.teacher.repository;

import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.teacher.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

    Optional<Teacher> findByFirstNameAndLastName(String firstName, String lastName);

}
