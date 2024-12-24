package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.ReceivingAddress;
import com.training.aigoushopapi.mapper.ReceivingAddressMapper;
import com.training.aigoushopapi.service.IReceivingAddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class ReceivingAddressServiceImpl extends ServiceImpl<ReceivingAddressMapper, ReceivingAddress> implements IReceivingAddressService {

    @Resource
    ReceivingAddressMapper receivingAddressMapper;

    /**
     * 根据userId查询收获地址
     *
     * @param userId 用户ID
     */
    @Override
    public List<ReceivingAddress> getReceivingAddressByUserId(Long userId) {
        return receivingAddressMapper.getReceivingAddressByUserId(userId);
    }
}
