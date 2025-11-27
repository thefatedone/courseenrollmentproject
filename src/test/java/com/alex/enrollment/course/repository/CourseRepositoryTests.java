package com.alex.enrollment.course.repository;

import com.alex.enrollment.course.model.Course;
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
@SpringBootTest
@DBRider
@ActiveProfiles("test")
@Sql (scripts = {"/db/schema/courses.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataSet(
        value = "/db/data/courses.yml",
        cleanBefore = true,
        strategy = SeedStrategy.CLEAN_INSERT,
        disableConstraints = true
)
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void givenValidCourseName_whenFindCourse_expectCourse() {
        String courseName = "Math";

        Optional<Course> result = courseRepository.findByCourseName(courseName);
        assertTrue(result.isPresent());

        Course course = result.get();

        assertEquals("Math", course.getCourseName());
    }

}
