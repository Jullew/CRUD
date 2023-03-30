package pl.aeh_project.auction_system.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")

/* Klasa reprezentująca produkt */
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long productId;
    private Long userId;
    private String title;
    private String description;
    private BigDecimal price;
    private Long customerId;
    private LocalDate endDate;
}
