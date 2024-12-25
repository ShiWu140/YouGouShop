<script setup>
import {onMounted, ref} from 'vue';
import axios from 'axios';
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";
import Footer from "@/components/Footer.vue";

const carouselFigures = ref([]);

const fetchCarouselFigures = async () => {
  try {
    const response = await axios.get('/carouselFigure/all');
    // 对获取的轮播图数据根据 sequenceNum 进行排序，并截取前五个
    carouselFigures.value = response.data.data.sort((a, b) => a.sequenceNum - b.sequenceNum);
    console.log('Carousel figures fetched:', carouselFigures.value)
  } catch (error) {
    console.error('Error fetching carousel figures:', error);
  }
};
const productTypes = ref([]);

const fetchProductTypes = async () => {
  try {
    const response = await axios.get('/productType/all');
    productTypes.value = response.data.data;
    console.log('Product types fetched:', productTypes.value)
  } catch (error) {
    console.error('Error fetching product types:', error);
  }
};
const productNews = ref([]);
const fetchProductNews = async () => {
  try {
    const response = await axios.get('/product/page?current=1&size=6');
    productNews.value = response.data.data.records;
    console.log('Product news fetched:', productNews.value)
  } catch (error) {
    console.error('Error fetching product news:', error);
  }
};
const productSales = ref([]);
const fetchProductSales = async () => {
  try {
    const response = await axios.get('/product/getProductSalesList');
    productSales.value = response.data.data;
    console.log('Product sales fetched:', productSales.value)
  } catch (error) {
    console.error('Error fetching product sales:', error);
  }
};
const allCategoryProduct = ref([]);
const fetchAllCategoryProduct = async () => {
  try {
    const response = await axios.get(`/product/allCategoryProduct`);
    allCategoryProduct.value = response.data.data;
    console.log('allCategoryProduct fetched:', allCategoryProduct.value)
  } catch (error) {
    console.error('Error allCategoryProduct:', error);
  }
};
onMounted(() => {
  fetchProductTypes();
  fetchCarouselFigures();
  fetchProductNews();
  fetchProductSales();
  fetchAllCategoryProduct();
});
</script>
<template>
  <Header/>
  <Search/>
  <!--分类+banner-->
  <div class="main w1230 clear-float">
    <!--商品分类-->
    <div class="classify">
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
    <!--轮播-->
    <el-carousel width="1020px" height="360px">
      <el-carousel-item v-for="item in carouselFigures" :key="item">
        <el-image :src="item.url" alt="" fit="fill" style="width: 1020px;height: 360px"/>
      </el-carousel-item>
    </el-carousel>
  </div>
  <!--新品+排行榜-->
  <div class="new-rank w1230 clear-float">
    <a href="#" class="new-img"><img src="@/assets/img/new.jpg" width="267px" height="400px"/></a>
    <!--新品-->
    <div class="new">
      <h3 class="title">新品</h3>
      <div class="new-list">
        <ul v-for="product in productNews">
          <li>
            <a href="#">
              <img :src="product.productImage" alt="" width="90px" height="90px"/>
              <p>{{ product.productName }}</p>
            </a>
          </li>
        </ul>
      </div>
    </div>
    <!--排行榜-->
    <div class="rank">
      <h3 class="title">排行榜</h3>
      <div class="rank-list">
        <ul v-for="product in productSales">
          <li>
            <a href="#">
              <img :src="product.productImage" alt="" width="90px" height="90px"/>
              <p>{{ product.productName }}</p>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <!--侧导航栏-->
  <div class="nav-side">
    <ul>
      <li v-for="type in allCategoryProduct" :key="type.id">
        <a :href="'#' + type.id" class="nav-button">{{ type.typeName }}</a>
      </li><li><a href="#top" class="nav-top">返回顶部</a></li>
    </ul>

  </div>
  <!--分类商品-->
  <div v-for="type in allCategoryProduct" :key="type.id">
    <div class="foot w1230" :id="type.id">
      <h3 class="h-title">{{ type.typeName }}</h3>
      <div class="global-list">
        <ul class="clear-float">
          <li v-for="product in type.product" :key="product.id" class="last">
            <a href="#">
              <p>{{ product.productName }}</p>
              <img :src="product.productImage" alt="" width="150px" height="150px"/>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>

  <Footer/>
</template>

<style scoped>
.icon-center {
  display: inline-flex;
  align-items: center;
}

.h-title {
  color: #B41E23;
}

.h-title:before {
  background-color: #B41E23;
}

.foot {
  background-color: #fff;
  margin-bottom: 20px;
}
.nav-side {
  width: 50px;
  position: fixed;
  bottom: 30px;
  right: 8px;
}
.nav-button {
  background-color: #c95c5c;
  border-radius: 10px;
}
.nav-top{
  border-radius: 10px;
}
.nav-side li a {
  display: block;
  width: 24px;
  height: 36px;
  padding: 8px;
  margin: 4px;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
}
.banner-btn {
  width: auto;
}
</style>
