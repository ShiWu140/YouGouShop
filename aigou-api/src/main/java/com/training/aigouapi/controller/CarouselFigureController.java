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

@RestController
@RequestMapping("/carouselFigure")
@CrossOrigin
public class CarouselFigureController {

    @Autowired
    private CarouselFigureService carouselFigureService;

    /**
     * 分页查询用户信息
     *
     * @param current  当前页码
     * @param pagesize 每页显示数量
     * @return 包含分页用户信息的响应实体
     */
    @PostMapping("/page")
    public ResponseEntity<PageEntity<CarouselFigure>> page(@RequestParam Integer current, @RequestParam Integer pagesize) {
        PageEntity<CarouselFigure> carouselFigurePageEntityPageEntity = carouselFigureService.findPage(current, pagesize);
        return ResponseEntity.ok(carouselFigurePageEntityPageEntity);
    }


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


    @PostMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> update(CarouselFigure carouselFigure) {
        boolean rs = carouselFigureService.update(carouselFigure);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "CarouselFigure 更新成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 更新失败"));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        boolean rs = carouselFigureService.remove(id);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "CarouselFigure 删除成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "CarouselFigure 删除失败"));
        }
    }


    @PostMapping("/queryId")
    public ResponseEntity<CarouselFigure> findById(@RequestParam String id) {
        Optional<CarouselFigure> carouselFigure = Optional.ofNullable(carouselFigureService.findId(id));
        return carouselFigure.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }


    @RequestMapping("/queryAll")
    public ResponseEntity<List<CarouselFigure>> findAll() {
        List<CarouselFigure> carouselFigure = carouselFigureService.findAll();
        return ResponseEntity.ok(carouselFigure);
    }

}
