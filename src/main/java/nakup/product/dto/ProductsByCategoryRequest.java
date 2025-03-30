package nakup.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsByCategoryRequest {
    @JsonProperty("category-id")
    Long categoryId;
    @JsonProperty("category-name")
    String name;
}
