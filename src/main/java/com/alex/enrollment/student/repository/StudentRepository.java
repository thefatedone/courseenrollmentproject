package com.alex.enrollment.student.repository;

import com.alex.enrollment.student.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);

}
