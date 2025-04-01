import axios from 'axios';

// 数据处理方法
const filterValidData = (data) => {
  return data.filter(item => item !== null && item.id);
};

// 轮播图相关接口
export const carouselApi = {
  // 获取所有轮播图
  getAllCarouselFigures: async () => {
    try {
      const response = await axios.get('/carouselFigure/all');
      return response.data.data.sort((a, b) => a.sequenceNum - b.sequenceNum);
    } catch (error) {
      console.error('Error fetching carousel figures:', error);
      return [];
    }
  }
};

// 商品类型相关接口
export const productTypeApi = {
  // 获取所有商品类型
  getAllProductTypes: async () => {
    try {
      const response = await axios.get('/productType/all');
      return filterValidData(response.data.data);
    } catch (error) {
      console.error('Error fetching product types:', error);
      return [];
    }
  }
};

// 商品相关接口
export const productApi = {
  // 获取新品
  getNewProducts: async () => {
    try {
      const response = await axios.get('/product/newProduct');
      return filterValidData(response.data.data);
    } catch (error) {
      console.error('Error fetching product news:', error);
      return [];
    }
  },
  // 获取销量排行
  getProductSales: async () => {
    try {
      const response = await axios.get('/product/getProductSalesList');
      return filterValidData(response.data.data);
    } catch (error) {
      console.error('Error fetching product sales:', error);
      return [];
    }
  },
  // 获取所有分类商品
  getAllCategoryProduct: async () => {
    try {
      const response = await axios.get('/product/allCategoryProduct');
      return filterValidData(response.data.data);
    } catch (error) {
      console.error('Error fetching all category product:', error);
      return [];
    }
  }
};
