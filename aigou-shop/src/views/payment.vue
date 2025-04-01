<script setup>
import {ref, onMounted} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {ElCard, ElRow, ElCol, ElButton, ElImage, ElMessage} from 'element-plus';
import {checkOrderStatus} from '@/api/payment';
import axios from 'axios';

const router = useRouter();
// 订单号和金额数据
const orderId = ref('');
const orderAmount = ref('');
const tradeNo = ref('');
// 支付状态，默认为PENDING
const paymentStatus = ref('PENDING');

// 获取路由参数
const route = useRoute();

// 初始化 orderId, orderAmount 和 tradeNo
orderId.value = route.query.orderId;
orderAmount.value = route.query.totalAmount;
tradeNo.value = route.query.tradeNo;

// 测试环境的支付处理函数
const handlePayment = async () => {
  try {
    // 直接更新订单状态为已支付
    const response = await axios.post('/order/updatePaymentStatus', null, {
      params: {
        orderId: orderId.value,
        state: 1  // 1 表示已支付
      }
    });
    
    if (response.data.code === 1) {
      ElMessage.success('支付成功！');
      paymentStatus.value = 'SUCCESS';
      // 3秒后跳转到订单页面
      setTimeout(() => {
        router.push('/buyerTrade');
      }, 3000);
    } else {
      ElMessage.error('支付失败，请重试！');
    }
  } catch (error) {
    console.error('支付处理失败:', error);
    ElMessage.error('支付失败，请重试！');
  }
};

/* 以下为原微信支付相关代码，暂时注释保存
// 检查订单状态
const checkStatus = async () => {
  try {
    const {code, msg, data} = await checkOrderStatus(tradeNo.value);
    if (code === 1 && msg === "success") {
      paymentStatus.value = data;
      if (paymentStatus.value === 'SUCCESS') {
        ElMessage.success('支付成功！');
        // 停止轮询
        clearInterval(intervalId);
      } else {
        ElMessage.info("未支付");
      }
    }
  } catch (error) {
    console.error('检查订单状态失败:', error);
  }
};

let intervalId;

// 组件挂载时开始轮询
onMounted(() => {
  // 每10秒检查一次订单状态
  intervalId = setInterval(checkStatus, 10000);
});
*/
</script>

<template>
  <div class="payment-page">
    <div class="payment-container">
      <el-card class="payment-card">
        <div class="order-info">
          <el-row class="order-row">
            <el-col :span="6" class="label">订单号:</el-col>
            <el-col :span="18" class="value">{{ orderId }}</el-col>
          </el-row>
          <el-row class="order-row">
            <el-col :span="6" class="label">订单金额:</el-col>
            <el-col :span="18" class="value">{{ orderAmount }} 元</el-col>
          </el-row>
        </div>

        <!-- 注释掉微信支付二维码部分
        <div class="payment-qr">
          <el-row>
            <el-col :span="24" class="qr-title">扫码支付</el-col>
            <el-col :span="24" class="qr-image">
              <el-image 
                :src="'http://localhost:8080/wxpay/code?url=' + route.query.qrCodeUrl" 
                fit="contain" 
                width="200px" 
                height="200px"
              ></el-image>
            </el-col>
          </el-row>
        </div>
        -->

        <div class="payment-action" v-if="paymentStatus !== 'SUCCESS'">
          <el-button type="primary" size="large" @click="handlePayment">立即支付</el-button>
        </div>

        <div class="payment-success" v-if="paymentStatus === 'SUCCESS'">
          <el-row>
            <el-col :span="24" class="success-title">
              <el-icon><Check /></el-icon>
              支付成功！3秒后跳转到订单页面...
            </el-col>
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
  color: #67C23A;
  font-size: 20px;
  text-align: center;
  margin: 20px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.success-title .el-icon {
  font-size: 24px;
}

.qr-loading {
  width: 200px;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  border-radius: 4px;
  color: #909399;
}
</style>
