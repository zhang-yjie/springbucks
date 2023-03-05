package geektime.spring.springbucks.mapper;

import geektime.spring.springbucks.model.Coffee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoffeeMapper {

    @Select("SELECT c.* FROM t_coffee c LEFT JOIN t_order_coffee rel ON c.ID = rel.ITEMS_ID"
            + " LEFT JOIN t_order o ON o.ID = rel.COFFEE_ORDER_ID WHERE o.ID = #{orderId}")
    @Result(id = true, column = "id", property = "id")
    List<Coffee> selectCoffeesByOrderId(@Param("orderId") Long orderId);

    @Insert("INSERT INTO t_coffee (NAME, PRICE, CREATE_TIME, UPDATE_TIME)"
            + " VALUES (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCoffee(Coffee coffee);

    @Select("SELECT * FROM t_coffee WHERE id IN ( #{ids} )")
    @Result(id = true, column = "id", property = "id")
    List<Coffee> selectCoffeesByIds(@Param("ids") String ids);

    @Select({
            "<script>",
            "SELECT * FROM t_coffee",
            "<if test='name != null'>",
            "WHERE name LIKE #{name} ",
            "</if>",
            "ORDER BY CREATE_TIME DESC",
            "</script>"
    })
    @Result(id = true, column = "id", property = "id")
    List<Coffee> selectCoffeesByName(@Param("name") String name);

    @Update("UPDATE t_coffee SET name = #{name}, price = #{price}, update_time = now() WHERE ID = #{id}")
    int updateCoffee(Coffee coffee);

    @Select("SELECT * FROM t_coffee WHERE id = #{id}")
    Coffee findOneById(@Param("id") Long id);

    @Delete("DELETE FROM t_coffee WHERE id = #{id}")
    int deleteCoffeeById(@Param("id") Long id);
}
