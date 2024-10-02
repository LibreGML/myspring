import axios from "axios";
import { Message } from "element-ui";
import { debounce } from "throttle-debounce";
import { logout } from "@/utils/index";
import localStorage from "@/utils/localStorage";

// const baseUrl = "/waibao/web/";
const baseUrl = "";
const timeout = 0;
const errMesage = "接口异常，请联系管理员！";

/**
 * 判断是否第一次进入系统
 * 前端是不应该想当然的判断用户是登录过期还是未登录的，因为有太多的不确定情况
 * 但是我们又需要在用户首次进入进入系统时不显示未登录message，所以我们需要判断是否第一次进入系统
 * 在 sessionStorage 添加 isInFirst 字段，如果有值且等于‘false’，则不是第一次进入系统，否则就是第一次进入系统
 * 在任何一个请求成功后主动将 isInFirst 设置为 false
 * 因为 sessionStorage 特性，浏览器或者标签页关闭时会清空，但是用户再次进入系统时可能会依然是已登录状态，此时不会显示未登录message
 * 最终导致的效果是：第一次进入系统不会显示未登录message，其他情况登录过期都会显示未登录message,这里所谓的第一次是刚打开浏览器或者tabs后访问系统，不包括f5刷新
 * 提示：本来我是可以将 isInFirst 设备成某个 js 变量的，但是那样无法处理f5刷新的情况，所以我改成了 sessionStorage
 */
const canIsInFirst = () => {
  return sessionStorage.getItem("isInFirst") !== "false";
};

// 登录过期提示
const message401 = debounce(5000, true, () => {
  Message.error("未登录或token过期，请重新登录！");
});

// 业务相关请求 axios 开始
const instance = axios.create({
  baseURL: baseUrl,
  timeout,
});

// 请求拦截器
instance.interceptors.request.use(
  function(config) {
    // 在发送请求之前做些什么
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    // config.headers.appCode = "frame-admin-web"; // 正式使用时删除此行代码
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
        sessionStorage.setItem("isInFirst", "false");
        return data;
      case 401: // 没有权限 包括未登录/登录超时
        if (!canIsInFirst()) message401();
        logout();
        return Promise.reject(data);
      default:
        Message.error(msg);
        return Promise.reject(data);
    }
  },
  function(error) {
    // 对响应错误做点什么
    Message.error(errMesage);
    return Promise.reject(error);
  }
);

export default instance;
// 业务相关请求 axios 结束

// 文件流
export const fileAxios = axios.create({
  baseURL: baseUrl,
  timeout,
});

fileAxios.interceptors.request.use(
  function(config) {
    // 在发送请求之前做些什么
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    config.responseType = "blob";
    return config;
  },
  function(error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器
fileAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    // 对响应错误做点什么
    Message.error("导出失败" || errMesage);
    return Promise.reject(error);
  }
);
