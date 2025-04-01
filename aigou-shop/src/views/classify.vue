<script>
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";
import { classifyApi } from '@/api/classify';

export default {
  components: {Search, Header},
  data() {
    return {
      products: [],
      product: {
        id: '',
        productName: '',
        productImage: '',
        price: '',
        productType: '',
        productDesc: '',
        createTime: '',
        productBrand: '',
        salesNum: ''
      },
      tiaojian: {
        page: '',
        size: '',
        name: '',
        productType: '',
        brands: '',
        minP: '',
        maxP: '',
        status: ''
      },
      page: 1,
      size: 50,
      name: '',
      names: [],
      productType: null,
      productTypes: [],
      brands: [],
      brandHH: [],
      minP: null,
      maxP: null,
      status: 0,
      brand: {
        id: '',
        brandName: '',
        brandType: '',
        brandImg: ''
      },
      msg: ''
    }
  },
  methods: {
    async loadingProduct() {
      this.tiaojian.page = this.page;
      this.tiaojian.size = this.size;
      this.tiaojian.name = this.name;

      if (!this.names.includes(this.name)) {
        this.names.unshift(this.name);
        if (this.names.length >= 8) {
          this.names.pop();
        }
      }

      this.tiaojian.productType = this.productType;
      this.tiaojian.brands = this.brands.length === 0 ? [] : this.brands;
      this.tiaojian.minP = this.minP;
      this.tiaojian.maxP = this.maxP;
      this.tiaojian.status = this.status;

      const result = await classifyApi.getPageProducts(this.tiaojian);
      this.products = result.records;
      this.msg = result.hasData ? '' : "当前没有数据";
    },
    //状态
    statusProduct(status) {
      this.status = status;
      this.loadingProduct();
    },
    //价格
    priceProduct() {
      this.loadingProduct();
    },
    priceNullProduct() {
      this.maxP = '';
      this.minP = '';
      this.loadingProduct();
    },
    //品牌数据列表
    async brandsLoading() {
      this.brandHH = await classifyApi.getAllBrands();
    },
    //分类列表
    async productTypesLoading() {
      this.productTypes = await classifyApi.getAllProductTypes();
    },
    //点击分类
    async productTypeClick(typeId, typeName) {
      if (this.productType !== typeId) {
        this.productType = typeId;
        this.page = 1;
        this.name = '';
      } else {
        this.page = 1;
      }
      await this.loadingProduct();
    },
    //点击品牌
    brandsClick() {
      this.loadingProduct();
    },
    //重置按钮
    cancelbrandsClick() {
      this.brands = [];
      this.loadingProduct();
    },
    //点击历史搜索
    nameClick(item) {
      this.name = item;
      this.loadingProduct();
      this.name = null;
    },
    //失去焦点
    handleFocus() {
      if (this.name.length >= 15) {
        alert("请输入15个字符以内");
      }
    },
    //回车键
    handleSubmit(item) {
      this.nameClick(item);
    }
  },
  async mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');
    
    await this.productTypesLoading();
    await this.brandsLoading();
    
    if (category) {
      const type = this.productTypes.find(item => item.productTypeName === category);
      if (type) {
        this.productType = type.id;
      }
    }
    
    await this.loadingProduct();
  }
}
</script>

<template>
  <!--头部-->
  <div class="top" id="top">
    <Header/>
    <!--logo+搜索-->
    <div class="top-header w1230 clear-float">
      <a href="/" target="_blank" class="logo">
        <el-image 
          src="@/assets/img/logo.png"
          style="width: 100px; height: 40px"
        />
      </a>
      <div class="top-header-right">
        <!--搜索框-->
        <div class="search clear-float">
          <input 
            type="text" 
            placeholder="优购网-专业的综合网上购物商城" 
            class="search-txt" 
            v-model="name"
            @keyup.enter="handleSubmit(name)" 
            @blur="handleFocus"
          />
          <a href="#" class="search-btn" @click="loadingProduct()">搜索</a>
        </div>
        <!--热搜-->
        <p class="hotkey">
          <a href="#" style="display: inline-block;" v-for="item in names" @click="nameClick(item)">{{ item }}</a>
        </p>
      </div>
    </div>
  </div>
  <!--导航栏-->
  <div class="nav">
    <div class="w1230">
      <div id="all-goods">
        <span class="all-goods" @click="productTypeClick(null)">所有商品分类</span>
        <div class="nav-er" id="nav-er">
          <ul>
            <li v-for="type in productTypes" :key="type.id" :class="{ active: productType === type.id }"
                @click="productTypeClick(type.id, type.productTypeName)">
              <h3>
                <a href="#" class="icon-center">
                  <el-icon :size="20">
                    <component :is="type.productTypeIcon"></component>
                  </el-icon>
                  {{ type.productTypeName }}
                </a>
              </h3>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!--面包屑导航栏-->
  <div class="crumbSlide w1230">
    <i class="fa fa-th-large classify-icon"></i>
    <span>{{ product.productName }}</span>
    <i class="fa fa-angle-right"></i>
  </div>
  <!--相关分类（品牌）-->
  <div class="classify-brand w1230 clear-float">
    <div class="brand-title">品牌</div>
    <div class="brands">
      <ul class="clear-float">
        <li v-for="item in brandHH" :key="item.id">
          <input 
            type="checkbox" 
            class="chk" 
            :id="item.id" 
            v-model="brands" 
            :value="item.id"
          />
          <label :for="item.id">
            <el-image 
              :src="item.brandImg" 
              style="width: 100px; height: 40px"
              fit="contain"
            />
          </label>
        </li>
      </ul>
    </div>
  </div>
  <!--商品列表-->
  <div class="product-list w1230">
    <div class="product-list-header">
      <div class="product-list-title">
        <span>商品列表</span>
        <span class="product-count">共 {{ products.length }} 个商品</span>
      </div>
      <div class="product-list-sort">
        <span 
          :class="{ active: status === 0 }" 
          @click="statusProduct(0)"
        >默认排序</span>
        <span 
          :class="{ active: status === 1 }" 
          @click="statusProduct(1)"
        >价格从低到高</span>
        <span 
          :class="{ active: status === 2 }" 
          @click="statusProduct(2)"
        >价格从高到低</span>
      </div>
    </div>
    <div class="product-list-content">
      <div v-if="msg" class="no-data">{{ msg }}</div>
      <div v-else class="product-grid">
        <div v-for="product in products" :key="product.id" class="product-item">
          <el-image 
            :src="product.productImage" 
            style="width: 200px; height: 200px"
            fit="cover"
          />
          <div class="product-info">
            <h3 class="product-name">{{ product.productName }}</h3>
            <p class="product-price">￥{{ product.price }}</p>
            <p class="product-desc">{{ product.productDesc }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/classify.css";

.product-list {
  margin-top: 20px;
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.product-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.product-list-title {
  font-size: 18px;
  font-weight: bold;
}

.product-count {
  margin-left: 10px;
  color: #999;
  font-size: 14px;
}

.product-list-sort {
  display: flex;
  gap: 20px;
}

.product-list-sort span {
  cursor: pointer;
  color: #666;
  padding: 5px 10px;
  border-radius: 4px;
}

.product-list-sort span.active {
  color: #409EFF;
  background: #ecf5ff;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  padding: 20px 0;
}

.product-item {
  border: 1px solid #eee;
  border-radius: 4px;
  overflow: hidden;
  transition: all 0.3s;
}

.product-item:hover {
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  transform: translateY(-5px);
}

.product-info {
  padding: 10px;
}

.product-name {
  font-size: 16px;
  margin: 0 0 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
  margin: 0 0 5px 0;
}

.product-desc {
  color: #999;
  font-size: 14px;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.no-data {
  text-align: center;
  padding: 40px;
  color: #999;
  font-size: 16px;
}
</style>
