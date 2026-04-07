import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import axios from './config/axios.config.js';
import ElementUI from 'element-ui';
import {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/utils/pxrem.js'
Vue.use(ElementUI)


Vue.prototype.$message = Message;
Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  render(h) {
    return h(App);
  },
}).$mount("#app");
