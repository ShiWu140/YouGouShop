package com.training.aigouapi.service.impl;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.ShopCartProduct;
import com.training.aigouapi.mapper.ShopCartProductMapper;
import com.training.aigouapi.service.ShopCartProductService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class ShopCartProductServicelmpl implements ShopCartProductService {
    @Resource
    private ShopCartProductMapper shopCartProductMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<ShopCartProduct> findPage(Integer current, Integer pageSize) {
        //分页查询数据
        List<ShopCartProduct> shopCartProducts = shopCartProductMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<ShopCartProduct> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(shopCartProductMapper.selectCount());
        pageEntity.setRecords(shopCartProducts);
        return pageEntity;
    }

    @Override
    public List<ShopCartProduct> findAll() {
        return shopCartProductMapper.selectAll();
    }

    @Override
    public ShopCartProduct findId(String id) {
        return shopCartProductMapper.selectId(id);
    }

    @Override
    public boolean remove(String id) {
        return shopCartProductMapper.delete(id);
    }

    @Override
    public boolean update(ShopCartProduct shopCartProduct) {
        return shopCartProductMapper.update(shopCartProduct);
    }

    @Override
    public boolean save(ShopCartProduct shopCartProduct) {
        return shopCartProductMapper.insert(shopCartProduct);
    }
}
