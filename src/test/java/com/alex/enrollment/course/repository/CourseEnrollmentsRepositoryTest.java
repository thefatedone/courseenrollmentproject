package com.alex.enrollment.course.repository;

import com.alex.enrollment.course.model.CourseEnrollment;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DBRider
@ActiveProfiles("test")
@Sql (scripts = {"/db/schema/courseenrollments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataSet(
        value = "/db/data/courseenrollments.yml",
        cleanBefore = true,
        strategy = SeedStrategy.CLEAN_INSERT,
        disableConstraints = true
)
public class CourseEnrollmentsRepositoryTest {
    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Test
    public void givenValidStudentIdAndCourseId_whenFindCourseEnrollment_expectCourseEnrollment() {
        Integer studentId = 1;
        Integer courseId = 2;

        Optional<CourseEnrollment> result = courseEnrollmentRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId, courseId);
        assertTrue(result.isPresent());

        CourseEnrollment courseEnrollment = result.get();

        assertEquals(studentId, courseEnrollment.getEnrollmentStudentId());
        assertEquals(courseId, courseEnrollment.getEnrollmentCourseId());
    }

}
