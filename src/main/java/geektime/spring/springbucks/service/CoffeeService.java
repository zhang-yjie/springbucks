package geektime.spring.springbucks.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import geektime.spring.springbucks.mapper.CoffeeMapper;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeCache;
import geektime.spring.springbucks.repository.redis.CoffeeCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "springbucks-coffee")
public class CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    /**
     * 根据名字查询咖啡信息列表
     *
     * @param name 咖啡名字
     * @return 咖啡列表
     */
    @Cacheable
    public List<Coffee> listCoffeeByName(String name){
        return coffeeMapper.selectCoffeesByName(StringUtils.hasLength(name) ? "%" + name + "%" : null);
    }

    /**
     * 分页查询
     *
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return 咖啡分页信息
     */
    @Cacheable
    public PageInfo<Coffee> pageCoffeeByName(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Coffee> coffees = coffeeMapper.selectCoffeesByName(null);
        return new PageInfo<>(coffees);
    }

    /**
     * 保存咖啡信息
     *
     * @param coffee 咖啡信息
     * @return 保存后的咖啡信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Coffee saveCoffee(Coffee coffee){
        if(coffee.getId() == null){
            coffeeMapper.insertCoffee(coffee);
        }else {
            coffeeMapper.updateCoffee(coffee);

        }
        Coffee result = coffeeMapper.findOneById(coffee.getId());

        // 存入缓存
        CoffeeCache coffeeCache = CoffeeCache.builder()
                .id(result.getId())
                .name(result.getName())
                .price(result.getPrice())
                .createTime(result.getCreateTime())
                .updateTime(result.getUpdateTime())
                .build();
        coffeeCacheRepository.save(coffeeCache);

        return result;
    }

    /**
     * 根据id删除
     *
     * @param id 咖啡id
     * @return true 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id){
        int result = coffeeMapper.deleteCoffeeById(id);
        return result > 0;
    }

    /**
     * 清空咖啡缓存
     */
    @CacheEvict()
    public void cleanCoffeeCache(){
    }
}
