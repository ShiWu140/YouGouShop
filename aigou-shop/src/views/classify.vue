<script>
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";

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
    loadingProduct() {
      this.tiaojian.page = this.page
      this.tiaojian.size = this.size
      this.tiaojian.name = this.name

      if (this.names.includes(this.name)) {

      } else {
        this.names.unshift(this.name)
        if (this.names.length >= 8) {
          this.names.pop()
        }
      }

      this.tiaojian.productType = this.productType
      if (this.brands.length === 0) {
        this.tiaojian.brands = []
      } else {
        this.tiaojian.brands = this.brands
      }

      this.tiaojian.minP = this.minP
      this.tiaojian.maxP = this.maxP
      this.tiaojian.status = this.status
      this.$http.post("/product/pageH", this.tiaojian).then((response) => {
        console.log('分页数据', response.data)
        if (response.data.code === 1) {
          this.products = response.data.data.records;
          if (response.data.data.records.length === 0) {
            this.msg = "当前没有数据"
          } else {
            this.msg = ''
          }
        } else {

        }
      })
    },
    //状态
    statusProduct(status) {
      this.status = status
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
    brandsLoading() {
      this.$http.get("/brand/all").then((response) => {
        if (response.data.code === 1) {
          console.log("品牌数据列表", response.data.data);
          this.brandHH = response.data.data;
        } else {

        }
      })
    },
    //分类列表
    productTypesLoading() {
      this.$http.get("/productType/all").then((response) => {
        if (response.data.code === 1) {
          console.log("分类列表", response.data.data);
          this.productTypes = response.data.data;
        } else {

        }
      })
    },
    //点击分类
    productTypeClick(typeId, typeName) {
      console.log('点击分类-数据', typeId)
      this.productType = typeId;
      this.product.productName = typeName
      this.loadingProduct();
    },
    //点击品牌
    brandsClick() {
      console.log("点击品牌-数据", this.brands)
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
        alert("请输入15个字符以内")
      }
    },
    //回车键
    handleSubmit(item) {
      console.log('回车键被按下');
      this.nameClick(item)
    }

  },
  mounted() {
    this.loadingProduct();
    this.brandsLoading();
    this.productTypesLoading()
  }
}

/*
import {onMounted, ref} from 'vue';
import axios from "axios";

const products = ref([])
const product = {
  id: '',
  productName: '',
  productImage: '',
  price: '',
  productType: '',
  productDesc: '',
  createTime: '',
  productBrand: '',
  salesNum: ''
}

const fetchProducts = async () => {
  try {
    const response = await axios.get('/product/pageH?page=1&size=10');
    console.log('响应Response11111111111:', response.data); // 调试日志
    // if (response.data) {
    console.log('this.products:', response.data.data.records);
      products.value=response.data.data.records;
      console.log('this.products:11111111', products.value);
    // }
  }catch (e) {
    console.log(e)
  }
}
onMounted(() => {
  fetchProducts()
});
*/

</script>
<template>
  <Header/>
  <Search/>
  <!--导航栏-->
  <div class="nav">
    <div class="w1230">
      <div id="all-goods">
        <span class="all-goods" @click="productTypeClick(null)">所有商品分类</span>
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
  <!--面包屑导航栏-->
  <div class="crumbSlide w1230">
    <i class="fa fa-th-large classify-icon"></i>
    <span>{{ this.product.productName }}</span>
    <i class="fa fa-angle-right"></i>
  </div>
  <!--相关分类（品牌）-->
  <div class="classify-brand w1230 clear-float">
    <div class="brand-title">品牌</div>
    <div class="brands">
      <ul class="clear-float">
        <li v-for="item in brandHH">
          <input type="checkbox" class="chk" :id="item.id" v-model="this.brands" :value="item.id"/>
          <label :for="item.id">
            <!--            <img src="@/assets/img/liangpin.jpg" width="50" height="50px"/><br/>{{item.brandName}}-->
            <img :src="item.brandImg" width="50" height="50px"/><br/>{{ item.brandName }}
            <img src="../assets/img/choose.png" class="choose"/>
          </label>
        </li>
      </ul>
      <div class="brands-btn">
        <a href="#" class="brands-sure" @click="brandsClick()">确定</a>
        <a href="javascript:void(0)" class="brands-cancel" id="brands-cancel" @click="cancelbrandsClick()">重置</a>
      </div>
    </div>
  </div>
  <!--综合分类-->
  <div class="search-select w1230">
    <ul>
      <li><a href="#" @click="statusProduct(0)">综合</a></li>
      <li><a href="#" @click="statusProduct(this.status==1?2:1)">销量</a></li>
      <li><a href="#" @click="statusProduct(this.status==3?4:3)">新品</a></li>
      <li>
        <a href="javascript:void(0)">
          <span>价格</span>
          <input type="number" placeholder="￥" min="0" class="price" v-model="this.minP"/> -
          <input type="number" placeholder="￥" min="0" class="price" v-model="this.maxP"/>
        </a>
        <a href="#" class="price-sure" @click="priceProduct()">确定</a>
        <a href="javascript:void(0)" class="price-cancel" id="price-cancel" @click="priceNullProduct()">清空</a>
      </li>

    </ul>
  </div>
  <!--商品展示-->
  <div v-if="msg!=''" style="margin: 100px auto;width: 100px;height: 150px;">{{ msg }}</div>
  <div class="goods-show w1230">
    <ul class="clear-float">
      <li v-for="(item, index) in products" :key="item.id">
        <a :href="'/goodsDetail?id=' + item.id">
          <div class="g-img">
            <img :src="item.productImage" alt="" width="230px" height="230px"/>
          </div>
          <p class="g-title">{{ item.productName }}</p>
          <span class="g-price">￥{{ item.price }}</span>
          <span class="g-num">销量:{{ item.salesNum }}</span>
        </a>
      </li>
    </ul>
  </div>

  <!--脚注-->
  <div class="footer">
    <p class="w1230">
      <a href="#">关于爱购</a>
      <a href="#">合作伙伴</a>
      <a href="#">营销中心</a>
      <a href="#">廉正举报</a>
      <a href="#">联系客服</a>
      <a href="#">开发平台</a>
      <a href="#">诚征英才</a>
      <a href="#">联系我们</a>
      <a href="#">网站地图</a>
      <a href="#">知识产权</a><span>|</span>
      <span>&copy;2018-2020 igo.com 版权所有</span>
    </p>
  </div>
</template>
<style scoped>
@import "@/assets/css/classify.css";

.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}
</style>
<!--
<template>
  <div>
    <div class="mt-4">
      <el-input
          v-model="input3"
          placeholder="Please input"
          class="input-with-select"
      >
        <template #prepend>
          <el-select v-model="select" placeholder="Select" style="width: 115px">
            <el-option label="Restaurant" value="1"/>
            <el-option label="Order No." value="2"/>
            <el-option label="Tel" value="3"/>
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search"/>
        </template>
      </el-input>
    </div>

    <el-row>
      <el-col
          v-for="(item, index) in records"
          :key="item"
          :span="8"
          :offset="index > 0 ? 2 : 0"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img
              :src=item.productImage
              class="image"
          />
          <div style="padding: 14px">
            <span>{{item.productName}}</span>
            <div>{{item.price}}</div>
            <div class="bottom">
              <time class="time">{{ currentDate }}</time>
              <el-button text class="button">Operating</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>
<script setup lang="ts">
import {ref} from 'vue'
import {Search} from '@element-plus/icons-vue'

const input3 = ref('')
const select = ref('')

const products = []
const product = {
  id: '',
  productName: '',
  productImage: '',
  price: '',
  productType: '',
  productDesc: '',
  createTime: '',
  productBrand: '',
  salesNum: ''
}

const records=[
  {
    "id": "55fd5fb1ceb24f00a1dc55a5f1e1830c",
    "productName": "平凡的世界(共3册)全三册3册 完整版 路遥原著 全套全集 茅盾文学奖经典文学小说书籍 畅销书排行榜 八年级下册书籍",
    "productImage": "78e66ef1eea14f7ea18c7212f113d839.jpg",
    "price": 47.5,
    "productType": "758afbf0a4d1474e8b53043c6697a9f7",
    "productDesc": "754b2baf653748af99522f8483053196.jpg",
    "createTime": "2018-09-19T11:00:44.000+00:00",
    "productBrand": "e4f4a6cc046741268edc0279007a50e7",
    "salesNum": 0
  },
  {
    "id": "9de787791f0f44fa9b6b344368f14c17",
    "productName": "朗读者董卿正版书籍全套(1-3辑)全3册现当代文学随笔中国诗词大会见字如面平凡的世界畅销书籍排行榜正版",
    "productImage": "797395b3a4424b408688e7131dbbe0d2.jpg",
    "price": 88.0,
    "productType": "758afbf0a4d1474e8b53043c6697a9f7",
    "productDesc": "afa8d1788f3b48c28d521891184a4300.jpg",
    "createTime": "2018-09-19T10:59:59.000+00:00",
    "productBrand": "e4f4a6cc046741268edc0279007a50e7",
    "salesNum": 0
  },
  {
    "id": "dd31d79d9b2f4524be351934e6adb7f3",
    "productName": "龙应台人生三书：目送+亲爱的安德烈+孩子你慢慢来 全3册经典套装 龙应台的书籍 畅销",
    "productImage": "2c76927bbdce47fbbc19e48812561984.jpg",
    "price": 59.8,
    "productType": "758afbf0a4d1474e8b53043c6697a9f7",
    "productDesc": "f374288be4be4dd9a9275bedffac5da0.jpg",
    "createTime": "2018-09-19T10:59:35.000+00:00",
    "productBrand": "e4f4a6cc046741268edc0279007a50e7",
    "salesNum": 0
  },
  {
    "id": "8d0860d8aaea486b9448067f73963cf9",
    "productName": "正版包邮 HTML5权威指南 弗里曼 html5+css3 从入门到精通  网页源码 web应用开发 ",
    "productImage": "7bd2902c8d8e4fbab36af9db946d866b.jpg",
    "price": 87.0,
    "productType": "758afbf0a4d1474e8b53043c6697a9f7",
    "productDesc": "3a8fdc4931b749c0b304594689474039.jpg",
    "createTime": "2018-09-19T11:01:09.000+00:00",
    "productBrand": "e4f4a6cc046741268edc0279007a50e7",
    "salesNum": 45
  }
]

const currentDate = ref(new Date())

</script>

<style>
.input-with-select .el-input-group__prepend {
  background-color: var(&#45;&#45;el-fill-color-blank);
}

.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}
</style>-->
