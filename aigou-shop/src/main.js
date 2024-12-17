import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 封装 axios 实例
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 10 * 1000
})

// 添加全局错误处理
axiosInstance.interceptors.response.use(
    response => response,
    error => {
        // 处理错误，例如显示错误提示
        console.error('Request failed:', error)
        return Promise.reject(error)
    }
)

// 创建 Vue 应用
const app = createApp(App)
    .use(router)
    .use(ElementPlus)
    .provide('$axios', axiosInstance)
    .mount('#app')
