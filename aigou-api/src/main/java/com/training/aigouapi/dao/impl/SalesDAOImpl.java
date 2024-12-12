package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.SalesDAO;
import com.training.aigouapi.entity.Sales;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售表数据操作接口实现类
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/12 12:17
 */
@Repository
public class SalesDAOImpl implements SalesDAO {

    /**
     * 查询分页方法
     *
     * @param start 起始页码
     * @param limit 每页返回的记录数
     * @return 分页后的销售记录列表
     */
    @Override
    public List<Sales> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(Sales.class, "select * from s_sales limit ?, ?", start, limit);
    }

    /**
     * 查询销售记录总数
     *
     * @return 销售记录的总数
     */
    @Override
    public long selectCount() {
        return JDBCUtils.executeCount("select count(*) from s_sales");
    }

    /**
     * 查询所有销售记录
     *
     * @return 所有销售记录的列表
     */
    @Override
    public List<Sales> selectAll() {
        return JDBCUtils.executeQuery(Sales.class, "select * from s_sales");
    }

    /**
     * 根据 ID 查询销售记录
     *
     * @param id 销售记录的唯一标识
     * @return 对应的销售记录，如果不存在则返回 null
     */
    @Override
    public Sales selectId(String id) {
        List<Sales> salesList = JDBCUtils.executeQuery(Sales.class, "select * from s_sales where id =?", id);
        if (salesList.isEmpty()) {
            return null;
        }
        return salesList.get(0);
    }

    /**
     * 插入新的销售记录
     *
     * @param sales 要插入的销售记录对象
     * @return 插入是否成功
     */
    @Override
    public boolean insert(Sales sales) {
        int rows = JDBCUtils.executeUpdate("insert into s_sales(id, product_id, sales_num) values(?,?,?)",
                sales.getId(), sales.getProductId(), sales.getSalesNum());
        return rows > 0;
    }

    /**
     * 删除指定 ID 的销售记录
     *
     * @param id 要删除的销售记录的唯一标识
     * @return 删除是否成功
     */
    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("delete from s_sales where id =?", id);
        return rows > 0;
    }

    /**
     * 更新销售记录
     *
     * @param sales 要更新的销售记录对象
     * @return 更新是否成功
     */
    @Override
    public boolean update(Sales sales) {
        int rows = JDBCUtils.executeUpdate("update s_sales set product_id=?, sales_num=? where id =?",
                sales.getProductId(), sales.getSalesNum(), sales.getId());
        return rows > 0;
    }
}
