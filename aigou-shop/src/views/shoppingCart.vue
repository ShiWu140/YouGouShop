<script lang="js" setup>
import {ElMessage, ElTable} from 'element-plus';
import {onMounted, ref} from 'vue';
import {shoppingCartApi} from '@/api/shoppingCart';


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
// 提取省市信息
const extractProvinceOrCity = (address) => {
  const regex = /^(.*?[省市])/; // 匹配以"省"或"市"结尾的部分
  const match = address.match(regex);
  return match ? match[1] : '';
};
// 商品数量更改
const handleChange = async (row) => {
  try {
    const response = await shoppingCartApi.updateProductNum({
      cartId: row.cartId,
      productId: row.productId,
      productNum: row.productNum
    });
    if (response.code === 1) {
      ElMessage.success("商品数量更新成功！");
    } else {
      ElMessage.error(`更新失败：${response.msg}`);
    }
  } catch (error) {
    ElMessage.error("更新失败，请稍后重试！");
  }
};

// 删除商品
const handleClick = async (row) => {
  try {
    const response = await shoppingCartApi.deleteProductFromCart({
      cartId: row.cartId,
      productId: row.productId
    });
    if (response.code === 1) {
      ElMessage.success('删除成功');
      tableData.value = tableData.value.filter(item => item !== row);
    } else {
      ElMessage.error('删除失败');
    }
  } catch (error) {
    ElMessage.error("删除商品失败");
  }
};

// 批量删除选中商品
const batchDelete = async () => {
  if (!multipleSelection.value.length) {
    ElMessage.warning("请先选择商品再批量删除！");
    return;
  }
  try {
    const results = await shoppingCartApi.batchDeleteProducts(multipleSelection.value);
    const failedDeletes = results.filter(res => res.code !== 1);
    if (failedDeletes.length === 0) {
      ElMessage.success("批量删除成功！");
      tableData.value = tableData.value.filter(item => !multipleSelection.value.includes(item));
      clearSelection();
    } else {
      ElMessage.warning(`部分商品删除失败：${failedDeletes.length}个`);
    }
  } catch (error) {
    ElMessage.error("批量删除失败，请稍后重试！");
  }
};

// 获取购物车数据
const fetchProducts = async () => {
  try {
    const response = await shoppingCartApi.getProductsByUserId(userId);
    if (response.code === 1) {
      const products = response.data || [];
      tableData.value = products.map(product => ({
        cartId: product.cartId,
        productId: product.productId,
        productImage: product.productImage,
        productPrice: product.productPrice,
        productNum: product.productNum,
      }));
    } else {
      console.error('获取购物车数据失败:', response.msg);
    }
  } catch (error) {
    console.error('获取购物车数据失败:', error);
  }
};

// 获取收货地址数据
const fetchAddress = async () => {
  try {
    const response = await shoppingCartApi.getReceivingAddressByUserId(userId);
    if (response.code === 1) {
      const addresses = response.data;
      addressList.value = addresses;
      
      const defaultAddress = addresses.find(item => item.isDefault === 1);
      if (defaultAddress) {
        addressData.value = defaultAddress;
      } else if (addresses.length > 0) {
        addressData.value = addresses[0];
      }
    } else {
      ElMessage.error('获取收货地址失败');
    }
  } catch (error) {
    console.error('获取收货地址失败:', error);
    ElMessage.error('获取收货地址失败');
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
  
  const orderData = {
    id: "",
    userId: userId,
    createTime: "",
    state: 0,
    receivingAddress: addressData.value.id,
    products: multipleSelection.value.map(item => ({
      productId: item.productId,
      productNum: item.productNum,
    })),
  };

  try {
    const response = await shoppingCartApi.submitOrder(orderData);
    if (response.code === 1) {
      const orderId = response.data.orderId;
      const totalAmount = response.data.totalAmount;

      const wxPayResponse = await shoppingCartApi.createWxPayOrder({
        orderId: orderId,
        price: totalAmount
      });

      if (wxPayResponse.code === 1) {
        const qrCodeUrl = wxPayResponse.data.code_url;
        const tradeNo = wxPayResponse.data.trade_no;
        window.location.href = `/payment?&totalAmount=${encodeURIComponent(totalAmount)}
        &qrCodeUrl=${encodeURIComponent(qrCodeUrl)}
        &tradeNo=${encodeURIComponent(tradeNo)}`;
      } else {
        ElMessage.error(`微信下单失败：${wxPayResponse.msg}`);
      }
      clearSelection();
    } else {
      ElMessage.error(`订单提交失败：${response.msg}`);
    }
  } catch (error) {
    ElMessage.error("订单提交失败，请稍后重试！");
  }
};
// 切换地址
const selectAddress = (address) => {
  addressData.value = address;
  dialogVisible.value = false;
  ElMessage.success('收货地址已更新');
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
            <el-button link type="danger" @click="handleClick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 2px">
        <el-button @click="toggleSelection(tableData)">全选</el-button>
        <el-button type="danger" @click="batchDelete()">批量删除</el-button>
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
