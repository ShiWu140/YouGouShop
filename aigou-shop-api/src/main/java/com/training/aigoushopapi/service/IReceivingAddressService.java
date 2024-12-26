package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.ReceivingAddress;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IReceivingAddressService extends IService<ReceivingAddress> {

    /**
     * 根据userId查询收获地址
     *
     * @param userId 用户ID
     */
    List<ReceivingAddress> getReceivingAddressByUserId(Long userId);

}
