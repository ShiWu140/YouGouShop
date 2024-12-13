package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.ReceivingAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收货地址表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ReceivingAddressMapper {
    /**
     * 查询收货地址分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 收货地址列表
     */
    List<ReceivingAddress> selectLimit(int start, int limit);

    /**
     * 查询收货地址总数
     *
     * @return 收货地址总数
     */
    long selectCount();

    /**
     * 查询所有收货地址
     *
     * @return 收货地址列表
     */
    List<ReceivingAddress> selectAll();

    /**
     * 按id查询收货地址
     *
     * @param addressId 收货地址ID
     * @return 收货地址对象，如果不存在则返回null
     */
    ReceivingAddress selectId(String addressId);

    /**
     * 插入新收货地址
     *
     * @param receivingAddress 收货地址对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(ReceivingAddress receivingAddress);

    /**
     * 删除收货地址
     *
     * @param addressId 收货地址ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String addressId);

    /**
     * 更新收货地址
     *
     * @param receivingAddress 收货地址对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(ReceivingAddress receivingAddress);
}
