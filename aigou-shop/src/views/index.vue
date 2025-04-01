<script setup>
import {onMounted, ref} from 'vue';
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";
import Footer from "@/components/Footer.vue";
import { carouselApi, productTypeApi, productApi } from '@/api';

const carouselFigures = ref([]);
const productTypes = ref([]);
const productNews = ref([]);
const newProductImage = ref('');
const productSales = ref([]);
const allCategoryProduct = ref([]);

const fetchData = async () => {
  // 获取轮播图数据
  carouselFigures.value = await carouselApi.getAllCarouselFigures();
  
  // 获取商品类型数据
  productTypes.value = await productTypeApi.getAllProductTypes();
  
  // 获取新品数据
  productNews.value = await productApi.getNewProducts();
  if (productNews.value.length > 0) {
    newProductImage.value = productNews.value[0].productImage;
  }
  
  // 获取销量排行数据
  productSales.value = await productApi.getProductSales();
  
  // 获取所有分类商品数据
  allCategoryProduct.value = await productApi.getAllCategoryProduct();
};

onMounted(() => {
  fetchData();
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
            <a :href="'/classify?category=' + type.productTypeName" class="icon-center">
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
    <a href="#" class="new-img">
      <el-image 
        :src="newProductImage || '@/assets/img/new.jpg'" 
        fit="cover" 
        style="width: 267px; height: 400px"
      />
    </a>
    <!--新品-->
    <div class="new">
      <h3 class="title">新品</h3>
      <div class="new-list">
        <ul v-for="product in productNews">
          <li>
            <a :href="'/goodsDetail?id=' + product.id">
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
        <ul>
          <li v-for="(product, index) in productSales" :key="product.id" :data-rank="index + 1">
            <a :href="'/goodsDetail?id=' + product.id">
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
      </li>
      <li><a href="#top" class="nav-top">返回顶部</a></li>
    </ul>

  </div>
  <!--分类商品-->
  <div v-for="type in allCategoryProduct" :key="type.id">
    <div class="foot w1230" :id="type.id">
      <h3 class="h-title">{{ type.typeName }}</h3>
      <div class="global-list">
        <ul class="clear-float">
          <li v-for="product in type.product" :key="product.id" class="last">
            <a :href="'/goodsDetail?id=' + product.id">
              <p class="product-name">{{ product.productName }}</p>
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

.classify {
  height: 360px;
}

.h-title {
  color: #B41E23;
}

.h-title:before {
  background-color: #B41E23;
}

.global-list ul {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 20px;
}

.global-list li {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.global-list li a {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  color: inherit;
}

.global-list li:hover {
  transform: translateY(-5px);
}

.global-list li img {
  width: 150px;
  height: 150px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.global-list li:hover img {
  transform: scale(1.1);
}

.product-name {
  width: 150px;
  margin-bottom: 10px;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 14px;
  color: #333;
}

.foot {
  background-color: #fff;
  margin-bottom: 20px;
}

.nav-side {
  width: 50px;
  position: fixed;
  bottom: 60px;
  right: 10px;
}

.nav-button {
  background-color: #c95c5c;
  border-radius: 10px;
}

.nav-top {
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

.rank-list li,.new-list li{
	height: 130px;
	float: left;
	text-align: center;
	margin-top: 10px;
	padding: 20px 5px 10px;
}
.new-list li{
	width: 140px;
	border: 1px solid #eee;
	margin: 10px 0 0 11px;
}
.rank-list li{
	width: 100px;
	border: 1px solid #eee;
	margin: 10px 0 0 11px;
	position: relative;
}
.rank-list li:nth-child(1)::before {
	content: "1";
	position: absolute;
	top: 5px;
	left: 5px;
	width: 20px;
	height: 20px;
	background-color: #FFD700;
	color: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
	font-size: 12px;
}
.rank-list li:nth-child(2)::before {
	content: "2";
	position: absolute;
	top: 5px;
	left: 5px;
	width: 20px;
	height: 20px;
	background-color: #C0C0C0;
	color: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
	font-size: 12px;
}
.rank-list li:nth-child(3)::before {
	content: "3";
	position: absolute;
	top: 5px;
	left: 5px;
	width: 20px;
	height: 20px;
	background-color: #CD7F32;
	color: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
	font-size: 12px;
}
.rank-list li:nth-child(n+4)::before {
	content: attr(data-rank);
	position: absolute;
	top: 5px;
	left: 5px;
	width: 20px;
	height: 20px;
	background-color: #666;
	color: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
	font-size: 12px;
}
.new-list li p,.rank-list li p{
	font-size: 14px;
	color: #333;
	line-height: 32px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.rank-list li img{
	transition: transform .5s;
}
.rank-list li:hover img{
	transform: scale(1.1);
}
</style>
