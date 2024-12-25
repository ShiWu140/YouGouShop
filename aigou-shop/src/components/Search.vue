<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
const searchHistory = ref([]);
const fetchSearchHistory = async () => {
  try {
    const response = await axios.get('http://localhost:8080/searchHistory/page', {
      params: {
        current: 1, // 当前页码
        size: 10    // 每页大小
      }
    });
    searchHistory.value = response.data.data.records;
    console.log('Search history fetched:', searchHistory.value)
  } catch (error) {
    console.error('Error fetching search history:', error);
  }
}

onMounted(() => {
  fetchSearchHistory();
})

</script>

<template>
  <!--logo+搜索-->
  <div class="top-header w1230 clear-float">
    <router-link to="/" target="_blank" class="logo">
      <img src="@/assets/img/logo.png"/>
    </router-link>
    <div class="top-header-right">
      <!--搜索框-->
      <div class="search clear-float">
        <input type="text" placeholder="牛奶" class="search-txt"/>
        <a href="#" class="search-btn">搜索</a>
      </div>
      <!--热搜-->
      <p class="hotkey">
        <a v-for="history in searchHistory" :key="history.id" :href="`#${history.searchWords}`">{{
            history.searchWords
          }}</a>
      </p>
    </div>
  </div>
</template>

<style scoped>

</style>