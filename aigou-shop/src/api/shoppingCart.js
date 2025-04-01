import axios from 'axios'

/**
 * 购物车相关接口
 */
export const shoppingCartApi = {
  /**
   * 更新购物车商品数量
   * @param {Object} params - 更新参数
   * @param {string} params.cartId - 购物车ID
   * @param {string} params.productId - 商品ID
   * @param {number} params.productNum - 更新后的商品数量
   * @returns {Promise} - 返回更新结果
   */
  updateProductNum: async (params) => {
    try {
      const response = await axios.get('/shopCartProduct/updateProductNum', { params });
      return response.data;
    } catch (error) {
      console.error("更新商品数量失败：", error);
      throw error;
    }
  },

  /**
   * 从购物车删除商品
   * @param {Object} params - 删除参数
   * @param {string} params.cartId - 购物车ID
   * @param {string} params.productId - 商品ID
   * @returns {Promise} - 返回删除结果
   */
  deleteProductFromCart: async (params) => {
    try {
      const response = await axios.delete('/shopCart/deleteProductFromCart', { params });
      return response.data;
    } catch (error) {
      console.error("删除商品失败：", error);
      throw error;
    }
  },

  /**
   * 批量删除购物车商品
   * @param {Array} items - 要删除的商品列表
   * @returns {Promise} - 返回批量删除结果
   */
  batchDeleteProducts: async (items) => {
    try {
      const promises = items.map(item =>
        axios.delete('/shopCart/deleteProductFromCart', {
          params: {
            cartId: item.cartId,
            productId: item.productId,
          },
        })
      );
      const results = await Promise.all(promises);
      return results.map(res => res.data);
    } catch (error) {
      console.error("批量删除失败：", error);
      throw error;
    }
  },

  /**
   * 获取用户购物车商品列表
   * @param {string} userId - 用户ID
   * @returns {Promise} - 返回购物车商品列表
   */
  getProductsByUserId: async (userId) => {
    try {
      const response = await axios.get(`/shopCart/getProductsByUserId?userId=${userId}`);
      return response.data;
    } catch (error) {
      console.error('获取购物车数据失败:', error);
      throw error;
    }
  },

  /**
   * 获取用户收货地址列表
   * @param {string} userId - 用户ID
   * @returns {Promise} - 返回收货地址列表
   */
  getReceivingAddressByUserId: async (userId) => {
    try {
      const response = await axios.get(`/receivingAddress/getReceivingAddressByUserId?userId=${userId}`);
      return response.data;
    } catch (error) {
      console.error('获取收货地址失败:', error);
      throw error;
    }
  },

  /**
   * 提交订单
   * @param {Object} orderData - 订单数据
   * @returns {Promise} - 返回订单提交结果
   */
  submitOrder: async (orderData) => {
    try {
      const response = await axios.post('/order/add', orderData);
      return response.data;
    } catch (error) {
      console.error("订单提交失败：", error);
      throw error;
    }
  },

  /**
   * 创建微信支付订单
   * @param {Object} params - 支付参数
   * @param {string} params.orderId - 订单ID
   * @param {number} params.price - 支付金额
   * @returns {Promise} - 返回微信支付结果
   */
  createWxPayOrder: async (params) => {
    try {
      const response = await axios.get('/wxpay/makeOrder', { params });
      return response.data;
    } catch (error) {
      console.error("微信下单失败：", error);
      throw error;
    }
  }
}; 