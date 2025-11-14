package org.example.foodordersystem.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class Item {

    private UUID id;
    private String name;
    private String description;
    private double price;
}
