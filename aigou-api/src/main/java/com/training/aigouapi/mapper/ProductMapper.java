package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ProductMapper {
    /**
     * 查询商品分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 商品列表
     */
    List<Product> selectLimit(int start, int limit);

    /**
     * 查询商品总数
     *
     * @return 商品总数
     */
    long selectCount();

    /**
     * 查询所有商品
     *
     * @return 商品列表
     */
    List<Product> selectAll();

    /**
     * 按id查询商品
     *
     * @param productId 商品ID
     * @return 商品对象，如果不存在则返回null
     */
    Product selectId(String productId);

    /**
     * 插入新商品
     *
     * @param product 商品对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(Product product);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String productId);

    /**
     * 更新商品
     *
     * @param product 商品对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(Product product);
}
