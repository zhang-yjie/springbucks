package geektime.spring.springbucks.repository.redis;

import geektime.spring.springbucks.model.OrderCache;
import org.springframework.data.repository.CrudRepository;

public interface OrderCacheRepository extends CrudRepository<OrderCache, Long> {
}
