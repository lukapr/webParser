package springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends CrudRepository<Config, Long> {
    public List<Config> findAllByOrderByLastupdatedDesc();
}
