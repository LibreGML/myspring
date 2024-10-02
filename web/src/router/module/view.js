// 系统内置页面，正式使用时也不应该被删除
export default [
  {
    path: "/index",
    name: "Home",
    component: () =>
      import(/* webpackChunkName: "home" */ "@/views/home/index"),
    meta: {}, // 必须有这个空对象！！！
  },
  {
    path: "/icon",
    name: "Icon",
    component: () => import(/* webpackChunkName: "icon" */ "@/views/icon"),
    meta: { title: "系统图标" },
  },
  {
    path: "/my-user",
    name: "MyUser",
    component: () => import(/* webpackChunkName: "my-user" */ "@/views/user"),
    meta: { title: "个人中心" },
  },
  {
    path: "/message",
    name: "Message",
    component: () =>
      import(/* webpackChunkName: "message" */ "@/views/message"),
    meta: { title: "消息中心" },
  },
  {
    path: "/401",
    name: "Err401", // 如果不需要tab则可以删除此属性
    component: () => import(/* webpackChunkName: "401" */ "@/views/401"),
    meta: {},
  },
  {
    path: "/404",
    name: "Err404",
    component: () => import(/* webpackChunkName: "404" */ "@/views/404"),
    meta: {},
  },
  {
    path: "/redirect/:path*",
    name: "redirect",
    component: () =>
      import(/* webpackChunkName: "redirect" */ "@/views/redirect"),
    meta: {},
  },
];
