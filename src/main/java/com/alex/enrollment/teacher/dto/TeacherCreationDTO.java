package com.alex.enrollment.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record TeacherCreationDTO(@NotBlank String firstName, @NotBlank String lastName,
                                 @NotBlank String subject, @NotNull Date dateOfBirth) {
}
