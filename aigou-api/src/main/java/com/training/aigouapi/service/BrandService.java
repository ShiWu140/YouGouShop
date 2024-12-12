package com.training.aigouapi.service;

import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.PageEntity;

import java.util.List;

/**
 * 品牌表服务接口，提供品牌相关的业务操作。
 *
 * @author ethan
 */
public interface BrandService {

    /**
     * 查询品牌分页信息。
     *
     * @param current  当前页码，从1开始
     * @param pageSize 每页显示的品牌数量
     * @return 包含品牌数据的分页对象
     */
    PageEntity<Brand> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有品牌。
     *
     * @return 所有品牌的列表
     */
    List<Brand> findAll();

    /**
     * 根据品牌ID查询品牌。
     *
     * @param id 品牌ID
     * @return 对应ID的品牌对象，如果不存在则返回null
     */
    Brand findId(String id);

    /**
     * 插入新的品牌。
     *
     * @param brand 需要插入的品牌对象
     * @return 插入成功返回true，否则返回false
     */
    boolean save(Brand brand);

    /**
     * 根据品牌ID删除品牌。
     *
     * @param id 品牌ID
     * @return 删除成功返回true，否则返回false
     */
    boolean remove(String id);

    /**
     * 更新品牌信息。
     *
     * @param brand 需要更新的品牌对象
     * @return 更新成功返回true，否则返回false
     */
    boolean update(Brand brand);
}
