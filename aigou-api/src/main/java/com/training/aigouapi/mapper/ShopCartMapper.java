package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.ShopCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ShopCartMapper {
    /**
     * 查询购物车分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 购物车记录列表
     */
    List<ShopCart> selectLimit(int start, int limit);

    /**
     * 查询购物车总数
     *
     * @return 购物车记录总数
     */
    long selectCount();

    /**
     * 查询所有购物车记录
     *
     * @return 购物车记录列表
     */
    List<ShopCart> selectAll();

    /**
     * 根据购物车id查询购物车记录
     *
     * @param id 购物车记录ID
     * @return 购物车记录对象，如果不存在则返回null
     */
    ShopCart selectId(String id);

    /**
     * 插入新的购物车记录
     *
     * @param shopCart 购物车记录对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(ShopCart shopCart);

    /**
     * 删除购物车记录
     *
     * @param id 购物车记录ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);

    /**
     * 更新购物车记录
     *
     * @param shopCart 购物车记录对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(ShopCart shopCart);
}
