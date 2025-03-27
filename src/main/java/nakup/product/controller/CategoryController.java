package nakup.product.controller;

import nakup.product.dto.CategoryCreateRequest;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import nakup.product.repository.ProductRepository;
import nakup.product.service.CategoryService;
import nakup.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createCategory(CategoryCreateRequest request) {
        Category category = categoryService.validateCategoty(request.getParentId());

        categoryService.createCategory(request, category);
    }

}
