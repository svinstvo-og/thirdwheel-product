package thirdwheel.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import thirdwheel.product.dto.ProductRequest;
import thirdwheel.product.model.Product;
import thirdwheel.product.repository.ProductRepository;
import thirdwheel.product.service.ProductService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        if (productRepository.findByName(productRequest.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
        }
        productService.createProduct(productRequest);
    }

    @GetMapping("/{name}")
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
}