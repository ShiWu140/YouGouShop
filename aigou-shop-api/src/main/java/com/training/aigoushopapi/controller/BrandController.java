package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Brand;
import com.training.aigoushopapi.service.IBrandService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌
 *
 * @author 十五
 */
@RestController
@ResponseResult
@RequestMapping("/brand")
public class BrandController {

    @Resource
    private IBrandService brandService;

    /**
     * 分页查询品牌信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含品牌数据的分页对象
     */
    @GetMapping("/page")
    public Page<Brand> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Brand> page = new Page<>(current, size);
        return brandService.page(page);
    }

    /**
     * 查询所有品牌
     *
     * @return 所有品牌的列表
     */
    @GetMapping("/all")
    public List<Brand> getAll() {
        return brandService.list();
    }

    /**
     * 按ID查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌对象或错误信息
     */
    @GetMapping("/{id}")
    public Brand getId(@PathVariable String id) {
        return brandService.getById(id);
    }

    /**
     * 添加品牌
     *
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    /**
     * 更新品牌信息
     *
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody Brand brand) {
        return brandService.updateById(brand);
    }

    /**
     * 删除品牌
     *
     * @param brand 品牌
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody Brand brand) {
        return brandService.removeById(brand.getId());
    }
}
