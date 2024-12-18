package com.training.aigouapi.service;

import com.training.aigouapi.entity.CarouselFigure;
import com.training.aigouapi.common.PageEntity;

import java.util.List;

/**
 * 轮播图业务逻辑接口
 *
 * @author ethan
 */
public interface CarouselFigureService {
    /**
     * 查询分页信息
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return 包含轮播图数据的分页对象
     */
    PageEntity<CarouselFigure> findPage(Integer current, Integer pageSize);
    /**
     * 查询所有轮播图
     *
     * @return 轮播图列表
     */
    List<CarouselFigure> findAll();

    /**
     * 按id查询轮播图
     *
     * @param figureId 轮播图id
     * @return 查询到的轮播图对象
     */
    CarouselFigure findId(String figureId);

    /**
     * 插入新轮播图
     *
     * @param figure 待插入的轮播图对象
     * @return 插入是否成功
     */
    boolean save(CarouselFigure figure);

    /**
     * 删除轮播图
     *
     * @param figureId 待删除的轮播图id
     * @return 删除是否成功
     */
    boolean remove(String figureId);

    /**
     * 更新轮播图
     *
     * @param figure 待更新的轮播图对象
     * @return 更新是否成功
     */
    boolean update(CarouselFigure figure);
}
