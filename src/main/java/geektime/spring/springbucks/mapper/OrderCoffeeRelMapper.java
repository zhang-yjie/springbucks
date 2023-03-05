package geektime.spring.springbucks.mapper;

import geektime.spring.springbucks.model.OrderCoffeeRel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderCoffeeRelMapper {

    /**
     * 插入关系数据
     *
     * @param orderCoffeeRel 关系数据
     * @return 受影响行数
     */
    @Insert("INSERT INTO t_order_coffee (coffee_order_id, items_id)"
            + " VALUES (#{coffeeOrderId}, #{itemsId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRel(OrderCoffeeRel orderCoffeeRel);

    /**
     * 根据订单id删除关系数据
     *
     * @param orderIds 订单id集合
     * @return 受影响行数
     */
    int deleteRelByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量插入关系数据, 详细实现见 orderCoffeeRelMapper.xml 文件
     *
     * @param orderCoffeeRelList 关系数据集合
     * @return 受影响行数
     */
    int insertBatch(List<OrderCoffeeRel> orderCoffeeRelList);
}
