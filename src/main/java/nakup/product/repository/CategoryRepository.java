package nakup.product.repository;

import nakup.product.model.Category;
import nakup.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByParentId(Long parentId);

    Category findByName(String name);

    List<Product> findProductsByCategory(Category category);
}
