package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.ShopCartDAO;
import com.training.aigouapi.entity.ShopCart;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车数据接口实现类
 */
@Repository
public class ShopCartDAOlmpl implements ShopCartDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<ShopCart> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(ShopCart.class, "select * from s_shop_cart limit ?, ?", start, limit);
    }

    /**
     * 查询总数
     *
     * @return count
     */
    @Override
    public long selectCount() {
        Integer count = JDBCUtils.executeCount("select count(*) from s_shop_cart");
        return count;
    }

    /**
     * 查询所有购物车记录
     *
     * @return shopCarts 购物车记录列表
     */
    @Override
    public List<ShopCart> selectAll() {
        return JDBCUtils.executeQuery(ShopCart.class, "select * from s_shop_cart");
    }

    /**
     * 根据购物车id查询购物车记录
     *
     * @param id 购物车记录id
     * @return shopCart 购物车记录
     */
    @Override
    public ShopCart selectId(String id) {
        List<ShopCart> shopCarts = JDBCUtils.executeQuery(ShopCart.class, "select * from s_shop_cart where id =?", id);
        if (shopCarts.isEmpty()) {
            return null;
        }
        return shopCarts.get(0);
    }

    /**
     * 插入新的购物车记录
     *
     * @param shopCart 新的购物车记录
     * @return boolean 是否插入成功
     */
    @Override
    public boolean insert(ShopCart shopCart) {
        int rows = JDBCUtils.executeUpdate("insert into s_shop_cart(id, cart_id, user_id) values(?, ?, ?)",
                shopCart.getId(),
                shopCart.getCartId(),
                shopCart.getUserId());
        return rows > 0;
    }

    /**
     * 删除购物车记录
     *
     * @param id 购物车记录id
     * @return boolean 是否删除成功
     */
    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("delete from s_shop_cart where id =?", id);
        return rows > 0;
    }

    /**
     * 更新购物车记录
     *
     * @param shopCart 需要更新的购物车记录
     * @return boolean 是否更新成功
     */
    @Override
    public boolean update(ShopCart shopCart) {
        int rows = JDBCUtils.executeUpdate("update s_shop_cart set cart_id=?, user_id=? where id =?",
                shopCart.getCartId(),
                shopCart.getUserId(),
                shopCart.getId());
        return rows > 0;
    }
}
