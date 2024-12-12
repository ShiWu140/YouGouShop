package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.BrandDAO;
import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ethan
 * @version 1.0
 * @since 2024/12/11 20:53
 */
@Repository
public class BrandDAOImpl implements BrandDAO {
    @Override
    public List<Brand> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(Brand.class, "select * from s_brand limit ?, ?", start, limit);
    }

    @Override
    public long selectCount() {
        return JDBCUtils.executeCount("select count(*) from s_brand");
    }

    @Override
    public List<Brand> selectAll() {
        return JDBCUtils.executeQuery(Brand.class, "select * from s_brand");
    }

    @Override
    public Brand selectId(String id) {
        List<Brand> brands = JDBCUtils.executeQuery(Brand.class, "select * from s_brand where id = ?", id);
        if (brands.isEmpty()) {
            return null;
        }
        return brands.get(0);
    }

    @Override
    public boolean insert(Brand brand) {
        int rows = JDBCUtils.executeUpdate("insert into s_brand(id,brand_name," +
                        "brand_type," +
                        "brand_img) " +
                        "values(?,?,?,?)",
                brand.getId(),
                brand.getBrandName(),
                brand.getBrandType(),
                brand.getBrandImg());
        // 行数大于0，返回true，反之
        return rows > 0;
    }

    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("delete from s_brand where id = ?", id);
        return rows > 0;
    }

    @Override
    public boolean update(Brand brand) {
        int rows = JDBCUtils.executeUpdate("update s_brand set " +
                        "brand_name=?," +
                        "brand_type=?," +
                        "brand_img=? " +
                        "where id =?",
                brand.getBrandName(),
                brand.getBrandType(),
                brand.getBrandImg(),
                brand.getId());
        return rows > 0;
    }
}
