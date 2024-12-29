package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.dto.ProductDto;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.ProductType;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
@Slf4j
public class ProductController {
    @Resource
    private IProductService productService;

    /**
     * 分页
     * @param productDto
     * @return
     */
    @PostMapping("/pageH")
    public Page pageH(@RequestBody ProductDto productDto){
        Page page = productService.pageH(productDto);
        return page;
    }

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
    public Product getId(@PathVariable String id) {
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
