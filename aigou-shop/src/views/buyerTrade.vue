<script setup>
import axios from "axios";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { onMounted, ref } from "vue";

const orderData = ref(null);

const loadOrder = async () => {
  try {
    const userId = localStorage.getItem('userId');
    // 这里将请求地址修改为正确的后端接口地址
    const response = await axios.get(`/details/${userId}`);
    orderData.value = response.data;
    console.log(orderData.value);
  } catch (error) {
    console.error("请求订单详情时出错:", error);
    // 可以添加一些错误处理的逻辑，比如显示错误信息给用户
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
    <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>
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
        <li class="operate">交易操作</li>
      </ul>
    </div>
    <!--详细展示-->
    <div class="trade-list">
      <div v-for="item in order">
        <div class="trade-list-top clear-float">
          <div class="trade-list-l">
            <span class="trade-date">{{item.createTime}}</span>
            <span class="trade-no">订单号:{{item.id}}</span>
          </div>
          <a href="#"><i class="fa fa-trash trade-list-r"></i></a>
        </div>
        <div>
          <ul class="clear-float">
            <li class="info">
              <img src="@/assets/img/book01.jpg"/>
              <p>七年级7册包邮
                西游记原著正版朝花夕拾鲁迅初中生呐喊猎人笔记湘行散记镜花缘白洋淀纪事世界经典名著青少学版初一课外阅读书籍</p>
            </li>
            <li class="price">￥55.80</li>
            <li class="num">1</li>
            <li class="real-price">￥55.80</li>
            <li class="operate"><a href="#">再次购买</a></li>
          </ul>
          <ul class="clear-float">
            <li class="info">
              <img src="@/assets/img/mod01.jpg"/>
              <p> 日本正品代购 资生堂心机2014年新款 润彩唇膏/口红 保湿滋润持久</p>
            </li>
            <li class="price">￥198.00</li>
            <li class="num">1</li>
            <li class="real-price">￥198.00</li>
            <li class="operate"><a href="#">再次购买</a></li>
          </ul>
        </div>
      </div>
      <div>
        <div class="trade-list-top clear-float">
          <div class="trade-list-l">
            <span class="trade-date">2018-10-09</span>
            <span class="trade-no">订单号:23959345783458275</span>
          </div>
          <a href="#"><i class="fa fa-trash trade-list-r"></i></a>
        </div>
        <div>
          <ul class="clear-float">
            <li class="info">
              <img src="@/assets/img/cloth01.jpg"/>
              <p>2018秋冬新款女装减龄慵懒风V领毛衣女宽松套头显瘦长袖针织衫潮</p>
            </li>
            <li class="price">￥300.00</li>
            <li class="num">1</li>
            <li class="real-price">￥300.00</li>
            <li class="operate"><a href="#">再次购买</a></li>
          </ul>
        </div>
      </div>
      <div>
        <div class="trade-list-top clear-float">
          <div class="trade-list-l">
            <span class="trade-date">2018-10-09</span>
            <span class="trade-no">订单号:23959345783458275</span>
          </div>
          <a href="#"><i class="fa fa-trash trade-list-r"></i></a>
        </div>
        <div>
          <ul class="clear-float">
            <li class="info">
              <img src="@/assets/img/mod01.jpg"/>
              <p> 日本正品代购 资生堂心机2014年新款 润彩唇膏/口红 保湿滋润持久</p>
            </li>
            <li class="price">￥198.00</li>
            <li class="num">1</li>
            <li class="real-price">￥198.00</li>
            <li class="operate"><a href="#">再次购买</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <Footer/>
</template>
<style scoped>
@import "@/assets/css/buyerTrade.css";
</style>

