package datamodels.repositories;

import datamodels.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestCountRepository extends CrudRepository<Product, Long> {
}
