import axios from 'axios'

/**
 * 检查订单支付状态
 * @param {string} tradeNo - 交易单号
 * @returns {Promise} - 返回订单状态
 */
export const checkOrderStatus = async (tradeNo) => {
  try {
    const response = await axios.get('/wxpay/checkOrder', {
      params: {
        tradeNo
      }
    });
    return response.data;
  } catch (error) {
    console.error('检查订单状态失败:', error);
    throw error;
  }
}; 