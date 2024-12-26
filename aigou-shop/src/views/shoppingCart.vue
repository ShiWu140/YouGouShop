<script lang="js" setup>
import {ElMessage, ElTable} from 'element-plus';
import {onMounted, ref} from 'vue';
import axios from "axios";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
const userId = localStorage.getItem('userId')
// 存储购物车数据
const tableData = ref([])
// 存储收货地址数据
const addressData = ref({});
// 地址列表
const addressList = ref([]);
// 控制弹窗显示
const dialogVisible = ref(false);
// 选中商品
const multipleTableRef = ref(null)
const multipleSelection = ref([])
// 全选
const toggleSelection = (rows, ignoreSelectable) => {
  if (rows) {
    rows.forEach((row) => {
      multipleTableRef.value.toggleRowSelection(
          row,
          undefined,
          ignoreSelectable
      );
    });
  } else {
    multipleTableRef.value.clearSelection();
  }
};
const clearSelection = () => {
  // 调用 clearSelection 清除所有选中的项
  if (multipleTableRef.value) {
    multipleTableRef.value.clearSelection();
  }
};
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}
// 商品数量更改
const handleChange = (row) => {
  console.log('商品数量更改为：', row.productNum);
};
// 删除商品
const handleClick = (row) => {
  tableData.value = tableData.value.filter(item => item !== row);
  console.log('删除商品：', row);
};
// 批量删除选中商品
const batchDelete = () => {
  if (multipleSelection.value.length) {
    tableData.value = tableData.value.filter(item => !multipleSelection.value.includes(item));
    clearSelection(); // 删除后清空选中状态
    console.log('批量删除商品：', multipleSelection.value);
  }
};
// 提取省市信息
const extractProvinceOrCity = (address) => {
  const regex = /^(.*?[省市])/; // 匹配以“省”或“市”结尾的部分
  const match = address.match(regex);
  return match ? match[1] : '';
};
// 获取购物车数据
const fetchProducts = async () => {
  try {
    const response = await axios.get('/shopCart/getProductsByUserId?userId=' + userId);
    console.log('API Response:', response.data); // 调试日志
    if (response.data.code === 1) {
      // 接收产品列表
      const products = response.data.data || [];
      tableData.value = products.map(product => ({
        productId: product.productId,
        productImage: product.productImage,
        productPrice: product.productPrice,
        productNum: product.productNum,
      }));
      console.log("购物车返回数据为：", tableData.value);
    } else {
      console.error('获取购物车数据失败:', response.data.msg);
    }
  } catch (error) {
    console.error('获取购物车数据失败:', error);
  }
};

// 获取收货地址数据
const fetchAddress = async () => {
  try {
    const response = await axios.get(`/receivingAddress/getReceivingAddressByUserId?userId=${userId}`);
    console.log('API 请求:', response.data); // 调试日志
    if (response.data.code === 1) {
      response.data.data.filter((item) => {
        if (item.isDefault) {
          addressData.value = item; // 设置地址数据
          console.log("默认收货地址数据为：", addressData)

        }
      })
      console.log("切换收货地址数据为：", addressData)
      addressList.value.length = 0;
      addressList.value = response.data.data
    } else {
      console.error('获取收获地址失败:', response.data.msg);
    }
  } catch (error) {
    console.error('获取收获地址失败:', error);
  }
};

// 提交订单逻辑
const submitForm = async () => {
  if (!multipleSelection.value.length) {
    ElMessage.warning("请先选择商品再结算！");
    return;
  }
  if (!addressData.value.id) {
    ElMessage.warning("请先设置收货地址！");
    return;
  }

  // 准备提交的数据
  const orderData = {
    id: "",
    userId: userId,
    createTime: "",
    state: 0,
    receivingAddress: addressData.value.id, // 收货地址 ID
    products: multipleSelection.value.map(item => ({
      productId: item.productId, // 假设你的商品数据包含 `id` 字段
      productNum: item.productNum,
    })),
  };

  try {
    // 调用后端接口
    console.log("准备提交的数据:", orderData);
    const response = await axios.post('/order/add', orderData);
    console.log(response.data.data)
    if (response.data.code === 1) {
      ElMessage.success("订单提交成功！");
      console.log("订单 ID:", response.data.data);
      // 清空选中项
      clearSelection();
    } else {
      ElMessage.error(`订单提交失败：${response.data.msg}`);
    }
  } catch (error) {
    console.error("订单提交失败：", error);
    ElMessage.error("订单提交失败，请稍后重试！");
  }
};

// 切换地址
const selectAddress = (address) => {
  addressData.value = address
  // 关闭弹窗
  dialogVisible.value = false;
};
// 切换弹窗
const toggleDialog = () => {
  dialogVisible.value = true;
  // 弹窗打开时获取地址列表
  fetchAddress();
};
// 页面加载时执行数据获取
onMounted(() => {
  fetchProducts();
  fetchAddress();
})
</script>

<template>
  <Header/>
  <div class="w1230">
    <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>
    <span class="cart">购物车</span>
  </div>

  <!--收货地址-->
  <div class="w1230 address">
    <el-descriptions border>
      <el-descriptions-item label="姓名">{{ addressData.receivingPerson }}</el-descriptions-item>
      <el-descriptions-item label="电话号码">{{ addressData.mobilePhone }}</el-descriptions-item>
      <el-descriptions-item label="省市">
        {{ addressData.receivingAddress ? extractProvinceOrCity(addressData.receivingAddress) : '' }}
      </el-descriptions-item>
      <el-descriptions-item label="地址">
        {{ addressData.receivingAddress || '暂无地址' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-button type="primary" size="small" @click="toggleDialog">切换地址</el-button>
        </template>
      </el-descriptions-item>
    </el-descriptions>
    <!--切换地址弹窗-->
    <el-dialog title="切换收货地址" v-model="dialogVisible" width="50%">
      <el-table :data="addressList" style="width: 100%">
        <el-table-column prop="receivingPerson" label="姓名" width="120"></el-table-column>
        <el-table-column prop="mobilePhone" label="电话" width="150"></el-table-column>
        <el-table-column prop="receivingAddress" label="地址"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="primary" @click="selectAddress(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
      </template>
    </el-dialog>
  </div>

  <!--购物车展示-->
  <div class="w1230 shopping">
    <!--标题栏-->

    <!--详细展示-->
    <div class="shopping-list">
      <el-table ref="multipleTableRef" :data="tableData" @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="商品信息">
          <template #default="scope">
            <el-image :src="scope.row.productImage" style="width: 50px; height: 50px;"></el-image>
          </template>
        </el-table-column>
        <el-table-column property="productPrice" label="单价"/>
        <el-table-column property="productNum" label="数量">
          <template #default="scope">
            <el-input-number
                v-model="scope.row.productNum"
                :min="1"
                :max="999"
                size="small"
                @change="handleChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="小计(元)">
          <template #default="scope">
            {{ scope.row.productPrice * scope.row.productNum }}
          </template>
        </el-table-column>
        <el-table-column label="选择">
          <template #default="scope">
            <el-button link type="primary" @click="handleClick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 2px">
        <el-button @click="toggleSelection(tableData)">全选</el-button>
        <el-button @click="batchDelete()">批量删除</el-button>
        <el-button @click="clearSelection()">清除</el-button>
        <el-button @click="submitForm()">结算</el-button>
      </div>
    </div>

  </div>
<Footer/>
</template>
<style scoped>
@import "@/assets/css/shoppingCart.css";
</style>
