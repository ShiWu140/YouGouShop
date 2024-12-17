package com.training.aigouapi.service.impl;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ReceivingAddress;
import com.training.aigouapi.mapper.ReceivingAddressMapper;
import com.training.aigouapi.service.ReceivingAddressService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 收货地址信息相关业务逻辑实现类
 */
@Service
public class ReceivingAddressServicelmpl implements ReceivingAddressService {
    /**
     * 调用dao层对象
     */
    @Resource
    private ReceivingAddressMapper ReceivingAddressMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<ReceivingAddress> findPage(Integer current, Integer pageSize) {
        //分页查询数据
        List<ReceivingAddress> receivingAddresses = ReceivingAddressMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<ReceivingAddress> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(ReceivingAddressMapper.selectCount());
        pageEntity.setRecords(receivingAddresses);
        return pageEntity;
    }

    /**
     * 查询所有收货地址信息
     *
     * @return 收货地址信息列表
     */
    @Override
    public List<ReceivingAddress> findAll() {
        return ReceivingAddressMapper.selectAll();
    }

    /**
     * 根据收货地址id查询收货地址信息
     *
     * @param addressId
     * @return
     */
    @Override
    public ReceivingAddress findId(String addressId) {
        return ReceivingAddressMapper.selectId(addressId);
    }

    /**
     * 添加收货地址信息
     *
     * @param receivingAddress
     * @return 是否添加成功 true 成功 false 失败
     */
    @Override
    public boolean save(ReceivingAddress receivingAddress) {
        return ReceivingAddressMapper.insert(receivingAddress);
    }

    /**
     * 删除收货地址信息
     *
     * @param addressId
     * @return 是否删除成功 true 成功 false 失败
     */
    @Override
    public boolean remove(String addressId) {
        return ReceivingAddressMapper.delete(addressId);
    }

    /**
     * 更新收货地址信息
     *
     * @param receivingAddress
     * @return 是否更新成功 true 成功 false 失败
     */
    @Override
    public boolean update(ReceivingAddress receivingAddress) {
        return ReceivingAddressMapper.update(receivingAddress);
    }
}
