package geektime.spring.springbucks.controller;

import com.github.pagehelper.PageInfo;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    /**
     * 保存咖啡信息, json格式的数据,
     * 如果是表单提交数据, 则可以使用 @ModelAttribute 或不加注解
     *
     * @param coffee 咖啡信息
     * @return 保存后的咖啡信息
     */
    @PostMapping()
    public Coffee save(@RequestBody Coffee coffee) {
        return coffeeService.saveCoffee(coffee);
    }

    /**
     * 根据名字查询咖啡信息列表
     *
     * @param name 咖啡名字
     * @return 咖啡列表
     */
    @GetMapping("/name/{name}")
    public List<Coffee> listCoffees(@PathVariable("name") String name) {
        return coffeeService.listCoffeeByName(name);
    }

    /**
     * 根据id删除咖啡
     *
     * @param id 咖啡id
     * @return true 删除成功
     */
    @DeleteMapping("/id/{id}")
    public boolean removeCoffeeById(@PathVariable("id") Long id) {
        return coffeeService.removeById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return 咖啡分页信息
     */
    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public PageInfo<Coffee> pageCoffeesByName(@PathVariable("pageNum") int pageNum,
                                              @PathVariable("pageSize") int pageSize) {
        return coffeeService.pageCoffeeByName(pageNum, pageSize);
    }

    /**
     * 清空咖啡缓存
     *
     * @return 清空成功
     */
    @DeleteMapping("/cache")
    public boolean cleanCoffeeCache(){
        coffeeService.cleanCoffeeCache();
        return true;
    }
}
