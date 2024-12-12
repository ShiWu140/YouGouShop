package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.CarouselFigureDAO;
import com.training.aigouapi.entity.CarouselFigure;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图数据接口实现类
 *
 * @author ethan
 */
@Repository
public class CarouselFigureDAOImpl implements CarouselFigureDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<CarouselFigure> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(CarouselFigure.class, "select * from s_carousel_figure limit ?, ?", start, limit);
    }

    /**
     * 查询总数
     *
     * @return count
     */
    @Override
    public long selectCount() {
        Integer count = JDBCUtils.executeCount("select count(*) from s_carousel_figure");
        return count;
    }

    /**
     * 查询所有轮播图
     *
     * @return 轮播图列表
     */
    @Override
    public List<CarouselFigure> selectAll() {
        return JDBCUtils.executeQuery(CarouselFigure.class, "select * from s_carousel_figure");
    }

    /**
     * 根据轮播图id查询轮播图
     *
     * @param id 轮播图id
     * @return 查询到的轮播图对象
     */
    @Override
    public CarouselFigure selectId(String id) {
        List<CarouselFigure> carouselFigures = JDBCUtils.executeQuery(CarouselFigure.class, "select * from s_carousel_figure where id = ?", id);
        if (carouselFigures.isEmpty()) {
            return null;
        }
        // 如果查询结果不为空，则返回第一个轮播图对象
        return carouselFigures.get(0);
    }

    /**
     * 插入轮播图
     *
     * @param figure 待插入的轮播图对象
     * @return 是否插入成功
     */
    @Override
    public boolean insert(CarouselFigure figure) {
        int rows = JDBCUtils.executeUpdate("insert into s_carousel_figure(id, url, sequence_num) values(?, ?, ?)",
                figure.getId(), figure.getUrl(), figure.getSequenceNum());
        return rows > 0;
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return 是否删除成功
     */
    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("delete from s_carousel_figure where id = ?", id);
        return rows > 0;
    }

    /**
     * 更新轮播图
     *
     * @param figure 待更新的轮播图对象
     * @return 是否更新成功
     */
    @Override
    public boolean update(CarouselFigure figure) {
        int rows = JDBCUtils.executeUpdate("update s_carousel_figure set url = ?, sequence_num = ? where id = ?",
                figure.getUrl(), figure.getSequenceNum(), figure.getId());
        return rows > 0;
    }
}
