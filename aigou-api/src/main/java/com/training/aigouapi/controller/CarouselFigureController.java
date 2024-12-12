package com.training.aigouapi.controller;

import com.training.aigouapi.entity.ApiResponse;
import com.training.aigouapi.entity.CarouselFigure;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.service.CarouselFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 轮播图控制器 BY 十五
 */
@RestController
@RequestMapping("/carouselFigure")
@CrossOrigin
public class CarouselFigureController {

    @Autowired
    private CarouselFigureService carouselFigureService;

    /**
     * 分页查询轮播图
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 包含分页轮播图的响应实体
     */
    @GetMapping("/{current}/{pageSize}")
    public ResponseEntity<PageEntity<CarouselFigure>> page(@PathVariable(value = "current") Integer current, @PathVariable(value = "pageSize") Integer pageSize) {
        PageEntity<CarouselFigure> carouselFigurePageEntity = carouselFigureService.findPage(current, pageSize);
        return ResponseEntity.ok(carouselFigurePageEntity);
    }

    /**
     * 根据ID查询轮播图
     *
     * @param id 轮播图ID
     * @return 查询到的轮播图或错误响应实体
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarouselFigure> findById(@PathVariable(value = "id") String id) {
        Optional<CarouselFigure> carouselFigure = Optional.ofNullable(carouselFigureService.findId(id));
        return carouselFigure.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * 查询所有轮播图
     *
     * @return 所有轮播图列表的响应实体
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<CarouselFigure>> findAll() {
        List<CarouselFigure> carouselFigures = carouselFigureService.findAll();
        return ResponseEntity.ok(carouselFigures);
    }

    /**
     * search 搜索
     */
    @GetMapping("/search")
    public ResponseEntity<List<CarouselFigure>> search(@RequestParam String keyword) {
//        List<User> users = userService.search(keyword);
//        return ResponseEntity.ok(users);
        return null;
    }

    /**
     * 添加轮播图
     *
     * @param carouselFigure 轮播图对象
     * @return 添加结果的响应实体
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> register(CarouselFigure carouselFigure) {
        if (carouselFigureService.findId(carouselFigure.getId()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 已经存在"));
        }
        boolean rs = carouselFigureService.save(carouselFigure);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "CarouselFigure 添加成功", carouselFigure.getId()));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 添加失败"));
        }
    }

    /**
     * 修改轮播图
     *
     * @param carouselFigure 轮播图对象
     * @return 更新结果的响应实体
     */
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> update(CarouselFigure carouselFigure) {
        boolean rs = carouselFigureService.update(carouselFigure);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "CarouselFigure 更新成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 更新失败"));
        }
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图ID
     * @return 删除结果的响应实体
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable(value = "id") String id) {
        boolean rs = carouselFigureService.remove(id);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "CarouselFigure 删除成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 删除失败"));
        }
    }
}
