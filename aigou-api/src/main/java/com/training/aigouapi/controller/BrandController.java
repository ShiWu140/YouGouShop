package com.training.aigouapi.controller;

import com.training.aigouapi.common.Result;
import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Resource
    private BrandService brandService;

    /**
     * 分页查询品牌信息
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 包含品牌数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer page, @RequestParam Integer size) {
        return Result.success(brandService.findPage(page, size));
    }

    /**
     * 查询所有品牌
     *
     * @return 所有品牌的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(brandService.findAll());
    }

    /**
     * 按ID查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable String id) {
        Brand brand = brandService.findId(id);
        if (brand != null) {
            return Result.success(brand);
        } else {
            return Result.error("没有找到{" + id + "}！");
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
            return Result.error("添加{" + brand.getId() + "}失败！");
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
        boolean rs = brandService.update(brand);
        if (rs) {
            return Result.success(brand);
        } else {
            return Result.error("编辑{" + brand.getId() + "}失败！");
        }
    }

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        boolean rs = brandService.remove(id);
        if (rs) {
            return Result.success("删除{" + id + "}成功！");
        } else {
            return Result.error("删除{" + id + "}失败！");
        }
    }
}
