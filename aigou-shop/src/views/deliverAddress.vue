<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
</script>
<template>
  <!--头部-->
  <div class="top" id="top">
    <Header/>
  </div>
  <div class="w1230">
    <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>
    <span class="cart">收货地址</span>
  </div>
  <!--编辑收货地址-->
  <div class="w1230">
    <h3 class="add-title">新增收货地址</h3>
    <div class="add-address">
      <form action="#" method="post">
        <div class="add-area">
          <label>地址信息：</label>
          <div id="area"></div>
        </div>
        <p>
          <label>详细地址：</label>
          <textarea placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息" class="add-detail"></textarea>
        </p>
        <p>
          <label>收货人姓名：</label>
          <input type="text" class="add-name" placeholder="请输入收货人姓名"/>
        </p>
        <p>
          <label>手机号码：</label>
          <input type="text" class="add-tel" placeholder="请输入收货人手机号"/>
        </p>
        <p class="chk-address">
          <input type="checkbox"/>
          <span>设置为默认收货地址</span>
        </p>
        <input type="submit" value="保存" class="save-btn"/>
      </form>
    </div>
  </div>
  <!--收货地址列表-->
  <div class="add-list w1230">
    <p class="message"><i class="fa fa-exclamation-circle"></i>只能保存5个地址</p>
    <el-table class="table-list">
      <tr>
        <th class="t-name">收货人</th>
        <th class="t-address">收货地址</th>
        <th class="t-tel">手机号</th>
        <th class="t-operate">操作</th>
        <th class="t-default"></th>
      </tr>
      <tr>
        <td>张三</td>
        <td>湖北省武汉市洪山区光谷软件园A</td>
        <td>13912349876</td>
        <td><a href="#">修改</a>|<a href="#">删除</a></td>
        <td class="default-on">默认地址</td>
      </tr>
      <tr>
        <td>李四</td>
        <td>湖北省武汉市洪山区光谷软件园B</td>
        <td>13009876666</td>
        <td><a href="#">修改</a>|<a href="#">删除</a></td>
        <td><a href="#">设为默认</a></td>
      </tr>
      <tr>
        <td>王五</td>
        <td>湖北省武汉市洪山区光谷软件园C</td>
        <td>13588880000</td>
        <td><a href="#">修改</a>|<a href="#">删除</a></td>
        <td><a href="#">设为默认</a></td>
      </tr>
    </el-table>
  </div>
<Footer/>
</template>

<style scoped>
@import "@/assets/css/deliverAddress.css";
</style>
