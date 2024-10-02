import axios from "axios";
import { debounce } from "throttle-debounce";
import Toast from "vant/lib/toast";
import "vant/lib/toast/style";

// 登录过期提示
const message401 = debounce(5000, true, () => {
  Toast.fail("页面已过期，请重新扫码进入！");
});

const instance = axios.create({
  timeout: 5000,
});

// 请求拦截器
instance.interceptors.request.use(
  function(config) {
    // 在发送请求之前做些什么
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  function(error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  function(response) {
    // 对响应数据做点什么
    const { data } = response;
    const { code, msg } = data;
    // 请求成功时进行响应处理
    switch (code) {
      case 200:
        return data;
      case 401:
        message401();
        return Promise.reject(data);
      default:
        Toast.fail(msg);
        return Promise.reject(data);
    }
  },
  function(error) {
    // 对响应错误做点什么
    Toast.fail(error.message);
    return Promise.reject(error);
  }
);

export default instance;
