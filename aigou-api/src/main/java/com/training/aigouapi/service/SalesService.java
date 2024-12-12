package com.training.aigouapi.service;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.Sales;

import java.util.List;

/**
 * 销售表业务逻辑接口
 *
 * @author ethan
 */
public interface SalesService {
    /**
     * 分页查询销售记录信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<Sales> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有销售记录
     *
     * @return 销售记录列表
     */
    List<Sales> findAll();

    /**
     * 根据销售记录ID查询销售记录
     *
     * @param salesId 销售记录ID
     * @return 销售记录对象
     */
    Sales findId(String salesId);

    /**
     * 插入新的销售记录
     *
     * @param sales 销售记录对象
     * @return 是否插入成功，true表示成功，false表示失败
     */
    boolean save(Sales sales);

    /**
     * 删除销售记录
     *
     * @param salesId 销售记录ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String salesId);

    /**
     * 更新销售记录信息
     *
     * @param sales 销售记录对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(Sales sales);
}
