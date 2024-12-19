package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.ProductType;
import com.training.aigoushopapi.service.IProductTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品类型
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/productType")
public class ProductTypeController {
    @Resource
    private IProductTypeService productTypeService;

    /**
     * 分页查询商品类型信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含商品类型数据的分页对象
     */
    @GetMapping("/page")
    public Page<ProductType> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ProductType> page = new Page<>(current, size);
        return productTypeService.page(page);
    }

    /**
     * 查询所有商品类型
     *
     * @return 所有商品类型的列表
     */
    @GetMapping("/all")
    public List<ProductType> getAll() {
        return productTypeService.list();
    }

    /**
     * 按ID查询商品类型信息
     *
     * @param id 商品类型ID
     * @return 商品类型对象或错误信息
     */
    @GetMapping("/{id}")
    public ProductType getId(@PathVariable Long id) {
        return productTypeService.getById(id);
    }

    /**
     * 添加商品类型
     *
     * @param productType 商品类型对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody ProductType productType) {
        return productTypeService.save(productType);
    }

    /**
     * 更新商品类型信息
     *
     * @param productType 商品类型对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody ProductType productType) {
        return productTypeService.updateById(productType);
    }

    /**
     * 删除商品类型
     *
     * @param productType 商品类型对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody ProductType productType) {
        return productTypeService.removeById(productType.getId());
    }
}
