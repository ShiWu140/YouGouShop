import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 创建 Vue 应用
const app = createApp(App)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
