package nakup.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nakup.product.model.Category;
import nakup.product.model.Product;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHierarchyRequest {
    private Long id;
    private String name;
    private String description;
    private List<ProductResponse> products;

    private Long parentCategoryId;
    private List<CategoryHierarchyRequest> childrenCategories;

    public CategoryHierarchyRequest(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();

        this.products = new ArrayList<>();
        for (Product product : category.getProducts()) {
            this.products.add(new ProductResponse(product));
        }

        if (category.getParent() != null) {
            this.parentCategoryId = category.getParent().getId();
        }
        else {
            this.parentCategoryId = null;
        }

        this.childrenCategories = new ArrayList<>();

        for (Category child : category.getChildren()) {
            this.childrenCategories.add(new CategoryHierarchyRequest(child));
        }
    }
}
