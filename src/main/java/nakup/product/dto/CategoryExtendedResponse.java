package nakup.product.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nakup.product.model.Category;
import nakup.product.model.Product;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExtendedResponse {
    private Long id;
    private String name;
    private String description;
    private List<Product> products;
//    private List<Category> children;
//    private Category parent;

    private Long parentCategoryId;
    private List<CategoryExtendedResponse> childrenCategories;

    public CategoryExtendedResponse(Category category, Long parentCategoryId, List<CategoryExtendedResponse> childrenCategories) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.products = category.getProducts();
        this.parentCategoryId = parentCategoryId;
        this.childrenCategories = childrenCategories;
//        this.children = category.getChildren();
//        this.parent = category.getParent();
    }

    public CategoryExtendedResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        //this.products = category.getProducts();
    }
}
