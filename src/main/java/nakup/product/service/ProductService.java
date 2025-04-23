package nakup.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nakup.product.dto.ProductResponse;
import nakup.product.model.event.UnitPriceUpdateEvent;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nakup.product.dto.ProductRequest;
import nakup.product.model.Product;
import nakup.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .sellerId(productRequest.getSellerId())
                .category(categoryService.validateCategory(productRequest.getCategoryId()))
                .build();

        productRepository.save(product);
        log.info("Created product: {}", product.getName());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> response = new ArrayList<>();
        log.info("Found {} products", products.size());
        for (Product product : products) {
            response.add(new ProductResponse(product));
        }
        return response;
    }

    public void getProductById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            log.info("Found product: {}", product.toString());
        }
        catch (Exception e) {
            log.error("Could not find product with id: {}", id);
        }
    }

    public HashMap<Long, Double> getPrices(List<Long> productIds) {
        HashMap<Long, Double> prices = new HashMap<>();

        for (Long productId : productIds) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new RuntimeException("Could not find product with id: " + productId);
            }
            prices.put(productId, product.get().getPrice().doubleValue());
        }
        return prices;
    }

    public Product validateProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }
}