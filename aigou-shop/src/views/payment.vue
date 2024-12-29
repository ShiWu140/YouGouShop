<script setup>
import {ref} from 'vue';
import {ElCard, ElRow, ElCol, ElButton, ElImage} from 'element-plus';
import {ElMessage} from "element-plus";

// 订单号和金额数据
const orderId = ref('ORD1234567890'); // 模拟的订单号
const orderAmount = ref('299.99'); // 模拟的订单金额
const paymentQrCode = ref('https://shiwu-aigou.oss-cn-wuhan-lr.aliyuncs.com/images/mm_facetoface_collect_qrcode_1735455239707%281%29.png'); // 模拟的支付二维码

// 支付处理函数
const handlePayment = () => {
  // 处理支付逻辑
  ElMessage.success('支付成功！');
};
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

        <div class="payment-qr">
          <el-row>
            <el-col :span="24" class="qr-title">扫码支付</el-col>
            <el-col :span="24" class="qr-image">
              <el-image :src="paymentQrCode" fit="contain" width="200px" height="200px"></el-image>
            </el-col>
          </el-row>
        </div>

        <div class="payment-action">
          <el-button type="primary" size="large" @click="handlePayment">立即支付</el-button>
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
</style>
