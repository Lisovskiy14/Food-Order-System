package org.example.foodordersystem.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CustomerRequestDto {

    @NotBlank(message = "is required")
    @Size(min = 2, max = 20, message = "must be between 2 and 20 characters")
    String firstName;

    @NotBlank(message = "is required")
    @Size(min = 2, max = 20, message = "must be between 2 and 20 characters")
    String lastName;
}
