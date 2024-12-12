package com.training.aigouapi.dao;

import com.training.aigouapi.entity.Brand;

import java.util.List;

/**
 * 品牌表表数据操作接口
 *
 * @author ethan
 */
public interface BrandDAO {
    /**
     * 查询品牌分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 品牌列表
     */
    List<Brand> selectLimit(int start, int limit);

    /**
     * 查询品牌总数
     *
     * @return 品牌总数
     */
    long selectCount();

    /**
     * 查询所有品牌信息
     *
     * @return 品牌列表
     */
    List<Brand> selectAll();

    /**
     * 根据id查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌对象，如果不存在则返回null
     */
    Brand selectId(String id);

    /**
     * 插入品牌信息
     *
     * @param brand 品牌对象
     * @return 插入是否成功
     */
    boolean insert(Brand brand);

    /**
     * 根据id删除品牌信息
     *
     * @param id 品牌ID
     * @return 删除是否成功
     */
    boolean delete(String id);

    /**
     * 根据id更新品牌信息
     *
     * @param brand 品牌对象
     * @return 更新是否成功
     */
    boolean update(Brand brand);

}
