package geektime.spring.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "springbucks-order", timeToLive = 60)
public class OrderCache {

    @Id
    private Long id;

    @Indexed
    private String customer;

    @Indexed
    private List<Coffee> items;

    @Indexed
    private OrderState state;

    @Indexed
    private Date createTime;

    @Indexed
    private Date updateTime;

}
