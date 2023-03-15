package pl.projektaeh.systemaukcji;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private Long idCustomer;
    private String title;
    private String desc;
    private double price;
    private Long userId;
    private LocalDate dateEnd;
}
