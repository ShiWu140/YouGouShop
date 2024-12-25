<script>
export default {
  data() {
    return {
      userName: '',
    };
  },
  mounted() {
    this.userName = localStorage.getItem('userName')
  },
};
</script>

<template>
  <div class="dashboard">
    <div class="welcome-container">
      <span class="welcome-message">🎉欢迎用户🎉<br>{{ userName }}</span>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.welcome-message {
  font-size: 50px;
  font-weight: bolder;
  display: block;
  text-align: center;
  color: #000;
}
</style>



