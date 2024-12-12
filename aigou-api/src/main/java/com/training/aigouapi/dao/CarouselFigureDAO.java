package com.training.aigouapi.dao;

import com.training.aigouapi.entity.CarouselFigure;

import java.util.List;

/**
 * 轮播图表数据操作接口
 *
 * @author ethan
 */
public interface CarouselFigureDAO {
    /**
     * 查询轮播图分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 轮播图列表
     * @throws IllegalArgumentException 如果参数无效
     */
    List<CarouselFigure> selectLimit(int start, int limit);

    /**
     * 查询轮播图总数
     *
     * @return 轮播图总数
     */
    long selectCount();

    /**
     * 查询所有轮播图
     *
     * @return 轮播图列表
     */
    List<CarouselFigure> selectAll();

    /**
     * 按id查询轮播图
     *
     * @param figureId 轮播图id
     * @return 查询到的轮播图对象，如果不存在则返回null
     */
    CarouselFigure selectId(String figureId);

    /**
     * 插入新轮播图
     *
     * @param figure 待插入的轮播图对象
     * @return 插入是否成功
     */
    boolean insert(CarouselFigure figure);

    /**
     * 删除轮播图
     *
     * @param figureId 待删除的轮播图id
     * @return 删除是否成功
     */
    boolean delete(String figureId);

    /**
     * 更新轮播图
     *
     * @param figure 待更新的轮播图对象
     * @return 更新是否成功
     */
    boolean update(CarouselFigure figure);
}
