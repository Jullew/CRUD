package pl.aeh_project.auction_system.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long userId;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDate endDate;
}
