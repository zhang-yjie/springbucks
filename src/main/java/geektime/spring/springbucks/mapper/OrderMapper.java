package geektime.spring.springbucks.mapper;

import geektime.spring.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入一条订单信息
     *
     * @param coffeeOrder 订单信息
     * @return 受影响行数
     */
    @Insert("INSERT INTO t_order (customer, state, create_time, update_time)"
            + " VALUES (#{customer}, #{state}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCoffeeOrder(CoffeeOrder coffeeOrder);

    /**
     * 根据id查询单条订单信息
     * 由于子集合 items, 不使用 foreach 的情况下(XML 文档结构必须从头至尾包含在同一个实体内, 不能即存在于 java 文件又存在于 xml 文件),
     * 注解的方法无法批量查询订单集合信息,
     * 批量查询见 findOredersByIds()
     *
     * @param id 订单id
     * @return 订单信息
     */
    @Select("SELECT * FROM t_order WHERE id = #{id}")
    @Results(id = "findOneById", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "items", column = "id",
                    many = @Many(select = "geektime.spring.springbucks.mapper.CoffeeMapper.selectCoffeesByOrderId", fetchType = FetchType.DEFAULT))
    })
    CoffeeOrder findOneById(@Param("id") Long id);

    /**
     * 根据id查询订单信息
     * 实现方法见 orderMapper.xml 文件
     *
     * @param ids 查询id
     * @return CoffeeOrder
     */
    List<CoffeeOrder> findOrdersByIds(@Param("ids") List<Long> ids);

    /**
     * 根据订单id更新订单状态
     *
     * @param id 订单id
     * @param state 订单状态
     * @return 受影响行数
     */
    @Update("UPDATE t_order SET state = #{state} WHERE id = #{id}")
    int updateStatusById(@Param("id") Long id, @Param("state") int state);

    /**
     * 更新订单信息
     *
     * @param coffeeOrder 订单信息
     * @return 受影响行数
     */
    @Update("UPDATE t_order SET customer = #{customer}, state = #{state}, update_time = now()"
            + "WHERE ID = #{id}")
    int updateCoffeeOrder(CoffeeOrder coffeeOrder);

    /**
     * 根据id删除订单信息
     *
     * @param ids 订单id
     * @return 受影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);

}
