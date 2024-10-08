import Button from "./ElButton";
import ElFormItem from "./ElFormItem";

const components = [Button, ElFormItem];

const install = function(Vue) {
  components.forEach((component) => {
    Vue.component(component.name, component);
  });
};

export default {
  install,
};
