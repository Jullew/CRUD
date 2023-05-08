package pl.aeh_project.auction_system.api.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BidProductDto {
    private String login;
    private String sessionKey;
    private Long productId;
    private BigDecimal newProductPrice;
}
