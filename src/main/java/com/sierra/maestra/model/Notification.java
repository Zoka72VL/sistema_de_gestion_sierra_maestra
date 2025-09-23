package com.sierra.maestra.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    @ManyToOne private Product product;
    // getters y setters
}