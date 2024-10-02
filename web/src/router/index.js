import Vue from "vue";
import VueRouter from "vue-router";
import Layout from "@/layout";
import view from "./module/view";
import Pages from "./module/pages";

Vue.use(VueRouter);

/*
对VueRouter原型链上的push、replace方法进行重写
为了解决某些情况下控制台报 ‘Uncaught (in promise) undefined’的问题
参考地址：https://github.com/vuejs/vue-router/issues/2881
*/
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
};
/**
 * 所有页面都应该是一级页面，或者说必须直接渲染到layout上，否则没有缓存
 * name 必须与组件name一致，用于路由缓存功能
 * permission 为布尔时表示需要权限,默认值应该为 false，待后端返回相应的地址后会自动改为 true
 */
const routes = [
  {
    path: "/login",
    hidden: true,
    component: () =>
      import(/* webpackChunkName: "login" */ "@/views/login/layout"),
    children: [
      {
        path: "",
        component: () =>
          import(/* webpackChunkName: "login" */ "@/views/login/index"),
        meta: { title: "登录" },
      },
      {
        path: "/login/reset-password",
        component: () =>
          import(
            /* webpackChunkName: "reset-password" */ "@/views/login/reset-password"
          ),
        meta: { title: "找回密码" },
      },
    ],
  },
  {
    path: "/",
    component: Layout,
    redirect: process.env.VUE_APP_HOME_PATH,
    children: [...view, ...Pages],
    meta: { permission: true },
  },
  {
    path: "/500",
    name: "Err500",
    component: () => import(/* webpackChunkName: "500" */ "@/views/500"),
    meta: {},
  },
  { path: "*", redirect: "/404" },
];

const router = new VueRouter({
  // mode: "history",
  // base: process.env.BASE_URL,
  routes,
});

export default router;
