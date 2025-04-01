import axios from 'axios';

// 订单相关接口
export const buyerTradeApi = {
  // 获取用户订单详情
  getUserOrders: async (userId) => {
    try {
      const response = await axios.get(`/order/details/${userId}`);
      return response.data.data;
    } catch (error) {
      console.error('Error fetching user orders:', error);
      return [];
    }
  },

  // 更新订单发货状态
  updateDeliveryStatus: async (orderId, deliveryStatus) => {
    try {
      const response = await axios.post('/order/updateDeliveryStatus', null, {
        params: {
          orderId,
          deliveryStatus
        }
      });
      if (!response.data) {
        throw new Error('更新发货状态失败');
      }
      return response.data;
    } catch (error) {
      console.error('Error updating delivery status:', error);
      throw error;
    }
  }
};
