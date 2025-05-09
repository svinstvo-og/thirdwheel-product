package nakup.product.controller;

import nakup.product.dto.*;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import nakup.product.repository.ProductRepository;
import nakup.product.service.CategoryService;
import nakup.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/")
    public void createCategory(@RequestBody CategoryCreateRequest request) {
        Category category;

        if (request.getParentId() != null) {
            category = categoryService.validateCategory(request.getParentId());
        }
        else {
            category = null;
        }

        categoryService.createCategory(request, category);
    }

    @GetMapping("/")
    public CategoryShortResponse getCategory(@RequestBody CategoryGetRequest request) {
        Category category = categoryService.validateCategory(request.getCategoryId());
        return new CategoryShortResponse(category, categoryService);
    }

    @GetMapping("/extended")
    public CategoryExtendedResponse getExtendedCategory(@RequestBody CategoryGetRequest request) {
        Category category = categoryService.validateCategory(request.getCategoryId());
        return categoryService.getExtendedCategory(category);
    }

    @GetMapping("/hierarchy")
    public List<CategoryHierarchyRequest> getCategoryHierarchy(@RequestBody CategoryGetRequest request) {
        if (request.getCategoryId() != null) {
            Category category = categoryService.validateCategory(request.getCategoryId());
            return categoryService.getHierarchy(category);
        }
        else {
            return categoryService.getHierarchy(null);
        }
    }
}
