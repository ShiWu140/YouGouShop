package com.training.aigouapi.service.impl;

import com.training.aigouapi.mapper.OrderProductMapper;
import com.training.aigouapi.entity.OrderProduct;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.OrderProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Resource
    private OrderProductMapper orderProductMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<OrderProduct> findPage(Integer current, Integer pageSize) {
        //分页查询数据
        List<OrderProduct> orderProducts = orderProductMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<OrderProduct> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(orderProductMapper.selectCount());
        pageEntity.setRecords(orderProducts);
        return pageEntity;
    }

    @Override
    public List<OrderProduct> findAll() {
        return orderProductMapper.selectAll();
    }

    @Override
    public OrderProduct findId(String id) {
        return orderProductMapper.selectId(id);
    }

    @Override
    public boolean remove(String id) {
        return orderProductMapper.delete(id);
    }

    @Override
    public boolean update(OrderProduct orderProduct) {
        return orderProductMapper.update(orderProduct);
    }

    @Override
    public boolean save(OrderProduct orderProduct) {
        return orderProductMapper.insert(orderProduct);
    }
}