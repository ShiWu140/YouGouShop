package com.training.aigouapi.service.impl;

import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.Sales;
import com.training.aigouapi.mapper.SalesMapper;
import com.training.aigouapi.service.SalesService;
import com.training.aigouapi.util.IDUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 销售信息相关业务逻辑实现类
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/12 12:26
 */
@Service
public class SalesServiceImpl implements SalesService {
    /**
     * 调用 dao 层对象
     */
    @Resource
    private SalesMapper salesMapper;

    /**
     * 查询分页方法
     *
     * @param current  当前页码
     * @param pageSize 每页大小
     * @return 分页后的销售记录
     */
    @Override
    public PageEntity<Sales> findPage(Integer current, Integer pageSize) {
        // 分页查询数据
        List<Sales> sales = salesMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<Sales> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(salesMapper.selectCount());
        pageEntity.setRecords(sales);
        return pageEntity;
    }

    /**
     * 查询所有销售记录
     *
     * @return 所有销售记录的列表
     */
    @Override
    public List<Sales> findAll() {
        return salesMapper.selectAll();
    }

    /**
     * 根据销售记录 id 查询销售记录
     *
     * @param salesId 销售记录 id
     * @return 对应的销售记录，如果不存在则返回 null
     */
    @Override
    public Sales findId(String salesId) {
        return salesMapper.selectId(salesId);
    }

    /**
     * 添加销售记录
     *
     * @param sales 要添加的销售记录
     * @return 是否添加成功，true 表示成功，false 表示失败
     */
    @Override
    public boolean save(Sales sales) {
        sales.setId(IDUtils.get());
        return salesMapper.insert(sales);
    }

    /**
     * 删除销售记录
     *
     * @param salesId 要删除的销售记录 id
     * @return 是否删除成功，true 表示成功，false 表示失败
     */
    @Override
    public boolean remove(String salesId) {
        return salesMapper.delete(salesId);
    }

    /**
     * 更新销售记录
     *
     * @param sales 要更新的销售记录
     * @return 是否更新成功，true 表示成功，false 表示失败
     */
    @Override
    public boolean update(Sales sales) {
        return salesMapper.update(sales);
    }
}
