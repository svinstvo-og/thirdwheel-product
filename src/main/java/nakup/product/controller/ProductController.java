package nakup.product.controller;

import lombok.extern.slf4j.Slf4j;
import nakup.product.dto.ProductsByCategoryRequest;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import nakup.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import nakup.product.dto.ProductRequest;
import nakup.product.model.Product;
import nakup.product.repository.ProductRepository;
import nakup.product.service.ProductService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        if (productRepository.findByName(productRequest.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
        }
        productService.createProduct(productRequest);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product findProduct(@PathVariable String name) {
        if (productRepository.findByName(name) == null) {
            log.info("Product does not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");
        }
        return productRepository.findByName(name);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> findAllProducts() {
        if (productRepository.findAll().isEmpty()) {
            log.info("No products found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }
        return productRepository.findAll();
    }

    @GetMapping
    public List<Product> getProductsByCategory(@RequestBody ProductsByCategoryRequest request) {
        Category category;
        if (request.getCategoryId() == null) {
            category = categoryService.validateCategory(request.getName());
        }
        else {
            category = categoryService.validateCategory(request.getCategoryId());
        }

        return categoryRepository.findProductsByCategory(category);
    }
}