package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.CarouselFigure;
import com.training.aigoushopapi.service.ICarouselFigureService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/carouselFigure")
public class CarouselFigureController {
    @Resource
    private ICarouselFigureService carouselFigureService;

    /**
     * 分页查询轮播图信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含轮播图数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<CarouselFigure> page = new Page<>(current, size);
        return Result.success(carouselFigureService.page(page));
    }

    /**
     * 查询所有轮播图
     *
     * @return 所有轮播图的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(carouselFigureService.list());
    }

    /**
     * 按ID查询轮播图信息
     *
     * @param id 轮播图ID
     * @return 轮播图对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        CarouselFigure carouselFigure = carouselFigureService.getById(id);
        if (carouselFigure != null) {
            return Result.success(carouselFigure);
        } else {
            return Result.error("没有找到ID为 " + id + " 的轮播图！");
        }
    }

    /**
     * 添加轮播图
     *
     * @param carouselFigure 轮播图对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody CarouselFigure carouselFigure) {
        boolean rs = carouselFigureService.save(carouselFigure);
        if (rs) {
            return Result.success(carouselFigure);
        } else {
            return Result.error("添加轮播图失败！");
        }
    }

    /**
     * 更新轮播图信息
     *
     * @param carouselFigure 轮播图对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody CarouselFigure carouselFigure) {
        boolean rs = carouselFigureService.updateById(carouselFigure);
        if (rs) {
            return Result.success(carouselFigure);
        } else {
            return Result.error("编辑ID为 " + carouselFigure.getId() + " 的轮播图失败！");
        }
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = carouselFigureService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的轮播图成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的轮播图失败！");
        }
    }
}
