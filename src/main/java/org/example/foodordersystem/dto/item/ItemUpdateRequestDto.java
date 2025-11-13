package org.example.foodordersystem.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;


@Value
public class ItemUpdateRequestDto {

    @NotBlank(message = "is required")
    @Size(min = 36, max = 36, message = "must be a valid UUID")
    String id;

    @Size(min = 10, message = "must be at least 10 characters long")
    String description;

    @DecimalMin(value = "0.01", message = "must be greater than 0")
    Double price;
}
