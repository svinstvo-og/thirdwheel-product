package nakup.product.controller;

import nakup.product.dto.CategoryCreateRequest;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import nakup.product.repository.ProductRepository;
import nakup.product.service.CategoryService;
import nakup.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
