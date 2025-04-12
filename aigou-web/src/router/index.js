import Vue from 'vue';
import VueRouter from 'vue-router';
import IndexView from '@/views/HomeView.vue';
import LoginView from "@/views/LoginVue.vue";
import AdminView from "@/views/AdminView.vue";
import adminProduct from "@/views/admin/product.vue";
// 首页
import adminHome from "@/views/admin/home.vue";
// 商品
import adminProductType from "@/views/admin/productType.vue";
import adminBrand from "@/views/admin/brand.vue";
import adminSales from "@/views/admin/sales.vue";
// 购物车
import adminShopCart from "@/views/admin/shopCart.vue";
import adminShopCartProduct from "@/views/admin/shopCartProduct.vue";
import adminReceivingAddress from "@/views/admin/receivingAddress.vue";
// 订单
import adminOrder from "@/views/admin/order.vue";
import adminOrderProduct from "@/views/admin/orderProduct.vue";
// 系统
import adminUser from "@/views/admin/user.vue";
import adminCarouselFigure from "@/views/admin/carouselFigure.vue";
import adminSearchHistory from "@/views/admin/searchHistory.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Index',
        component: IndexView
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginView,
        meta: { requiresAuth: false }
    },
    {
        path: '/admin',
        name: 'AdminView',
        component: AdminView,
        meta: { requiresAuth: true },
        children: [
            {
                path: '',
                redirect: 'home'
            },
            {
                path: 'home',
                name: 'Home',
                component: adminHome,
                meta: { requiresAuth: true }
            },
            // 商品
            {
                path: 'product',
                name: 'Product',
                component: adminProduct,
                meta: { requiresAuth: true }
            },
            {
                path: 'product-type',
                name: 'ProductType',
                component: adminProductType,
                meta: { requiresAuth: true }
            },
            {
                path: 'brand',
                name: 'Brand',
                component: adminBrand,
                meta: { requiresAuth: true }
            },
            {
                path: 'sales',
                name: 'Sales',
                component: adminSales,
                meta: { requiresAuth: true }
            },
            // 购物车
            {
                path: 'shop-cart',
                name: 'ShopCart',
                component: adminShopCart,
                meta: { requiresAuth: true }
            },
            {
                path: 'shop-cart-product',
                name: 'ShopCartProduct',
                component: adminShopCartProduct,
                meta: { requiresAuth: true }
            },
            {
                path: 'receiving-address',
                name: 'ReceivingAddress',
                component: adminReceivingAddress,
                meta: { requiresAuth: true }
            },
            // 订单
            {
                path: 'order',
                name: 'Order',
                component: adminOrder,
                meta: { requiresAuth: true }
            },
            {
                path: 'order-product',
                name: 'OrderProduct',
                component: adminOrderProduct,
                meta: { requiresAuth: true }
            },
            // 系统
            {
                path: 'user',
                name: 'User',
                component: adminUser,
                meta: { requiresAuth: true }
            },
            {
                path: 'carousel-figure',
                name: 'CarouselFigure',
                component: adminCarouselFigure,
                meta: { requiresAuth: true }
            },
            {
                path: 'search-history',
                name: 'SearchHistory',
                component: adminSearchHistory,
                meta: { requiresAuth: true }
            }
        ]
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

// 创建全局前置守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

    // 如果需要认证
    if (requiresAuth) {
        if (!token) {
            // 没有token，重定向到登录页
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            });
        } else {
            // 有token，直接放行
            next();
        }
    } else {
        // 不需要认证的路由，直接放行
        if (to.path === '/login' && token) {
            // 如果已登录且访问登录页，重定向到首页
            next({ path: '/admin' });
        } else {
            next();
        }
    }
});

export default router;
