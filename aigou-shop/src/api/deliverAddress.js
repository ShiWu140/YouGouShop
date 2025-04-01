import axios from 'axios';

// 收货地址相关接口
export const deliverAddressApi = {
  // 获取用户的收货地址列表
  getReceivingAddresses: async (userId) => {
    try {
      const response = await axios.get("/receivingAddress/getReceivingAddressByUserId", {
        params: { userId }
      });
      if (response.data.code === 1) {
        return response.data.data;
      }
      throw new Error(response.data.msg || '获取收货地址失败');
    } catch (error) {
      console.error('Error fetching receiving addresses:', error);
      throw error;
    }
  },

  // 添加收货地址
  addReceivingAddress: async (address) => {
    try {
      const response = await axios.post("/receivingAddress/add", address);
      if (response.data.code === 1) {
        return response.data.data;
      }
      throw new Error(response.data.msg || '添加收货地址失败');
    } catch (error) {
      console.error('Error adding receiving address:', error);
      throw error;
    }
  },

  // 修改收货地址
  modifyReceivingAddress: async (address) => {
    try {
      const response = await axios.post("/receivingAddress/modify", address);
      if (response.data.code === 1) {
        return response.data.data;
      }
      throw new Error(response.data.msg || '修改收货地址失败');
    } catch (error) {
      console.error('Error modifying receiving address:', error);
      throw error;
    }
  },

  // 删除收货地址
  deleteReceivingAddress: async (id) => {
    try {
      const response = await axios.post("/receivingAddress/delete", { id });
      if (response.data.code === 1) {
        return response.data.data;
      }
      throw new Error(response.data.msg || '删除收货地址失败');
    } catch (error) {
      console.error('Error deleting receiving address:', error);
      throw error;
    }
  }
}; 