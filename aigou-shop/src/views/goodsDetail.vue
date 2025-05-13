<script setup>
import Header from "@/components/Header.vue";
import Search from "@/components/Search.vue";
import Footer from "@/components/Footer.vue";
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import {ElMessage} from "element-plus";
import {goodsApi} from "@/api/goods";

const route = useRoute();
const productTypes = ref([]);
const goodsDetail = ref({});
const sameType = ref([]);
const quantity = ref(1);

const loadData = async () => {
  try {
    const [types, detail, similar] = await Promise.all([
      goodsApi.fetchProductTypes(),
      goodsApi.fetchGoodsDetail(route.query.id),
      goodsApi.fetchRandomProductsByType(route.query.id)
    ]);
    productTypes.value = types;
    goodsDetail.value = detail;
    sameType.value = similar;
  } catch (error) {
    console.log(productTypes.value)
    console.log(goodsDetail.value)
    console.log(sameType.value)
    ElMessage.error('数据加载失败，请刷新页面重试');
  }
};

const handleAddToCart = async () => {
  try {
    await goodsApi.addToCart(localStorage.getItem('userId'), route.query.id, quantity.value);
    ElMessage.success('添加购物车成功！');
  } catch (error) {
    ElMessage.error('添加失败，请重试');
  }
};

const increment = () => {
  quantity.value++;
};

const decrement = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

onMounted(() => {
  loadData();
});
</script>


<template>
  <Header/>
  <Search/>

  <!-- 导航栏 -->
  <div class="nav">
    <div class="w1230">
      <div id="all-goods">
        <span class="all-goods">所有商品分类</span>
        <div class="nav-er" id="nav-er">
          <ul>
            <li v-for="type in productTypes" :key="type.id">
              <h3>
                <a :href="'/classify?category=' + type.productTypeName" class="icon-center">
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

  <!-- 商品详情 -->
  <div class="w1230 clear-float goods-main">
    <div class="goods-content">
            <div class="big-img">
              <el-image
                :src="goodsDetail.productImage"
                fit="contain"
                style="width: 360px; height: 360px"
                :preview-src-list="[goodsDetail.productImage]"
              />
            </div>

      <div class="goods-detail">
        <h3 class="goods-title">{{ goodsDetail.productName }}</h3>
        <p class="price">
          价格
          <span class="price-value">￥{{ goodsDetail.price }}</span>
        </p>
        <p class="store-num">
          销量：<span>{{ goodsDetail.salasNum }}件</span>
        </p>

        <div class="update-num">
          <div class="quantity">
            <el-button
                :disabled="quantity <= 1"
                @click="decrement"
                circle
                size="large"
                class="quantity-btn"
            >-
            </el-button>
            <el-input-number
                v-model="quantity"
                :min="1"
                size="large"
                controls-position="right"
                class="quantity-input"
            />
            <el-button
                @click="increment"
                circle
                size="large"
                class="quantity-btn"
            >+
            </el-button>
          </div>

          <el-button
              type="primary"
              @click="handleAddToCart"
              size="large"
              class="add-cart-btn"
          >
            加入购物车
          </el-button>
        </div>
      </div>
    </div>

    <!-- 店家承诺 -->
    <div class="promise">
      <h3>
        <el-icon>
          <Check/>
        </el-icon>
        优购商城承诺
      </h3>
      <p>优购平台上的商品均由卖家自行销售并发货，卖家负责提供发票及相关售后服务，请您放心购买！
        温馨提示：由于厂家可能会对产品包装、产地或配件进行调整，优购平台无法保证您收到的商品与商城展示的图片、产地及配件说明完全一致。但我们承诺所售商品均为原厂正品，并与市场主流新品同步。如有变更，敬请谅解！</p>
    </div>
  </div>

  <!-- 商品介绍和推荐 -->
  <div class="w1230 clear-float content-section">
    <!-- 商品介绍 -->
    <div class="goods-des">
      <h3 class="goods-tro">商品详情</h3>
      <div class="goods-info goods-images">
        <div v-if="goodsDetail.productDesc">
          <el-image
            v-for="(image, index) in goodsDetail.productDesc.split(',')"
            :key="index"
            :src="image"
            fit="contain"
            :preview-src-list="goodsDetail.productDesc.split(',')"
            style="display: block; width: 100%; margin: 0; border-radius: 0; box-shadow: none;"
          />
        </div>
        <div v-else>
          暂无商品图片
        </div>
      </div>
    </div>

    <!-- 商品推荐 -->
    <div class="recommend goods-show">
      <h3>相关推荐</h3>
      <div class="recommend-grid">
        <div v-for="product in sameType" :key="product.id" class="recommend-item">
          <a :href="`/goodsDetail?id=${product.id}`">
            <el-image
                :src="product.productImage"
                fit="contain"
                style="width: 180px; height: 180px"
            />
            <p class="g-title">{{ product.productName }}</p>
            <div class="g-info">
              <span class="g-price">￥{{ product.price }}</span>
              <span class="g-num">销量: {{ product.salasNum }}</span>
            </div>
          </a>
        </div>
      </div>
    </div>
  </div>

  <Footer/>
</template>

<style scoped>
@import "@/assets/css/goodsDetail.css";

.goods-main {
  margin-top: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  position: relative;
  display: flex;
  gap: 20px;
}

.goods-content {
  flex: 1;
  display: flex;
  gap: 20px;
}

.big-img {
  flex-shrink: 0;
}

.goods-detail {
  flex: 1;
  padding: 20px;
}

.goods-title {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.price {
  font-size: 16px;
  color: #666;
}

.price-value {
  color: #f56c6c;
  font-size: 24px;
  font-weight: bold;
}

.update-num {
  display: flex;
  align-items: center;
  gap: 30px;
  margin-top: 30px;
}

.quantity {
  display: flex;
  align-items: center;
  gap: 15px;
}

.quantity-btn {
  font-size: 18px;
  width: 40px;
  height: 40px;
}

.quantity-input {
  width: 120px;
}

.quantity-input :deep(.el-input-number__decrease),
.quantity-input :deep(.el-input-number__increase) {
  width: 32px;
  height: 32px;
}

.quantity-input :deep(.el-input__inner) {
  font-size: 16px;
  height: 40px;
  line-height: 40px;
}

.add-cart-btn {
  height: 45px;
  padding: 0 35px;
  font-size: 16px;
  border-radius: 23px;
}

.add-cart-btn :deep(.el-icon) {
  font-size: 18px;
  margin-right: 8px;
}

.content-section {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.goods-des {
  flex: 1;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.recommend {
  flex: 0 0 220px; /* 固定宽度，不伸缩 */
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.recommend-grid {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.recommend-item {
  background: #fff;
  border-radius: 8px;
  transition: transform 0.3s;
  border: 1px solid #eee;
  padding: 0;
  width: 230px;
  margin: 0 auto;
}

.recommend-item a {
  display: block;
  padding: 10px;
  text-decoration: none;
}

.recommend-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #409eff;
}

.recommend-item .el-image {
  width: 140px;
  height: 160px;
  display: block;
  margin: 0 auto;
}

.g-title {
  margin: 10px 0;
  font-size: 14px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  height: 40px;
  text-align: left; /* 标题左对齐 */
}

.g-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  padding: 0 5px;
}

.g-price {
  color: #f56c6c;
  font-weight: bold;
  font-size: 16px;
}

.g-num {
  color: #999;
  font-size: 15px;
  white-space: nowrap;
  flex-shrink: 0;
}

.promise {
  position: sticky;
  top: 20px;
  width: 150px;
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  height: fit-content;
  flex-shrink: 0;
}

.promise h3 {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #409eff;
  margin-bottom: 10px;
  font-size: 16px;
}

.promise p {
  font-size: 14px;
  line-height: 1.6;
  color: #666;
}

.goods-tro {
  font-size: 20px;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.goods-info,
.goods-images {
  padding: 0;
  margin: 0;
}
.goods-images .el-image {
  display: block;
  width: 100%;
  margin: 0;
  border-radius: 0;
  box-shadow: none;
}
</style>
