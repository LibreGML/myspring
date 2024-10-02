import Vue from "vue";
import vClickOutside from "v-click-outside";

// 水波纹指令
import waves from "./waves/index";

// 元素大小改变
import resize from "./resize";
Vue.directive("waves", waves); // 全局注册

// 点击外部时触发
Vue.use(vClickOutside);

// 监测元素大小改变
Vue.directive("resize", resize);
