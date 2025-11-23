package com.alex.enrollment.teacher.repository;

import com.alex.enrollment.teacher.model.Teacher;
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
@Sql(scripts = {"/db/schema/teachers.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataSet(
        value = "/db/data/teachers.yml",
        cleanBefore = true,
        strategy = SeedStrategy.CLEAN_INSERT,
        disableConstraints = true
)
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void givenFirstnameAndLastname_whenFindTeacher_expectTeacher() {
        String firstname = "Alexander";
        String lastname = "Pepanian";

        Optional<Teacher> result = teacherRepository.findByFirstNameAndLastName(firstname, lastname);
        assertTrue(result.isPresent());

        Teacher teacher = result.get();

        assertEquals(1, teacher.getTeacherId());
        assertEquals("Alexander", teacher.getFirstName());
        assertEquals("Pepanian", teacher.getLastName());
        assertEquals("Accounting", teacher.getSubject());
        assertEquals(
                java.sql.Date.valueOf("2006-06-21"),
                teacher.getDateOfBirth()
        );
    }

}
