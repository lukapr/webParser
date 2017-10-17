package application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestCountRepository extends CrudRepository<Product, Long> {
}
