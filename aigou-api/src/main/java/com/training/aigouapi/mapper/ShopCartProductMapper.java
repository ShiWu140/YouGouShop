package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.ShopCartProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车商品表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ShopCartProductMapper {
    /**
     * 查询购物车商品分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 购物车商品列表
     */
    List<ShopCartProduct> selectLimit(int start, int limit);

    /**
     * 查询购物车商品总数
     *
     * @return 购物车商品总数
     */
    long selectCount();

    /**
     * 查询所有购物车商品信息
     *
     * @return 购物车商品列表
     */
    List<ShopCartProduct> selectAll();

    /**
     * 按id查询购物车商品信息
     *
     * @param id 购物车商品ID
     * @return 购物车商品对象，如果不存在则返回null
     */
    ShopCartProduct selectId(String id);

    /**
     * 插入新的购物车商品信息
     *
     * @param shopCartProduct 购物车商品对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(ShopCartProduct shopCartProduct);

    /**
     * 更新购物车商品信息
     *
     * @param shopCartProduct 购物车商品对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(ShopCartProduct shopCartProduct);

    /**
     * 删除购物车商品信息
     *
     * @param id 购物车商品ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);
}
