package geektime.spring.springbucks.repository.redis;

import geektime.spring.springbucks.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {
}
