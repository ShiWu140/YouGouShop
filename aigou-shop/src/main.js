import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";

// 创建 Vue 应用
const app = createApp(App)
    .use(router)
    .use(ElementPlus)

// 配置生产提示
app.config.productionTip = false;
// 挂载全局属性
app.config.globalProperties.$http = axios;
app.config.globalProperties.$http.defaults.baseURL = 'http://127.0.0.1:8080';

app.mount('#app')
