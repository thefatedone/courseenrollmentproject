package com.alex.enrollment.student;

import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.shared.model.Gender;
import com.alex.enrollment.student.dto.StudentCreationDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import com.alex.enrollment.student.model.Student;
import com.alex.enrollment.student.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static com.alex.enrollment.student.StudentResultMatcher.studentResultMatcher;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(DBUnitExtension.class)
@DBRider
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/db/schema/students.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataSet(
        value = "/db/data/students.yml",
        cleanBefore = true,
        strategy = SeedStrategy.CLEAN_INSERT,
        disableConstraints = true
)
@DBUnit
public class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void givenValidId_whenFindStudentById_expectResult() throws Exception {
        int studentId = 1;
        String url = "/student/" + studentId;

        ResponseDTO<StudentDTO> expectedResponse = readStudentRespons("classpath:json/response/find_student_by_id_response.json");

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(studentResultMatcher(expectedResponse).hasExpectedData());

    }

    @Test
    public void givenValidStudentCreationRequest_whenCreateStudent_expectSuccess() throws Exception {

        StudentCreationDTO studentCreationDTO = new StudentCreationDTO("Test", "Lastname", 3,
                new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), "test@mail.com",  Gender.MALE);

        String creationStringBody = objectMapper.writeValueAsString(studentCreationDTO);

        ResponseDTO<StudentDTO> expectedResponse = readStudentRespons("classpath:json/response/create_student_response.json");

        String url = "/student";
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creationStringBody)
                )
                .andExpect(status().isCreated())
                .andExpect(studentResultMatcher(expectedResponse).hasExpectedData());

        Optional<Student> result = studentRepository.findByFirstNameAndLastName("Test", "Lastname");
        assertTrue(result.isPresent());
        Student student = result.get();
        assertEquals("Test", student.getFirstName());
        assertEquals("Lastname", student.getLastName());
        assertEquals(3, student.getYear());
        assertEquals("test@mail.com", student.getEmail());

    }

    @Test
    public void givenInvalidStudentCreationRequest_whenCreateStudent_expectBadRequest() throws Exception {
        StudentCreationDTO studentCreationDTO = new StudentCreationDTO("Test", "Lastname", 3,
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"), "test@mail.com",  Gender.MALE);

        String creationStringBody = objectMapper.writeValueAsString(studentCreationDTO);


        String url = "/student";
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creationStringBody)
                )
                .andExpect(status().isBadRequest());

    }

    private ResponseDTO<StudentDTO> readStudentRespons(String path) throws IOException {
        return objectMapper.readValue(resourceLoader.getResource(path).getFile(), new TypeReference<>(){});
    }

}
