package com.training.aigoushopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.training.aigoushopapi.entity.ReceivingAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Mapper
public interface ReceivingAddressMapper extends BaseMapper<ReceivingAddress> {

    /**
     * 根据用户id获取收货地址
     *
     * @param userId 用户id
     * @return ReceivingAddress
     */
    List<ReceivingAddress> getReceivingAddressByUserId(Long userId);
}
