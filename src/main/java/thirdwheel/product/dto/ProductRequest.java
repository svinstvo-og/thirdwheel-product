package thirdwheel.product.dto;

import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}