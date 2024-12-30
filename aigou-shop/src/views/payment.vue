<script setup>
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';
import {ElCard, ElRow, ElCol, ElButton, ElImage, ElMessage} from 'element-plus';
import axios from 'axios';

// 订单号和金额数据
const orderId = ref('');
const orderAmount = ref('');
const paymentQrCode = ref('');
const tradeNo = ref('');
// 支付状态，默认为PENDING
const paymentStatus = ref('PENDING');

// 获取路由参数
const route = useRoute();

// 初始化 orderId, orderAmount 和 tradeNo
orderId.value = route.query.orderId;
orderAmount.value = route.query.totalAmount;
tradeNo.value = route.query.tradeNo;

// 支付处理函数
const handlePayment = () => {
  // 处理支付逻辑
  ElMessage.success('支付成功！');
};

// 获取支付二维码
const fetchPaymentQrCode = async () => {
  const qrCodeUrl = route.query.qrCodeUrl;
  try {
    const response = await axios.get('/wxpay/code', {
      params: {
        url: qrCodeUrl
      },
      responseType: 'blob' // 设置响应类型为 blob
    });

    // 创建一个 URL 对象
    const url = URL.createObjectURL(new Blob([response.data]));
    paymentQrCode.value = url;
  } catch (error) {
    console.error("获取支付二维码失败：", error);
    ElMessage.error("获取支付二维码失败，请稍后重试！");
  }
};
// 检查订单状态
const checkOrderStatus = async () => {
  try {
    const response = await axios.get('/wxpay/checkOrder', {
      params: {
        tradeNo: tradeNo.value
      }
    });

    // 解析接口返回的数据
    const { code, msg, data } = response.data;
    if (code === 1 && msg === "success") {
      paymentStatus.value = data; // 使用 data 字段的值
      if (paymentStatus.value === 'SUCCESS') {
        ElMessage.success('支付成功！');
        // 停止轮询
        clearInterval(intervalId);
      } else {
        ElMessage.info("未支付");
      }
    } else {
    }
  } catch (error) {
  }
};

let intervalId;


// 组件挂载时获取支付二维码并开始轮询
onMounted(() => {
  fetchPaymentQrCode();
  // 每10秒检查一次订单状态
  intervalId = setInterval(checkOrderStatus, 10000);
});
</script>

<template>
  <div class="payment-page">
    <div class="payment-container">
      <el-card class="payment-card">
        <div class="order-info">
          <el-row class="order-row">
            <el-col :span="6" class="label">支付单号:</el-col>
            <el-col :span="18" class="value">{{ tradeNo }}</el-col>
          </el-row>
          <el-row class="order-row">
            <el-col :span="6" class="label">订单金额:</el-col>
            <el-col :span="18" class="value">{{ orderAmount }} 元</el-col>
          </el-row>
        </div>

        <div class="payment-qr">
          <el-row>
            <el-col :span="24" class="qr-title">扫码支付</el-col>
            <el-col :span="24" class="qr-image">
              <el-image :src="paymentQrCode" fit="contain" width="200px" height="200px"></el-image>
            </el-col>
          </el-row>
        </div>

        <div class="payment-action" v-if="paymentStatus !== 'SUCCESS'">
          <el-button type="primary" size="large" @click="handlePayment">立即支付</el-button>
        </div>

        <div class="payment-success" v-if="paymentStatus === 'SUCCESS'">
          <el-row>
            <el-col :span="24" class="success-title">支付成功！</el-col>
          </el-row>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.payment-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.payment-container {
  width: 400px;
  padding: 20px;
}

.payment-card {
  border-radius: 10px;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.order-info {
  margin-bottom: 20px;
}

.order-row {
  margin-bottom: 10px;
}

.label {
  font-weight: bold;
  color: #333;
}

.value {
  color: #555;
}

.payment-qr {
  margin-bottom: 20px;
  text-align: center;
}

.qr-title {
  font-size: 18px;
  margin-bottom: 10px;
}

.payment-action {
  text-align: center;
}

.el-button {
  width: 100%;
}

.payment-success {
  text-align: center;
  margin-top: 20px;
}

.success-title {
  font-size: 18px;
  color: #67c23a;
}
</style>
