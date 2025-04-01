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
  }
};
