import Vue from "vue";

// 字典过滤器
import dict from "@/utils/dict";
Vue.filter("dictParse", function(value, dictName) {
  const found = dict[dictName].find((item) => item.value === value);
  return found ? found.label : value;
});
