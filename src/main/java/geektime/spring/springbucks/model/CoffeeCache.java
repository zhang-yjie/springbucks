package geektime.spring.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "springbucks-coffee", timeToLive = 60)
public class CoffeeCache {
    @Id
    private Long id;

    @Indexed
    private String name;

    @Indexed
    private Money price;

    @Indexed
    private Date createTime;

    @Indexed
    private Date updateTime;
}
