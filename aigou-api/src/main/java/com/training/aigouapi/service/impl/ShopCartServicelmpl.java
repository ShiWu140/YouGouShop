package com.training.aigouapi.service.impl;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ShopCart;
import com.training.aigouapi.mapper.ShopCartMapper;
import com.training.aigouapi.service.ShopCartService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 购物车业务逻辑实现类
 */
@Service
public class ShopCartServicelmpl implements ShopCartService {

    /**
     * 调用dao层对象
     */
    @Resource
    private ShopCartMapper shopCartMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<ShopCart> findPage(Integer current, Integer pageSize) {
        //分页查询数据
        List<ShopCart> shopCarts = shopCartMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<ShopCart> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(shopCartMapper.selectCount());
        pageEntity.setRecords(shopCarts);
        return pageEntity;
    }

    /**
     * 查询所有购物车记录
     *
     * @return shopCarts 购物车记录列表
     */
    @Override
    public List<ShopCart> findAll() {
        return shopCartMapper.selectAll();
    }

    /**
     * 根据购物车id查询购物车记录
     *
     * @param cartId 购物车记录id
     * @return shopCart 购物车记录
     */
    @Override
    public ShopCart findId(String cartId) {
        return shopCartMapper.selectId(cartId);
    }

    /**
     * 添加新的购物车记录
     *
     * @param shopCart 新的购物车记录
     * @return 是否添加成功 true 成功 false 失败
     */
    @Override
    public boolean save(ShopCart shopCart) {
        return shopCartMapper.insert(shopCart);
    }

    /**
     * 删除购物车记录
     *
     * @param cartId 购物车记录id
     * @return 是否删除成功 true 成功 false 失败
     */
    @Override
    public boolean remove(String cartId) {
        return shopCartMapper.delete(cartId);
    }

    /**
     * 更新购物车记录
     *
     * @param shopCart 需要更新的购物车记录
     * @return 是否更新成功 true 成功 false 失败
     */
    @Override
    public boolean update(ShopCart shopCart) {
        return shopCartMapper.update(shopCart);
    }
}
