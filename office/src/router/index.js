import Vue from "vue";
import VueRouter from "vue-router";

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
    path: "/home",
    hidden: true,
    component: () => import("@/views/Home"),
    meta: {},
  },
  {
    path: "/join",
    hidden: true,
    component: () => import("@/views/Join"),
    meta: {},
  },
  {
    path: "/source",
    hidden: true,
    component: () => import("@/views/Source"),
    meta: {},
  },
  {
    path: "/goods",
    hidden: true,
    component: () => import("@/views/Goods"),
    meta: {},
  },
  {
    path: "/",
    redirect: '/home',
    meta: { permission: true },
  },
  // {
  //   path: "/500",
  //   name: "Err500",
  //   component: () => import(/* webpackChunkName: "500" */ "@/views/500"),
  //   meta: {},
  // },
  { path: "*", redirect: "/404" },
];

const router = new VueRouter({
  routes,
});

export default router;
