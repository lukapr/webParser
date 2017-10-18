package datamodels.repositories;

import datamodels.Category;
import datamodels.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findByArticle(String article);
    public List<Product> findByCategoriesContains(Category category);
}
