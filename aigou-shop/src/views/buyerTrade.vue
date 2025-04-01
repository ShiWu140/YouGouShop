<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {onMounted, ref} from "vue";
import { buyerTradeApi } from '@/api/buyerTrade';

const orderData = ref([]);

const loadOrder = async () => {
  try {
    const userId = localStorage.getItem('userId');
    orderData.value = await buyerTradeApi.getUserOrders(userId);
  } catch (error) {
    console.error("加载订单详情时出错:", error);
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
  <div class="w1230">
    <el-image 
      src="@/assets/img/logo.png" 
      style="width: 100px; height: 40px" 
      class="logo"
    />
    <span class="cart">我的订单</span>
  </div>
  <!--订单列表-->
  <div class="w1230 trade">
    <!--标题栏-->
    <div class="trade-title">
      <ul class="clear-float">
        <li class="info">宝贝</li>
        <li class="price">单价</li>
        <li class="num">数量</li>
        <li class="real-price-title">实付款</li>
        <!--        <li class="operate">交易操作</li>-->
      </ul>
    </div><!--详细展示-->
    <div class="trade-list">
      <div v-for="order in orderData" :key="order.orderId">
        <div class="trade-list-top clear-float">
          <div class="trade-list-l">
            <span class="trade-date">{{ order.createTime }}</span>
            <span class="trade-no">订单号:{{ order.orderId }}</span>
          </div>
          <!--      <a href="#"><i class="fa fa-trash trade-list-r"></i></a>-->
        </div>
        <div v-for="product in order.products" :key="product.productId">
          <ul class="clear-float">
            <li class="info">
              <el-image 
                :src="product.productImage" 
                style="width: 100px; height: 100px"
                fit="cover"
              />
              <a class="product-name">{{ product.productName }}</a>
            </li>

            <li class="price">￥{{ product.price }}</li>
            <li class="num">{{ product.productNum }}</li>
            <li class="real-price">￥{{ product.price * product.productNum }}</li>
            <!--        <li class="operate"><a href="#">再次购买</a></li>-->
          </ul>
        </div>
      </div>
    </div>

  </div>
  <Footer/>
</template>
<style scoped>
@import "@/assets/css/buyerTrade.css";
.info {
  display: flex;
  align-items: center; /* 垂直居中 */
}

.product-name {
  margin-left: 50px; /* 距离左边的图片50px */
}

</style>

