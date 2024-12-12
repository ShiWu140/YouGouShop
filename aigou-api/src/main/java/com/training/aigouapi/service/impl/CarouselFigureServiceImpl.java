package com.training.aigouapi.service.impl;

import com.training.aigouapi.dao.CarouselFigureDAO;
import com.training.aigouapi.dao.impl.CarouselFigureDAOImpl;
import com.training.aigouapi.entity.CarouselFigure;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.service.CarouselFigureService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图相关业务逻辑实现类
 */
@Service
public class CarouselFigureServiceImpl implements CarouselFigureService {

    /**
     * 调用dao层对象
     */
    private CarouselFigureDAO carouselFigureDAO = new CarouselFigureDAOImpl();

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<CarouselFigure> findPage(Integer current, Integer pageSize) {
        //查询总数
        long total = carouselFigureDAO.selectCount();
        //分页查询数据
        List<CarouselFigure> brands = carouselFigureDAO.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<CarouselFigure> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(total);
        pageEntity.setRecords(brands);
        return pageEntity;
    }


    /**
     * 查询所有轮播图信息
     *
     * @return 轮播图信息列表
     */
    @Override
    public List<CarouselFigure> findAll() {
        return carouselFigureDAO.selectAll();
    }

    /**
     * 根据轮播图id查询轮播图信息
     *
     * @param figureId 轮播图id
     * @return 查询到的轮播图对象
     */
    @Override
    public CarouselFigure findId(String figureId) {
        return carouselFigureDAO.selectId(figureId);
    }

    /**
     * 添加轮播图信息
     *
     * @param figure 待添加的轮播图对象
     * @return 是否添加成功 true 成功 false 失败
     */
    @Override
    public boolean save(CarouselFigure figure) {
        return carouselFigureDAO.insert(figure);
    }

    /**
     * 删除轮播图信息
     *
     * @param figureId 轮播图id
     * @return 是否删除成功 true 成功 false 失败
     */
    @Override
    public boolean remove(String figureId) {
        return carouselFigureDAO.delete(figureId);
    }

    /**
     * 更新轮播图信息
     *
     * @param figure 待更新的轮播图对象
     * @return 是否更新成功 true 成功 false 失败
     */
    @Override
    public boolean update(CarouselFigure figure) {
        return carouselFigureDAO.update(figure);
    }
}
