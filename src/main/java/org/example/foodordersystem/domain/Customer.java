package org.example.foodordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class Customer {

    private String firstName;
    private String lastName;
}
