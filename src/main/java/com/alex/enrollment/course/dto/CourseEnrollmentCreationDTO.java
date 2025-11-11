package com.alex.enrollment.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CourseEnrollmentCreationDTO(@NotNull @Positive Integer enrollmentStudentId, @NotNull @Positive Integer enrollmentCourseId,
                                          @NotNull Date enrollmentStartDate, @NotNull Date enrollmentEndDate) {
}
