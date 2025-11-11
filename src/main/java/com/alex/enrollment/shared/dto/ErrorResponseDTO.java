package com.alex.enrollment.shared.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(String errorId, HttpStatus status, String path, String errorDescription) {
}
