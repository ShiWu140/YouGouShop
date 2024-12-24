<script setup>
import {ElTable} from 'element-plus';
import {ref} from 'vue';

const num = ref(1)
const handleChange = (value) => {
  console.log(value)

}
const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

<template>
  <!--头部-->
  <div class="top" id="top">
    <!--头部bar-->
    <div class="top-bar">
      <div class="w1230 top-main">
        <a href="index.html" class="toIndex">
          <i class="fa fa-home"></i>
          <span>爱购网首页</span>
        </a>
        <div class="account">
          <span>zhanghao<i class="fa fa-caret-down"></i></span>
          <div class="manage">
            <ul>
              <li><a href="#">收货地址</a></li>
              <li><a href="#">购物车</a></li>
              <li><a href="#">我的订单</a></li>
              <li><a href="#">退出</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="w1230">
    <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>
    <span class="cart">购物车</span>
  </div>

  <!--收货地址-->
  <div class="w1230 address">
    <el-descriptions border>
      <el-descriptions-item label="姓名">十五</el-descriptions-item>
      <el-descriptions-item label="电话号码">15571833169</el-descriptions-item>
      <el-descriptions-item label="省市">武汉市</el-descriptions-item>
      <el-descriptions-item label="地址">
        武昌理工学院
      </el-descriptions-item>
    </el-descriptions>
    <!--    <div class="down"><i class="fa fa-chevron-up" id="down"></i></div>-->
    <div class="address-list" id="address-list">
    </div>
  </div>

  <!--购物车展示-->
  <div class="w1230 shopping">
    <!--标题栏-->

    <!--详细展示-->
    <div class="shopping-list">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="商品信息">
          <template #default="scope">
            <el-image></el-image>
          </template>
        </el-table-column>
        <el-table-column property="price" label="单价"/>
        <el-table-column property="count" label="数量">
          <el-input-number v-model="num" :min="1" :max="10" size="small" @change="handleChange"/>
        </el-table-column>
        <el-table-column property="sumPrice" label="小计(元)"/>
        <el-table-column label="选择">
          <el-button link type="primary" @click="handleClick">删除</el-button>
        </el-table-column>
      </el-table>
    </div>

    <!--合计-->
    <div class="shopping-footer clear-float">
      <div class="shopping-footer-l">
        <input type="checkbox" class="all-chk2 all-chk"/>
        <span>全选（共<span class="all-num"></span>件）</span>
        <span class="line">|</span>
        <a href="#" class="all-del">批量删除</a>
      </div>
      <div class="shopping-footer-r">
        <span>已选商品<span class="choose-num">0</span>件</span>
        <span class="line">|</span>
        <span>合计：<span class="total">￥0.00</span></span>
        <a href="#" class="compute">结算</a>
      </div>
    </div>
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
@import "@/assets/css/shoppingCart.css";
</style>
