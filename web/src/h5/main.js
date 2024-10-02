import Vue from "vue";
import App from "./App";
import { allUrlParam } from "./utils/index";

new Vue({
  created() {
    const { token } = allUrlParam();
    if (token) {
      localStorage.setItem("token", token);
    }
  },
  render(h) {
    return h(App);
  },
}).$mount("#app");
