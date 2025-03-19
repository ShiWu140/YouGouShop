<script setup>
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";
import Footer from "@/components/Footer.vue";
import {onMounted, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage} from "element-plus"; // 引入 ElMessage

const route = useRoute();
const productTypes = ref([]);
const goodsDetail = ref({});
const sameType = ref([]);
const quantity = ref(1);

const fetchProductTypes = async () => {
  try {
    const response = await axios.get('/productType/all');
    productTypes.value = response.data.data;
  } catch (error) {
    console.error('Error fetching product types:', error);
  }
};
const fetchGoodsDetail = async () => {
  try {
    const response = await axios.get('/product/detail?id=' + route.query.id);
    goodsDetail.value = response.data.data;
  } catch (error) {
    console.error('Error GoodsDetail:', error);
  }
};
const fetchRandomProductsByType = async () => {
  try {
    const response = await axios.get('/product/sameType?id=' + route.query.id);
    sameType.value = response.data.data;
  } catch (error) {
    console.error('Error fetching random products:', error);
  }
};

// 加入购物车方法
const addToCart = async () => {
  try {
    await axios.post('/shopCart/addProductFromCart', {
      userId: localStorage.getItem('userId'),
      productId: goodsDetail.value.id,
      quantity: quantity.value
    });
    ElMessage.success('添加购物车成功！');
  } catch (error) {
    ElMessage.error('添加失败，请重试');
    console.error('添加购物车失败', error);
  }
};

// 数量控制
const increment = () => {
  quantity.value++;
};
const decrement = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

onMounted(() => {
  fetchRandomProductsByType();
  fetchGoodsDetail();
  fetchProductTypes();
});
</script>

<template>
  <Header/>
  <Search/>
  <!--导航栏-->
  <div class="nav">
    <div class="w1230">
      <div id="all-goods">
        <span class="all-goods">所有商品分类</span>
        <div class="nav-er" id="nav-er">
          <ul>
            <li v-for="type in productTypes" :key="type.id">
              <h3>
                <a href="#" class="icon-center">
                  <el-icon :size="20">
                    <component :is="type.productTypeIcon"></component>
                  </el-icon>
                  {{ type.productTypeName }}</a>
              </h3>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!--商品详情-->
  <div class="w1230 clear-float goods-main">
    <div class="big-img">
      <el-image :src="goodsDetail.productImage" fit="contain" style="width: 360px;height: 360px"/>
    </div>
    <div class="goods-detail">
      <h3 class="goods-title">{{ goodsDetail.productName }}</h3>
      <p class="price">价格<span>￥{{ goodsDetail.price }}</span></p>
      <p class="store-num">销量：<span>{{ goodsDetail.salasNum }}件</span></p>
      <div class="update-num">
        <div class="quantity">
          <span class="reduce" @click="decrement">-</span>
          <input type="number" v-model="quantity" min="1"/>
          <span class="add" @click="increment">+</span>
        </div>
        <a href="#" id="addCart" title="添加购物车" @click.prevent="addToCart">
          <i class="fa fa-shopping-cart"></i>添加购物车
        </a>
      </div>
    </div>
    <!--店家承诺-->
    <div class="promise">
      <h3><i class="fa fa-handshake-o"></i>爱购承诺</h3>
      <p>爱购平台卖家销售并发货的商品，由平台卖家提供发票和相应的售后服务。请您放心购买！
        注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若没有及时更新，请大家谅解！</p>
    </div>
  </div>
  <!--商品介绍-->
  <div class="w1230 clear-float">
    <!--商品推荐-->
    <div class="recommend goods-show">
      <h3>看了本商品的用户最终购买了</h3>
      <ul class="clear-float" v-for="product in sameType" :key="product.id">
        <li><a :href="`/goodsDetail?id=${product.id}`">
          <div class="g-img">
            <el-image :src="product.productImage" fit="contain" style="width: 230px;height: 230px"/>
          </div>
          <p class="g-title">{{ product.productName }}</p>
          <span class="g-price">￥{{ product.price }}</span>
          <span class="g-num">销量:{{ product.salasNum }}</span>
        </a></li>
      </ul>
    </div>
    <!--商品介绍-->
    <div class="goods-des">
      <h3 class="goods-tro">商品介绍</h3>
      <div class="goods-info">
        <ul>{{ goodsDetail.productDesc }}</ul>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
@import "@/assets/css/goodsDetail.css";
</style>
