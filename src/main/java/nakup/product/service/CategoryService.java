package nakup.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nakup.product.dto.CategoryCreateRequest;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category validateCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        }
        else {
            throw new NoSuchFieldError("No such category found");
        }
    }

    public void createCategory(CategoryCreateRequest request, Category parent) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        category.setParent(parent);
        System.out.println("Added parent: " + parent);
        if (parent != null) {
            parent.getChildren().add(category);
            System.out.println("Added child to parent: " + parent.getName());
        }

        category.setProducts(new ArrayList<>());

        categoryRepository.save(category);
    }
}
