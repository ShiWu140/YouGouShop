package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品表
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    /**
     * 分页查询商品信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含商品数据的分页对象
     */
    @GetMapping("/page")
    public Page<Product> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Product> page = new Page<>(current, size);
        return productService.page(page);
    }

    /**
     * 查询所有商品
     *
     * @return 所有商品的列表
     */
    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.list();
    }

    /**
     * 按ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品对象或错误信息
     */
    @GetMapping("/{id}")
    public Product getId(@PathVariable Long id) {
        return productService.getById(id);
    }

    /**
     * 添加商品
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 更新商品信息
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody Product product) {
        return productService.updateById(product);
    }

    /**
     * 删除商品
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody Product product) {
        return productService.removeById(product.getId());
    }
}
