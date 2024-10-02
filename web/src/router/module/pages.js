// 在这里注册你的业务页面
export default [
  {
    path: "/account",
    name: "Account",
    component: () => import("@/pages/account/index"),
    meta: {},
  },
  {
    path: "/account/details",
    name: "AccountDetails",
    component: () => import("@/pages/account/details"),
    meta: { title: "账户详情" },
  },
  {
    path: "/business",
    name: "Business",
    component: () => import("@/pages/business/index"),
    meta: {},
  },
  {
    path: "/business/details",
    name: "BusinessDetails",
    component: () => import("@/pages/business/details"),
    meta: { title: "商家详情" },
  },
  {
    path: "/supplier",
    name: "Supplier",
    component: () => import("@/pages/supplier/index"),
    meta: {},
  },
  {
    path: "/supplier/details",
    name: "SupplierDetails",
    component: () => import("@/pages/supplier/details"),
    meta: { title: "供应商详情" },
  },
  {
    path: "/jump-queue",
    name: "jump-queue",
    component: () => import("@/pages/jump-queue/index"),
    meta: {},
  },
  {
    path: "/jump-queue/details",
    name: "jumpQueueDetails",
    component: () => import("@/pages/jump-queue/details"),
    meta: { title: "插队详情" },
  },
  {
    path: "/withdrawal",
    name: "Withdrawal",
    component: () => import("@/pages/withdrawal/index"),
    meta: {},
  },
  {
    path: "/order",
    name: "order",
    component: () => import("@/pages/order/index"),
    meta: {},
  },
  {
    path: "/order/details",
    name: "orderDetails",
    component: () => import("@/pages/order/details"),
    meta: { title: "提现详情" },
  },
  {
    path: "/sales",
    name: "sales",
    component: () => import("@/pages/sales/index"),
    meta: {},
  },
  {
    path: "/sales/details",
    name: "salesDetails",
    component: () => import("@/pages/sales/details"),
    meta: { title: "售后详情" },
  },
  {
    path: "/notice",
    name: "Notice",
    component: () => import("@/pages/notice/index"),
    meta: {},
  },
  {
    path: "/notice/details",
    name: "NoticeDetails",
    component: () => import("@/pages/notice/details"),
    meta: { title: "公告详情" },
  },
  {
    path: "/source",
    name: "source",
    component: () => import("@/pages/source/index"),
    meta: {},
  },
  {
    path: "/source/details",
    name: "sourceDetails",
    component: () => import("@/pages/source/details"),
    meta: { title: "货源详情" },
  },
  {
    path: "/price",
    name: "price",
    component: () => import("@/pages/price/index"),
    meta: {},
  },
  {
    path: "/fund",
    name: "fund",
    component: () => import("@/pages/fund/index"),
    meta: {},
  },
  {
    path: "/category",
    name: "category",
    component: () => import("@/pages/categorys/index"),
    meta: {},
  },
  {
    path: "/website",
    name: "website",
    component: () => import("@/pages/website/index"),
    meta: {},
  },
  {
    path: "/background",
    name: "background",
    component: () => import("@/pages/background/index"),
    meta: {},
  },
  {
    path: "/log",
    name: "log",
    component: () => import("@/pages/log/index"),
    meta: {},
  },
  {
    path: "/product",
    name: "product",
    component: () => import("@/pages/product/index"),
    meta: {},
  },
  {
    path: "/sticky",
    name: "StickyDemo",
    component: () =>
      import(/* webpackChunkName: "sticky" */ "@/pages/components/Sticky"),
    meta: { title: "Sticky" },
  },
  {
    path: "/waterfall",
    name: "WaterfallDemo",
    component: () =>
      import(
        /* webpackChunkName: "waterfall" */ "@/pages/components/Waterfall"
      ),
    meta: { title: "Waterfall" },
  },
  {
    path: "/water-mark",
    name: "WaterMarkDemo",
    component: () =>
      import(
        /* webpackChunkName: "waterfall" */ "@/pages/components/WaterMark"
      ),
    meta: { title: "WaterMark" },
  },
  {
    path: "/count-up",
    name: "CountUpDemo",
    component: () =>
      import(/* webpackChunkName: "CountUp" */ "@/pages/components/CountUp"),
    meta: { title: "CountUp" },
  },
  {
    path: "/img-preview",
    name: "imgPreviewDemo",
    component: () =>
      import(
        /* webpackChunkName: "imgPreview" */ "@/pages/components/imgPreview"
      ),
    meta: { title: "imgPreview" },
  },
  {
    path: "/upload",
    name: "Upload",
    component: () =>
      import(/* webpackChunkName: "upload" */ "@/pages/components/upload"),
    meta: { title: "Upload" },
  },
];
