package com.alex.enrollment.course.dto;

import jakarta.validation.constraints.*;

public record CourseCreationDTO(@NotBlank @Size(min = 5, max = 100) String courseName,
                                @Min(1) @Max(100) int courseCapacity, @NotNull Integer courseTeacher) {
}
