import axios from 'axios';

/**
 * 商品相关接口
 */
export const goodsApi = {
  /**
   * 获取所有商品分类
   * @returns {Promise} - 返回商品分类列表
   */
  fetchProductTypes: async () => {
    try {
      const response = await axios.get('/productType/all');
      return response.data.data;
    } catch (error) {
      console.error('Error fetching product types:', error);
      throw error;
    }
  },

  /**
   * 获取商品详情
   * @param {string|number} id - 商品ID
   * @returns {Promise} - 返回商品详情
   */
  fetchGoodsDetail: async (id) => {
    try {
      const response = await axios.get(`/product/detail?id=${id}`);
      return response.data.data;
    } catch (error) {
      console.error('Error fetching goods detail:', error);
      throw error;
    }
  },

  /**
   * 获取同类商品
   * @param {string|number} id - 商品ID
   * @returns {Promise} - 返回同类商品列表
   */
  fetchRandomProductsByType: async (id) => {
    try {
      const response = await axios.get(`/product/sameType?id=${id}`);
      return response.data.data;
    } catch (error) {
      console.error('Error fetching random products:', error);
      throw error;
    }
  },

  /**
   * 添加商品到购物车
   * @param {string|number} userId - 用户ID
   * @param {string|number} productId - 商品ID
   * @param {number} quantity - 商品数量
   * @returns {Promise} - 返回添加结果
   */
  addToCart: async (userId, productId, quantity) => {
    try {
      await axios.post('/shopCart/addProductFromCart', {
        userId,
        productId,
        quantity
      });
      return true;
    } catch (error) {
      console.error('Error adding to cart:', error);
      throw error;
    }
  }
}; 