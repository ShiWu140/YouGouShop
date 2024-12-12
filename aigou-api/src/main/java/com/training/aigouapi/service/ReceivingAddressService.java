package com.training.aigouapi.service;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ReceivingAddress;

import java.util.List;

/**
 * 收货地址信息业务逻辑接口
 *
 * @author ethan
 */
public interface ReceivingAddressService {
    /**
     * 分页查询收货地址信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<ReceivingAddress> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有收货地址信息
     *
     * @return 收货地址信息列表
     */
    List<ReceivingAddress> findAll();

    /**
     * 根据收货地址ID查询收货地址信息
     *
     * @param addressId 收货地址ID
     * @return 收货地址信息对象
     */
    ReceivingAddress findId(String addressId);

    /**
     * 添加新的收货地址信息
     *
     * @param receivingAddress 新增的收货地址信息对象
     * @return 是否添加成功，true表示成功，false表示失败
     */
    boolean save(ReceivingAddress receivingAddress);

    /**
     * 删除收货地址信息
     *
     * @param addressId 需要删除的收货地址ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String addressId);

    /**
     * 更新收货地址信息
     *
     * @param receivingAddress 需要更新的收货地址信息对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(ReceivingAddress receivingAddress);
}
