package geektime.spring.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderCoffeeRel implements Serializable {

    private static final long serialVersionUID = -6681750000854826892L;

    private Long id;

    private Long coffeeOrderId;

    private Long itemsId;

}
