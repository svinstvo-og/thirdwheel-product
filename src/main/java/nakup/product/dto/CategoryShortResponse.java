package nakup.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nakup.product.model.Category;
import nakup.product.model.Product;
import nakup.product.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryShortResponse {
    private Long id;
    private String name;
    private String description;
    private List<Product> products;

    private int parentCategoriesNumber;
    private int childrenCategoriesNumber;

    public CategoryShortResponse(Category category, CategoryService categoryService) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.products = category.getProducts();
        this.parentCategoriesNumber = (categoryService.getParentCategories(category, new ArrayList<>())).toArray().length;
        //this.childrenCategoriesNumber = (categoryService.getHierachy(category)).toArray().length;
    }
}
