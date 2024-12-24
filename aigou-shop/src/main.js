import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 创建 Vue 应用
const app = createApp(App)
    .use(router)
    .use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 配置生产提示
app.config.productionTip = false;
// 挂载全局属性
app.config.globalProperties.$http = axios;
app.config.globalProperties.$http.defaults.baseURL = 'http://127.0.0.1:8080';

app.mount('#app')
