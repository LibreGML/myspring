import Vue from "vue";
import Vuex from "vuex";
import layout from "./module/layout";
import facility from "./module/facility"; // 设备数据
import lock from "./module/lock"; // 锁屏状态

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo: {
      account: {},
    },
    unRead: 0,
    logo: "",
  },
  modules: {
    layout,
    facility,
    lock,
  },
});

export default store;
