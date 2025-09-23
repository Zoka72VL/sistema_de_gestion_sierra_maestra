package com.sierra.maestra.model;
import jakarta.persistence.*;
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private int stock;
    // getters y setters
}