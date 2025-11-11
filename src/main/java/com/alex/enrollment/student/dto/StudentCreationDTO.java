package com.alex.enrollment.student.dto;

import com.alex.enrollment.shared.model.Gender;
import jakarta.validation.constraints.*;

import java.util.Date;

public record StudentCreationDTO(@NotBlank String name, @NotBlank String lastName, @Min(1) @Max(4) Integer year,
                                 @NotNull Date dateOfBirth, @Email String email, @NotNull Gender gender) {
}
