package thirdwheel.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thirdwheel.product.dto.ProductRequest;
import thirdwheel.product.model.Product;
import thirdwheel.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();

        productRepository.save(product);
        //log.info("Created product: {}", product.toString());
    }

    public void getAllProducts() {
        List<Product> products = productRepository.findAll();
        //log.info("Found {} products", products.size());
    }

    public void getProductById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            //log.info("Found product: {}", product.toString());
        }
        catch (Exception e) {
            //log.error("Could not find product with id: {}", id);
        }
    }
}