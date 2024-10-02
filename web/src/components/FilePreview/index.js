import Vue from "vue";
import Main from "./main";

let instance;
const MainConstructor = Vue.extend(Main);

const FilePreview = function(options = {}) {
  //   if (Vue.prototype.$isServer) return; // 是否运行在服务器

  instance = new MainConstructor({
    propsData: options,
  });

  instance.$mount();
  document.body.appendChild(instance.$el);

  instance.visible = true;

  return instance;
};

export default FilePreview;
