package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.Brand;
import com.training.aigoushopapi.service.IBrandService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌
 *
 * @author 十五
 */
@RestController
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
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Brand> page = new Page<>(current, size);
        return Result.success(brandService.page(page));
    }

    /**
     * 查询所有品牌
     *
     * @return 所有品牌的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(brandService.list());
    }

    /**
     * 按ID查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        Brand brand = brandService.getById(id);
        if (brand != null) {
            return Result.success(brand);
        } else {
            return Result.error("没有找到ID为 " + id + " 的品牌！");
        }
    }

    /**
     * 添加品牌
     *
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        boolean rs = brandService.save(brand);
        if (rs) {
            return Result.success(brand);
        } else {
            return Result.error("添加品牌失败！");
        }
    }

    /**
     * 更新品牌信息
     *
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody Brand brand) {
        boolean rs = brandService.updateById(brand);
        if (rs) {
            return Result.success(brand);
        } else {
            return Result.error("编辑ID为 " + brand.getId() + " 的品牌失败！");
        }
    }

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = brandService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的品牌成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的品牌失败！");
        }
    }
}
