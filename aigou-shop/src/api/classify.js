import axios from 'axios';

// 分类相关接口
export const classifyApi = {
  // 获取分页商品数据
  getPageProducts: async (params) => {
    try {
      const response = await axios.post("/product/pageH", params);
      if (response.data.code === 1) {
        return {
          records: response.data.data.records,
          total: response.data.data.total,
          hasData: response.data.data.records.length > 0
        };
      }
      return {
        records: [],
        total: 0,
        hasData: false
      };
    } catch (error) {
      console.error('Error fetching page products:', error);
      return {
        records: [],
        total: 0,
        hasData: false
      };
    }
  },

  // 获取所有品牌
  getAllBrands: async () => {
    try {
      const response = await axios.get("/brand/all");
      if (response.data.code === 1) {
        return response.data.data;
      }
      return [];
    } catch (error) {
      console.error('Error fetching brands:', error);
      return [];
    }
  },

  // 获取所有商品类型
  getAllProductTypes: async () => {
    try {
      const response = await axios.get("/productType/all");
      if (response.data.code === 1) {
        return response.data.data;
      }
      return [];
    } catch (error) {
      console.error('Error fetching product types:', error);
      return [];
    }
  }
}; 