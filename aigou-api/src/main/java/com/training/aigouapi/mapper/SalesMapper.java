package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.Sales;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 销售表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface SalesMapper {
    /**
     * 查询销售记录分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 销售记录列表
     */
    List<Sales> selectLimit(int start, int limit);

    /**
     * 查询销售记录总数
     *
     * @return 销售记录总数
     */
    long selectCount();

    /**
     * 查询所有销售记录
     *
     * @return 销售记录列表
     */
    List<Sales> selectAll();

    /**
     * 按id查询销售记录
     *
     * @param id 销售记录ID
     * @return 销售记录对象，如果不存在则返回null
     */
    Sales selectId(String id);

    /**
     * 插入新的销售记录
     *
     * @param sales 销售记录对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(Sales sales);

    /**
     * 删除销售记录
     *
     * @param id 销售记录ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);

    /**
     * 更新销售记录
     *
     * @param sales 销售记录对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(Sales sales);
}
