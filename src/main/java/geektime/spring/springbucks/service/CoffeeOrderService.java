package geektime.spring.springbucks.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import geektime.spring.springbucks.integration.Barista;
import geektime.spring.springbucks.mapper.CoffeeMapper;
import geektime.spring.springbucks.mapper.OrderCoffeeRelMapper;
import geektime.spring.springbucks.mapper.OrderMapper;
import geektime.spring.springbucks.model.*;
import geektime.spring.springbucks.repository.redis.OrderCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = "springbucks-order")
public class CoffeeOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderCoffeeRelMapper orderCoffeeRelMapper;

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private OrderCacheRepository orderCacheRepository;

    @Autowired
    private Barista barista;

    /**
     * 保存订单信息
     *
     * @param coffeeOrder 订单信息
     * @return 保存后的订单信息
     * @throws Exception 保存失败所抛出的异常
     */
    @Transactional(rollbackFor = Exception.class)
    public CoffeeOrder save(CoffeeOrder coffeeOrder) throws Exception {

        if(CollectionUtils.isEmpty(coffeeOrder.getItems())){
            throw new Exception("请添加咖啡!");
        }

        List<Long> coffeeIds = coffeeOrder.getItems().stream().map(Coffee::getId).collect(Collectors.toList());
        int coffeeList = coffeeMapper.selectCoffeesByIds(StringUtils.join(coffeeIds, ",")).size();
        if(coffeeList != coffeeOrder.getItems().size()){
            throw new Exception("选择的咖啡不存在!");
        }

        if (coffeeOrder.getId() == null) {
            // 新增订单
            coffeeOrder.setState(OrderState.INIT);
            orderMapper.insertCoffeeOrder(coffeeOrder);
        } else {
            // 更新订单
            orderMapper.updateCoffeeOrder(coffeeOrder);
        }

        List<OrderCoffeeRel> orderCoffeeRelList = coffeeIds.stream().map(itemId -> new OrderCoffeeRel(null, coffeeOrder.getId(), itemId)).collect(Collectors.toList());

        // 删除旧关联关系
        orderCoffeeRelMapper.deleteRelByOrderIds(Arrays.asList(coffeeOrder.getId()));

        // 新增关联关系
        orderCoffeeRelMapper.insertBatch(orderCoffeeRelList);

        // 查询更新结果
        CoffeeOrder result = orderMapper.findOneById(coffeeOrder.getId());

        // 添加到缓存
        OrderCache orderCache = OrderCache.builder()
                .id(result.getId())
                .customer(result.getCustomer())
                .state(result.getState())
                .items(result.getItems())
                .createTime(result.getCreateTime())
                .updateTime(result.getUpdateTime())
                .build();
        orderCacheRepository.save(orderCache);

        return result;
    }

    /**
     * 更新订单状态
     *
     * @param id 订单id
     * @param state 状态
     * @return true 更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateState(Long id, String state){
        OrderState orderState = OrderState.valueOf(state);
        int result = orderMapper.updateStatusById(id, orderState.getState());
        if(result <= 0){
            return false;
        }
        // 如果更新为已付款 则向MQ发送消息
        if(Objects.equals(OrderState.PAID.getKey(), state)){
            barista.newOrders().send(MessageBuilder.withPayload(id).build());
        }
        return true;
    }

    /**
     * 根据订单id批量删除订单
     *
     * @param ids 订单id
     * @return true 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOrdersByIds(String ids) throws Exception {
        String[] idArray = ids.split(",");
        List<Long> idList = Arrays.stream(idArray).map(id -> Long.valueOf(id)).collect(Collectors.toList());
        int delOrderLines = orderMapper.deleteByIds(idList);
        orderCoffeeRelMapper.deleteRelByOrderIds(idList);
        if(idList.size() != delOrderLines){
            throw new Exception("删除失败!");
        }
        return true;
    }

    /**
     * 根据id查询订单信息
     *
     * @param ids 订单id
     * @return 订单信息
     */
    @Cacheable
    public List<CoffeeOrder> listOrdersByIds(String ids){
        List<Long> orderIds = Arrays.stream(ids.split(",")).map(id -> Long.valueOf(id)).collect(Collectors.toList());
        return orderMapper.findOrdersByIds(orderIds);
    }

    /**
     * 分页查询
     *
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 订单信息
     */
    @Cacheable
    public PageInfo<CoffeeOrder> pageOrders(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(orderMapper.findOrdersByIds(null));
    }

    /**
     * 清空缓存
     */
    @CacheEvict()
    public void cleanOrderCache(){
    }
}
