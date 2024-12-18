package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 商品表
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
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
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Product> page = new Page<>(current, size);
        return Result.success(productService.page(page));
    }

    /**
     * 查询所有商品
     *
     * @return 所有商品的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(productService.list());
    }

    /**
     * 按ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product != null) {
            return Result.success(product);
        } else {
            return Result.error("没有找到ID为 " + id + " 的商品！");
        }
    }

    /**
     * 添加商品
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody Product product) {
        boolean rs = productService.save(product);
        if (rs) {
            return Result.success(product);
        } else {
            return Result.error("添加商品失败！");
        }
    }

    /**
     * 更新商品信息
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody Product product) {
        boolean rs = productService.updateById(product);
        if (rs) {
            return Result.success(product);
        } else {
            return Result.error("编辑ID为 " + product.getId() + " 的商品失败！");
        }
    }

    /**
     * 删除商品
     *
     * @param id 商品ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = productService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的商品成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的商品失败！");
        }
    }
}
