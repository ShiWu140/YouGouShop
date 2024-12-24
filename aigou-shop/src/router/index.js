import {createRouter, createWebHistory} from 'vue-router';

import IndexView from '@/views/index.vue';
import LoginView from '@/views/login.vue';
import RegisterView from '@/views/register.vue';
import ShoppingCartView from '@/views/shoppingCart.vue';
import BuyerTradeView from '@/views/buyerTrade.vue';
import ClassifyView from '@/views/classify.vue';
import GoodsDetailView from '@/views/goodsDetail.vue';
import DeliverAddressView from '@/views/deliverAddress.vue';

const routes = [
    {
        path: '/',
        name: 'index',
        meta: {noRequireAuth: true},
        component: IndexView,
    },
    {
        path: '/login',
        name: 'login',
        meta: {noRequireAuth: true},
        component: LoginView,
    },
    {
        path: '/register',
        name: 'register',
        meta: {noRequireAuth: true},
        component: RegisterView,
    },
    {
        path: '/shoppingCart',
        name: 'shoppingCart',
        meta: {noRequireAuth: true},
        component: ShoppingCartView,
    },
    {
        path: '/buyerTrade',
        name: 'buyerTrade',
        meta: {noRequireAuth: true},
        component: BuyerTradeView,
    },
    {
        path: '/classify',
        name: 'classify',
        meta: {noRequireAuth: true},
        component: ClassifyView,
    },
    {
        path: '/goodsDetail',
        name: 'goodsDetail',
        meta: {noRequireAuth: true},
        component: GoodsDetailView,
    },
    {
        path: '/deliverAddress',
        name: 'deliverAddress',
        meta: {noRequireAuth: true},
        component: DeliverAddressView,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// 检查用户是否已登录
const isAuthenticated = () => {
    const user = localStorage.getItem("userId");
    const token = localStorage.getItem("token");
    return user && token;
};

router.beforeEach((to, from, next) => {
    // 如果路由不需要认证，直接放行
    if (to.meta.noRequireAuth) {
        return next();
    }
    // 如果用户已登录，放行
    if (isAuthenticated()) {
        return next();
    }
    // 如果用户未登录，重定向到首页
    next({path: '/'});
});

export default router;
