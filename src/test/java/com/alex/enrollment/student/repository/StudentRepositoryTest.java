package com.alex.enrollment.student.repository;

import com.alex.enrollment.shared.model.Gender;
import com.alex.enrollment.student.model.Student;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(DBUnitExtension.class)
@DBRider
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/db/schema/students.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataSet(
        value = "/db/data/students.yml",
        cleanBefore = true,
        strategy = SeedStrategy.CLEAN_INSERT,
        disableConstraints = true
)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void givenValidEmail_whenFindStudent_expectStudent() {
        String email = "john.doe@email.com";

        Optional<Student> result = studentRepository.findByEmail(email);

        assertTrue(result.isPresent());

        Student student = result.get();

        assertEquals(1, student.getStudentId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals(Gender.MALE, student.getGender());

    }

    @Test
    public void givenFirstnameAndLastname_whenFindStudent_expectStudent() {
        String firstName = "John";
        String lastName = "Doe";

        Optional<Student> result = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        assertTrue(result.isPresent());

        Student student = result.get();

        assertEquals(1, student.getStudentId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals(Gender.MALE, student.getGender());
        assertEquals("john.doe@email.com", student.getEmail());
    }

}
