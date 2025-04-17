import axios from 'axios';

export const goodsApi = {
    fetchProductTypes: async () => {
        try {
            const res = await axios.get('/productType/all');
            return res.data.data; // 👈 只返回有用部分
        } catch (error) {
            console.error('Error fetching product types:', error);
            return [];
        }
    },

    fetchGoodsDetail: async (id) => {
        try {
            const res = await axios.get(`/product/detail?id=${id}`);
            return res.data.data;
        } catch (error) {
            console.error('Error fetching goods detail:', error);
            return {};
        }
    },

    fetchRandomProductsByType: async (id) => {
        try {
            const res = await axios.get(`/product/sameType?id=${id}`);
            return res.data.data;
        } catch (error) {
            console.error('Error fetching random products:', error);
            return [];
        }
    },

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
