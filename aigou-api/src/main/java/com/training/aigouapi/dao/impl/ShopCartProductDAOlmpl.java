package com.training.aigouapi.dao.impl;
import com.training.aigouapi.dao.ShopCartProductDAO;
import com.training.aigouapi.entity.ShopCartProduct;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ShopCartProductDAOlmpl implements ShopCartProductDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<ShopCartProduct> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(ShopCartProduct.class, "select * from s_shop_cart_product limit ?, ?", start, limit);
    }

    /**
     * 查询总数
     *
     * @return count
     */
    @Override
    public long selectCount() {
        Integer count = JDBCUtils.executeCount("select count(*) from s_shop_cart_product");
        return count;
    }

    @Override
    public List<ShopCartProduct> selectAll() {
        List<ShopCartProduct> list = JDBCUtils.executeQuery(ShopCartProduct.class, "SELECT * FROM `s_shop_cart_product`");
        return list;
    }

    @Override
    public ShopCartProduct selectId(String id) {
        List<ShopCartProduct> list = JDBCUtils.executeQuery(ShopCartProduct.class, "SELECT * FROM `s_shop_cart_product` WHERE `id` = ?", id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean insert(ShopCartProduct shopCartProduct) {
        int rows = JDBCUtils.executeUpdate("INSERT INTO `s_shop_cart_product` (`id`, `shop_cart_id`, `product_id`, `product_num`) VALUES (?, ?, ?, ?)",
                shopCartProduct.getId(), shopCartProduct.getShopCartId(), shopCartProduct.getProductId(), shopCartProduct.getProductNum());
        return rows > 0;
    }

    @Override
    public boolean update(ShopCartProduct shopCartProduct) {
        int rows = JDBCUtils.executeUpdate("UPDATE `s_shop_cart_product` SET `shop_cart_id` = ?, `product_id` = ?, `product_num` = ? WHERE `s_shop_cart_product`.`id` = ?",
                shopCartProduct.getShopCartId(), shopCartProduct.getProductId(), shopCartProduct.getProductNum(), shopCartProduct.getId());
        return rows > 0;
    }

    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("DELETE FROM `s_shop_cart_product` WHERE `id` = ?", id);
        return rows > 0;
    }
}
