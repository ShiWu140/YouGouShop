package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.ReceivingAddressDAO;
import com.training.aigouapi.entity.ReceivingAddress;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ReceivingAddressDAOlmpl implements ReceivingAddressDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<ReceivingAddress> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(ReceivingAddress.class, "select * from s_receiving_address limit ?, ?", start, limit);
    }

    /**
     * 查询总数
     *
     * @return count
     */
    @Override
    public long selectCount() {
        Integer count = JDBCUtils.executeCount("select count(*) from s_receiving_address");
        return count;
    }

    @Override
    public List<ReceivingAddress> selectAll() {
        List<ReceivingAddress> receivingAddresses = JDBCUtils.executeQuery(ReceivingAddress.class, "select * from s_receiving_address");
        return receivingAddresses;
    }

    @Override
    public ReceivingAddress selectId(String receivingAddressId) {
        List<ReceivingAddress> receivingAddresses = JDBCUtils.executeQuery(ReceivingAddress.class, "select * from s_receiving_address where id = ?", receivingAddressId);
        if (receivingAddresses.isEmpty()) {
            return null;
        }
        return receivingAddresses.get(0);
    }

    @Override
    public boolean insert(ReceivingAddress receivingAddress) {
        int rows = JDBCUtils.executeUpdate("insert into s_receiving_address(id, receiving_address, receiving_person, mobile_phone, user_id, is_default) values(?,?,?,?,?,?)",
                receivingAddress.getId(), receivingAddress.getReceivingAddress(), receivingAddress.getReceivingPerson(), receivingAddress.getMobilePhone(), receivingAddress.getUserId(), receivingAddress.getIsDefault());
        return rows > 0;
    }

    @Override
    public boolean delete(String receivingAddressId) {
        int rows = JDBCUtils.executeUpdate("delete from s_receiving_address where id = ?", receivingAddressId);
        return rows > 0;
    }

    @Override
    public boolean update(ReceivingAddress receivingAddress) {
        int rows = JDBCUtils.executeUpdate("update s_receiving_address set receiving_address = ?, receiving_person = ?, mobile_phone = ?, user_id = ?, is_default = ? where id = ?",
                receivingAddress.getReceivingAddress(), receivingAddress.getReceivingPerson(), receivingAddress.getMobilePhone(), receivingAddress.getUserId(), receivingAddress.getIsDefault(), receivingAddress.getId());
        return rows > 0;
    }
}
