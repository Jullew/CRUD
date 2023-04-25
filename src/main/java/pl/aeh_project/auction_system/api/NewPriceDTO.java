package pl.aeh_project.auction_system.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewPriceDTO {
    private String login;
    private String sessionKey;
    private Long productId;
    private BigDecimal newProductPrice;
}
