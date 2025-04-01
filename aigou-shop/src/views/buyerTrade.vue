<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {onMounted, ref} from "vue";
import { buyerTradeApi } from '@/api/buyerTrade';
import logoImage from '@/assets/img/logo.png';
import { ElMessage } from 'element-plus';

const orderData = ref([]);

const loadOrder = async () => {
  try {
    const userId = localStorage.getItem('userId');
    orderData.value = await buyerTradeApi.getUserOrders(userId);
  } catch (error) {
    console.error("加载订单详情时出错:", error);
  }
};

// 获取支付状态文本
const getPaymentStatusText = (state) => {
  return state === 0 ? '未支付' : '已支付';
};

// 获取发货状态文本
const getDeliveryStatusText = (status) => {
  const statusMap = {
    0: '未发货',
    1: '已发货',
    2: '已收货',
    3: '已完成'
  };
  return statusMap[status] || '未知状态';
};

// 获取发货状态类型
const getDeliveryStatusType = (status) => {
  const typeMap = {
    0: 'info',
    1: 'warning',
    2: 'success',
    3: 'success'
  };
  return typeMap[status] || 'info';
};

// 确认收货
const confirmReceive = async (orderId) => {
  try {
    await buyerTradeApi.updateDeliveryStatus(orderId, 2);
    ElMessage.success('确认收货成功！');
    loadOrder(); // 重新加载订单列表
  } catch (error) {
    ElMessage.error('确认收货失败，请重试！');
  }
};

onMounted(() => {
  loadOrder();
});
</script>

<template>
  <!--头部-->
  <div class="top" id="top">
    <Header/>
  </div>

  <div class="page-container">
    <div class="header-section">
      <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>

      <span class="cart">我的订单</span>
    </div>
    
    <!--订单列表-->
    <div class="trade">
      <!--标题栏-->
      <div class="trade-title">
        <ul class="title-list">
          <li class="info">宝贝</li>
          <li class="price">单价</li>
          <li class="num">数量</li>
          <li class="real-price-title">实付款</li>
          <li class="status">订单状态</li>
          <li class="action">操作</li>
        </ul>
      </div>
      
      <!--详细展示-->
      <div class="trade-list">
        <div v-for="order in orderData" :key="order.orderId" class="order-card">
          <div class="order-info">
            <div class="order-meta">
              <div class="order-date">
                <i class="el-icon-time"></i>
                <span>{{ order.createTime }}</span>
              </div>
              <div class="order-no">
                <i class="el-icon-document"></i>
                <span>订单号: {{ order.orderId }}</span>
              </div>
              <!-- <div class="order-status">
                <el-tag :type="order.state === 0 ? 'danger' : 'success'">
                  支付状态：{{ getPaymentStatusText(order.state) }}
                </el-tag>
                <el-tag :type="getDeliveryStatusType(order.deliveryStatus)" class="ml-2">
                  发货状态：{{ getDeliveryStatusText(order.deliveryStatus) }}
                </el-tag>
              </div> -->
            </div>
          </div>
          
          <div v-for="product in order.products" :key="product.productId" class="product-item">
            <ul class="product-list">
              <li class="info">
                <el-image 
                  :src="product.productImage" 
                  style="width: 100px; height: 100px"
                  :preview-src-list="[product.productImage]"
                  fit="scale-down"
                  class="product-image"
                />
                <a class="product-name">{{ product.productName }}</a>
              </li>
              <li class="price">￥{{ product.price }}</li>
              <li class="num">{{ product.productNum }}</li>
              <li class="real-price">￥{{ product.price * product.productNum }}</li>
              <li class="status">
                <el-tag :type="order.state === 0 ? 'danger' : 'success'">
                  {{ getPaymentStatusText(order.state) }}
                </el-tag>
                <el-tag :type="getDeliveryStatusType(order.deliveryStatus)" class="ml-2">
                  {{ getDeliveryStatusText(order.deliveryStatus) }}
                </el-tag>
              </li>
              <li class="action">
                <el-button 
                  v-if="order.state === 0" 
                  type="primary" 
                  size="small"
                  @click="$router.push(`/payment?orderId=${order.orderId}&totalAmount=${order.products.reduce((sum, p) => sum + p.price * p.productNum, 0)}`)"
                >
                  去支付
                </el-button>
                <el-button 
                  v-if="order.state === 1 && order.deliveryStatus === 1" 
                  type="success" 
                  size="small"
                  @click="confirmReceive(order.orderId)"
                >
                  确认收货
                </el-button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
@import "@/assets/css/buyerTrade.css";

.page-container {
  max-width: 1230px;
  margin: 0 auto;
  padding: 0 15px;
}

.header-section {
  display: flex;
  align-items: center;
}

.logo {
  display: block;
  margin-right: 0px;
}

.trade {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin-bottom: 20px;
}

.trade-title {
  background: #f8f9fa;
  padding: 15px 20px;
  border-bottom: 2px solid #409EFF;
}

.title-list {
  display: flex;
  margin: 0;
  padding: 0;
  list-style: none;
  justify-content: space-between;
}

.title-list li {
  color: #333;
  font-weight: 600;
  font-size: 18px;
  position: relative;
}

.title-list .info {
  flex: 2;
  min-width: 300px;
  padding-left: 10px;
}

.title-list .price, 
.title-list .num, 
.title-list .real-price-title,
.title-list .status,
.title-list .action {
  flex: 1;
  text-align: center;
  min-width: 100px;
}

.order-card {
  border-bottom: 1px solid #eee;
  transition: all 0.3s ease;
}

.order-card:hover {
  background: #f8f9fa;
}

.order-info {
  padding: 15px 20px;
  background: #f8f9fa;
  border-bottom: 1px solid #e4e7ed;
}

.order-meta {
  display: flex;
  gap: 30px;
  color: #606266;
  font-size: 14px;
}

.order-date, .order-no {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
}

.order-date i, .order-no i {
  font-size: 16px;
  color: #409EFF;
}

.order-date span, .order-no span {
  color: #303133;
  font-weight: 600;
}

.product-item {
  padding: 20px;
}

.product-list {
  display: flex;
  align-items: center;
  margin: 0;
  padding: 0;
  list-style: none;
  justify-content: space-between;
}

.info {
  flex: 2;
  display: flex;
  align-items: center;
  gap: 20px;
  min-width: 300px;
}

.price, .num {
  flex: 1;
  text-align: center;
  color: #333;
  font-weight: 500;
  min-width: 100px;
}

.real-price-title {
  flex: 1;
  text-align: center;
  color: #333;
  font-weight: 600;
  min-width: 100px;
}

.real-price {
  flex: 1;
  text-align: center;
  color: #f56c6c;
  font-size: 16px;
  font-weight: 600;
  min-width: 100px;
}

.product-image {
  background-color: #fff;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 4px;
  transition: transform 0.3s ease;
  flex-shrink: 0;
}

.product-image:hover {
  transform: scale(1.05);
}

.product-name {
  flex: 1;
  color: #333;
  text-decoration: none;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  min-width: 150px;
}

:deep(.el-image) {
  display: flex;
  justify-content: center;
  align-items: center;
}

:deep(.el-image__inner) {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .page-container {
    padding: 10px;
  }
  
  .product-list {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .info {
    width: 100%;
    min-width: auto;
  }
  
  .price, .num, .real-price-title, .real-price {
    width: 100%;
    text-align: left;
    min-width: auto;
  }
  
  .title-list {
    display: none;
  }
  
  .product-name {
    min-width: auto;
  }
}

.status,
.action {
  flex: 1;
  text-align: center;
  min-width: 120px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: center;
}

.ml-2 {
  margin-left: 8px;
}

.order-status {
  display: flex;
  gap: 8px;
  margin-left: 20px;
}
</style>

