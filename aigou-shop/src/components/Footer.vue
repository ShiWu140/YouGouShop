<script setup>

</script>

<template>
  <!--脚注-->
  <div class="footer">
    <div class="w1230">
      <p>
        <a href="#">关于优购</a>
        <a href="#">合作伙伴</a>
        <a href="#">营销中心</a>
        <a href="#">廉正举报</a>
        <a href="#">联系客服</a>
        <a href="#">开发平台</a>
        <a href="#">诚征英才</a>
        <a href="#">联系我们</a>
        <a href="#">网站地图</a>
        <a href="#">知识产权</a>
        <span>|</span>
        <span>&copy;2024-2025 dushiwu.n 版权所有</span>
      </p>
    </div>
  </div>
</template>

<style scoped>
.footer {
  background-color: #F4F4F4;
  border-top: 1px solid #e5e5e5;
  padding: 20px 0;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100;
}

.w1230 {
  width: 100%;
  max-width: 1230px;
  margin: 0 auto;
  padding: 0 15px;
  box-sizing: border-box;
}

.footer p {
  text-align: center;
  margin: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 10px;
  width: 100%;
}

.footer p span {
  color: #999;
  display: inline-block;
}

.footer a {
  color: #999;
  text-decoration: none;
  transition: color 0.3s ease;
  display: inline-block;
}

.footer a:hover {
  color: #333;
}
</style>
