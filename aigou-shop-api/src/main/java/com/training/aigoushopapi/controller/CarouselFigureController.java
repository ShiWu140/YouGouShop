package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.CarouselFigure;
import com.training.aigoushopapi.service.ICarouselFigureService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/carouselFigure")
@ResponseResult
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
    @GetMapping("/page")
    public Page<CarouselFigure> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<CarouselFigure> page = new Page<>(current, size);
        return carouselFigureService.page(page);
    }

    /**
     * 查询所有轮播图
     *
     * @return 所有轮播图的列表
     */
    @GetMapping("/all")
    public List<CarouselFigure> getAll() {
        return carouselFigureService.list();
    }

    /**
     * 按ID查询轮播图信息
     *
     * @param id 轮播图ID
     * @return 轮播图对象或错误信息
     */
    @GetMapping("/{id}")
    public CarouselFigure getId(@PathVariable Long id) {
        return carouselFigureService.getById(id);
    }

    /**
     * 添加轮播图
     *
     * @param carouselFigure 轮播图对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody CarouselFigure carouselFigure) {
        return carouselFigureService.save(carouselFigure);
    }

    /**
     * 更新轮播图信息
     *
     * @param carouselFigure 轮播图对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody CarouselFigure carouselFigure) {
        return carouselFigureService.updateById(carouselFigure);
    }

    /**
     * 删除轮播图
     *
     * @param carouselFigure 轮播图对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody CarouselFigure carouselFigure) {
        return carouselFigureService.removeById(carouselFigure.getId());
    }
}
