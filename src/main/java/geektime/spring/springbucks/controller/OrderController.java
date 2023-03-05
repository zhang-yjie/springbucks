package geektime.spring.springbucks.controller;

import com.github.pagehelper.PageInfo;
import geektime.spring.springbucks.model.CoffeeOrder;
import geektime.spring.springbucks.service.CoffeeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    /**
     * 新增或保存订单
     * @RequestBody 用于解析json格式的数据
     *
     * @param coffeeOrder 订单信息
     * @return 保存后的订单信息
     * @throws Exception 保存失败抛出的异常
     */
    @PostMapping()
    public CoffeeOrder save(@RequestBody CoffeeOrder coffeeOrder) throws Exception {
        return coffeeOrderService.save(coffeeOrder);
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param ids 订单id
     * @return 订单信息列表
     */
    @GetMapping("/ids/{ids}")
    public List<CoffeeOrder> listOrders(@PathVariable("ids") String ids) {
        return coffeeOrderService.listOrdersByIds(ids);
    }

    /**
     * 根据id删除订单
     *
     * @param ids 订单id
     * @return true 删除成功
     */
    @DeleteMapping("/ids/{ids}")
    public boolean removeOrdersByIds(@PathVariable("ids") String ids) throws Exception {
        return coffeeOrderService.removeOrdersByIds(ids);
    }

    /**
     * 分页查询
     *
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return 订单信息
     */
    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public PageInfo<CoffeeOrder> pageOrdersByConditions(@PathVariable("pageNum") int pageNum,
                                                        @PathVariable("pageSize") int pageSize) {
        return coffeeOrderService.pageOrders(pageNum, pageSize);
    }

    /**
     * 修改订单状态
     *
     * @param id 订单id
     * @param state 订单状态
     * @return true 修改成功
     */
    @PutMapping("/id/{id}/state/{state}")
    public boolean updateState(@PathVariable("id") Long id,
                               @PathVariable("state") String state){
        return coffeeOrderService.updateState(id, state);
    }

    /**
     * 情况订单缓存信息
     *
     * @return true 清空成功
     */
    @DeleteMapping("/cache")
    public boolean cleanOrderCache(){
        coffeeOrderService.cleanOrderCache();
        return true;
    }
}
