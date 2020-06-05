import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import FastClick from 'fastclick'
import axios from 'axios'
import API from './server/APIUtil'
import store from './store/index'
import StorageUtil from "./config/StorageUtil";

Vue.prototype.StorageUtil = StorageUtil;
Vue.prototype.axios = axios;
Vue.prototype.API = API;
Vue.config.productionTip = false;

if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function () {
        FastClick.attach(document.body);
    }, false)
}
import './style/common.less'
import './plugins/vant'
import './config/rem'
import './config/filter'
import cookieUtil from "../../../by_store/web_user/src/util/CookieUtil";
import Storage from "../../../by_store/web_user/src/util/StorageUtil";

axios.defaults.withCredentials = true;
axios.interceptors.request.use(
    config => {
        const userInfo = StorageUtil.Local.getAndTime("userInfo");
        if(userInfo){
            if (userInfo.token) {
                config.headers['token'] = StorageUtil.Local.getAndTime("userInfo").token;
            }
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    });

// 路由拦截
// 设置路由的前置守卫（路由跳转之前）
router.beforeEach((to, from, next) => {
    const userInfo = StorageUtil.Local.getAndTime("userInfo");
    if (to.matched.length === 0) { // 如果未匹配到路由
        next('/404')
    }
    if (to.path === '/dashboard/login' || to.path === '/dashboard/home' || to.path === '/dashboard/category'
       ||to.path === '/dashboard/register') {
        next();
    } else {
       if(userInfo){
           if (userInfo.username) {
               next();
           }
       }else {
           next({path: "/dashboard/login"})
       }
    }
});
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
