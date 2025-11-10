package org.example.foodordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private UUID id;
    private String name;
    private String description;
    private double price;
}
