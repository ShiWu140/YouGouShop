import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import router from './router';
import axios from "axios";
import qs from 'qs';

Vue.config.productionTip = false;
Vue.prototype.$qs = qs;
Vue.prototype.$http = axios;
Vue.prototype.$http.defaults.baseURL = 'http://127.0.0.1:8080';
Vue.prototype.$http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
Vue.use(ElementUI);
new Vue({
    router, render: function (h) {
        return h(App)
    }
}).$mount('#app')
