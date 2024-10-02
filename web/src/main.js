import Vue from "vue";
import App from "./App";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "@/permissions"; // 路由权限
import "@/styles/element-variables.scss";
import "element-ui/lib/theme-chalk/display.css";
import "@/styles/index.scss"; //全局公共样式
import Fragment from "vue-fragment"; // 可产生虚拟根节点
import "@/filters/index"; // 全局过滤器
import MyElement from "@/components/my-element"; // ElementUI 组件覆盖
import "@/directive/index"; // 全局自定义指令
import { checkPermission } from "@/utils/but-permission"; // 挂载权限按钮的的全局函数
import FilePreview from "@/components/FilePreview";
import "@/console";

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(Fragment.Plugin);
Vue.use(MyElement);

// 设置element组件默认值
Vue.prototype.$ELEMENT = { size: "medium", zIndex: 3000 };
Vue.prototype.$checkPermission = checkPermission; // 挂载在vue原型上，方便在模板上使用
Vue.prototype.$filePreview = FilePreview; // 挂载在vue原型上，方便在模板上使用

ElementUI.Input.props.clearable = { type: Boolean, default: true };
ElementUI.Select.props.clearable = { type: Boolean, default: true };
ElementUI.Pagination.props.background = { type: Boolean, default: true };
ElementUI.Pagination.props.layout = {
  type: String,
  default: "total,prev, pager, next",
};
ElementUI.TableColumn.props.showOverflowTooltip = {
  type: Boolean,
  default: true,
};
ElementUI.Dialog.props.closeOnClickModal.default = false; // 默认禁止点击遮罩层关闭弹窗，可以通过设置closeOnClickModal为true来开启

new Vue({
  router,
  store,
  render(h) {
    return h(App);
  },
}).$mount("#app");
