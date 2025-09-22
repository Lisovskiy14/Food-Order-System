package org.example.foodordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

    private String name;
    private double price;
}
