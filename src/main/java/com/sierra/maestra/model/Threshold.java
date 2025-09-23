package com.sierra.maestra.model;
import jakarta.persistence.*;
@Entity
public class Threshold {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productType;
    private Integer minValue;
    // getters y setters
}