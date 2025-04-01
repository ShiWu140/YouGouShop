<script setup>
import {onMounted, ref} from "vue";
import { useRouter, useRoute } from 'vue-router';
import axios from "axios";

const router = useRouter();
const route = useRoute();
const searchHistory = ref([]);
const searchKeyword = ref('');

const handleSearch = () => {
  if (!searchKeyword.value.trim()) {
    return;
  }
  
  // 根据当前路由决定跳转行为
  if (route.path === '/classify') {
    // 在分类页面时，触发父组件的搜索方法
    const event = new CustomEvent('search', { 
      detail: { keyword: searchKeyword.value.trim() }
    });
    window.dispatchEvent(event);
  } else {
    // 在首页时，跳转到分类页面
    router.push({
      path: '/classify',
      query: { keyword: searchKeyword.value.trim() }
    });
  }
};

const handleKeyPress = (event) => {
  if (event.key === 'Enter') {
    handleSearch();
  }
};

const fetchSearchHistory = async () => {
  try {
    const response = await axios.get('http://localhost:8080/searchHistory/page', {
      params: {
        current: 1,
        size: 10
      }
    });
    searchHistory.value = response.data.data.records;
  } catch (error) {
    console.error('Error fetching search history:', error);
  }
}

onMounted(() => {
  fetchSearchHistory();
  
  // 如果有搜索关键词，设置到搜索框
  if (route.query.keyword) {
    searchKeyword.value = route.query.keyword;
  }
})
</script>

<template>
  <!--logo+搜索-->
  <div class="top-header w1230">
    <router-link to="/" class="logo">
      <img src="@/assets/img/logo.png" alt="logo"/>
    </router-link>
    <div class="top-header-right">
      <!--搜索框-->
      <div class="search">
        <input 
          type="text" 
          v-model="searchKeyword"
          :placeholder="route.path === '/classify' ? '优购网-专业的综合网上购物商城' : '请输入商品关键词'" 
          class="search-txt"
          @keyup="handleKeyPress"
        />
        <button class="search-btn" @click="handleSearch">搜索</button>
      </div>
      <!--热搜-->
      <p class="hotkey">
        <a 
          v-for="history in searchHistory" 
          :key="history.id" 
          @click="searchKeyword = history.searchWords; handleSearch()"
        >
          {{ history.searchWords }}
        </a>
      </p>
    </div>
  </div>
</template>

<style scoped>
.top-header {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 50px;
  max-width: 1230px;
  margin: 0 auto;
  min-height: 110px;
  box-sizing: border-box;
}

.logo {
  display: block;
  text-decoration: none;
  flex-shrink: 0;
  margin-left: 0;
}

.logo img {
  display: block;
  width: 150px;
  height: 70px;
  margin-right: 0;
}

.top-header-right {
  flex: 1;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
  width: auto;
  min-height: 90px;
  box-sizing: border-box;
}

.search {
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 5px;
  height: 40px;
}

.search-txt {
  flex: 1;
  height: 40px;
  padding: 0 15px;
  border: 2px solid #B41E23;
  border-right: none;
  font-size: 14px;
  outline: none;
  border-radius: 4px 0 0 4px;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

.search-txt:focus {
  box-shadow: 0 0 5px rgba(180, 30, 35, 0.3);
}

.search-btn {
  width: 90px;
  height: 40px;
  background-color: #B41E23;
  border: none;
  color: white;
  font-size: 14px;
  cursor: pointer;
  border-radius: 0 4px 4px 0;
  transition: all 0.3s ease;
  text-align: center;
  line-height: 40px;
  font-weight: bolder;
  box-sizing: border-box;
}

.search-btn:hover {
  background-color: #d42f34;
  transform: translateY(-1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.hotkey {
  width: 100%;
  margin-top: 5px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  justify-content: flex-start;
  padding-left: 0;
  min-height: 30px;
  box-sizing: border-box;
}

.hotkey a {
  color: #666;
  text-decoration: none;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 2px 8px;
  border-radius: 10px;
  background-color: #f5f5f5;
  line-height: normal;
  margin-right: 0;
  box-sizing: border-box;
}

.hotkey a:hover {
  color: #B41E23;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>