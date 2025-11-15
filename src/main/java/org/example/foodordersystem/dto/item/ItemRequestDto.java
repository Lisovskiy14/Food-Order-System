package org.example.foodordersystem.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ItemRequestDto {

    @NotBlank(message = "is required")
    @Size(min = 2, max = 20, message = "must be between 2 and 20 characters")
    String name;

    @NotBlank(message = "is required")
    @Size(min = 10, message = "must be at least 10 characters")
    String description;

    @DecimalMin(value = "0.01", message = "must be greater than 0")
    BigDecimal price;
}
