package com.alex.enrollment.shared.dto;

import java.util.List;

public record ListResponseDTO<T>(List<T> data) {
}
