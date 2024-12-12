package com.training.aigouapi.service.impl;

import com.training.aigouapi.dao.BrandDAO;
import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.service.BrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ethan
 * @version 1.0
 * @since 2024/12/11 21:09
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandDAO brandDAO;

    @Override
    public PageEntity<Brand> findPage(Integer current, Integer pageSize) {
        //分页查询品牌数据
        List<Brand> brands = brandDAO.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<Brand> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(brandDAO.selectCount());
        pageEntity.setRecords(brands);
        return pageEntity;
    }

    @Override
    public List<Brand> findAll() {
        return brandDAO.selectAll();
    }

    @Override
    public Brand findId(String id) {
        return brandDAO.selectId(id);
    }

    @Override
    public boolean save(Brand brand) {
        return brandDAO.insert(brand);
    }

    @Override
    public boolean remove(String id) {
        return brandDAO.delete(id);
    }

    @Override
    public boolean update(Brand brand) {
        return brandDAO.update(brand);
    }
}
