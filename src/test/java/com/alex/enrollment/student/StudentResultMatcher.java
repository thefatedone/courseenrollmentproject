package com.alex.enrollment.student;

import com.alex.enrollment.shared.dto.ResponseDTO;
import com.alex.enrollment.student.dto.StudentDTO;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StudentResultMatcher {

    private ResponseDTO<StudentDTO> expectedResponse;

    private StudentResultMatcher(ResponseDTO<StudentDTO> expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

    public static StudentResultMatcher studentResultMatcher(ResponseDTO<StudentDTO> response) {
        return new StudentResultMatcher(response);
    }

    public ResultMatcher hasExpectedData() {
        StudentDTO studentDTO = expectedResponse.data();

        return (result) -> {
            jsonPath("$.data.studentId").isNumber();
            jsonPath("$.data.firstName").value(studentDTO.firstName()).match(result);
            jsonPath("$.data.lastName").value(studentDTO.lastName()).match(result);
            jsonPath("$.data.email").value(studentDTO.email()).match(result);

        };
    }
}
