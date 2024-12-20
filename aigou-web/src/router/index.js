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
        component: LoginView
    },
    {
        path: '/admin',
        name: 'AdminView',
        component: AdminView,
        meta: {requiresAuth: true},
        children: [
            {
                path: '',
                redirect: 'home'
            },
            {
                path: 'home',
                name: 'Home',
                component: adminHome,
                meta: {requiresAuth: true}
            },
            // 商品
            {
                path: 'product',
                name: 'Product',
                component: adminProduct,
                meta: {requiresAuth: true}
            },
            {
                path: 'product-type',
                name: 'ProductType',
                component: adminProductType,
                meta: {requiresAuth: true}
            },
            {
                path: 'brand',
                name: 'Brand',
                component: adminBrand,
                meta: {requiresAuth: true}
            },
            {
                path: 'sales',
                name: 'Sales',
                component: adminSales,
                meta: {requiresAuth: true}
            },
            // 购物车
            {
                path: 'shop-cart',
                name: 'ShopCart',
                component: adminShopCart,
                meta: {requiresAuth: true}
            },
            {
                path: 'shop-cart-product',
                name: 'ShopCartProduct',
                component: adminShopCartProduct,
                meta: {requiresAuth: true}
            },
            {
                path: 'receiving-address',
                name: 'ReceivingAddress',
                component: adminReceivingAddress,
                meta: {requiresAuth: true}
            },
            // 订单
            {
                path: 'order',
                name: 'Order',
                component: adminOrder,
                meta: {requiresAuth: true}
            },
            {
                path: 'order-product',
                name: 'OrderProduct',
                component: adminOrderProduct,
                meta: {requiresAuth: true}
            },
            // 系统
            {
                path: 'user',
                name: 'User',
                component: adminUser,
                meta: {requiresAuth: true}
            },
            {
                path: 'carousel-figure',
                name: 'CarouselFigure',
                component: adminCarouselFigure,
                meta: {requiresAuth: true}
            },
            {
                path: 'search-history',
                name: 'SearchHistory',
                component: adminSearchHistory,
                meta: {requiresAuth: true}
            }
        ]
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

let isRedirecting = false;
// 创建全局前置守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token'); // 获取本地存储中的token

    // 检查目标路由是否需要授权
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 如果没有token，则重定向到登录页面
        if (!token) {
            if (!isRedirecting) {
                isRedirecting = true;
                next({
                    path: '/login',
                    query: {redirect: to.fullPath} // 将当前路由作为参数传递给登录页面
                });
            } else {
                next(false); // 阻止进一步的导航
            }
        } else {
            isRedirecting = false;
            // 如果有token且是从登录页重定向过来的，检查是否有重定向参数
            if (from.path === '/login' && to.query.redirect) {
                // 解析并重定向到原始目标页面
                const redirectPath = decodeURIComponent(to.query.redirect);
                next(redirectPath);
            } else if (from.path.startsWith('/admin') && to.path.startsWith('/admin')) {
                // 如果是从 /admin 下的子路由跳转到另一个 /admin 子路由，直接放行
                next();
            } else {
                // 直接放行，避免不必要的重定向
                next();
            }
        }
    } else {
        next(); // 对于不需要授权的路由，直接放行
    }
});

export default router;
