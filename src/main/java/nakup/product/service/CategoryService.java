package nakup.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nakup.product.dto.CategoryCreateRequest;
import nakup.product.dto.CategoryExtendedResponse;
import nakup.product.model.Category;
import nakup.product.repository.CategoryRepository;
import nakup.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Category validateCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        }
        else {
            throw new NoSuchFieldError("No such category found");
        }
    }

    public Category validateCategory(String name) {
        if (categoryRepository.findByName(name) != null) {
            return categoryRepository.findByName(name);
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

    public CategoryExtendedResponse getExtendedCategory(Category category) {
        List<CategoryExtendedResponse> parentCategories = getParentCategories(category, new ArrayList<>());

        CategoryExtendedResponse extendedCategory = new CategoryExtendedResponse(category, parentCategories, getChildrenCategories(category));
        return extendedCategory;
    }

    public List<CategoryExtendedResponse> getParentCategories(Category category, List<CategoryExtendedResponse> parentCategories) {
        if (category.getParent() != null) {
            CategoryExtendedResponse parent = new CategoryExtendedResponse(category.getParent());
            parentCategories.add(parent);
            getParentCategories(category.getParent(), parentCategories);
        }
        return parentCategories;
    }

    public List<CategoryExtendedResponse> getChildrenCategories(Category category) {
        List<CategoryExtendedResponse> childrenCategories = new ArrayList<>();

        for (Category child : categoryRepository.findCategoriesByParentId(category.getId())) {
            childrenCategories.add(new CategoryExtendedResponse(child));
        }

        return childrenCategories;
    }
}
