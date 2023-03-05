package geektime.spring.springbucks.handler;

import geektime.spring.springbucks.model.OrderState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StateTypeHandler extends BaseTypeHandler<OrderState> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OrderState state, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, state.getState());
    }

    @Override
    public OrderState getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return findSate(resultSet.getInt(s));
    }

    @Override
    public OrderState getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return findSate(resultSet.getInt(i));
    }

    @Override
    public OrderState getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return findSate(callableStatement.getInt(i));
    }

    private OrderState findSate(int value){
        return Arrays.stream(OrderState.values())
                .filter(state -> state.getState() == value)
                .collect(Collectors.toList()).get(0);

    }
}
