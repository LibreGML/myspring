import Vue from "vue";
import router from "@/router";
import store from "@/store";
import NProgress from "nprogress";
import "nprogress/nprogress.css"; // progress bar style
import axios from "@/utils/axios";
import { Message } from "element-ui";
import { flattenDeep } from "@/store//module/layout";
import { objDeepCopy } from "@/utils/index";
import localStorage from "@/utils/localStorage";

let homePath = process.env.VUE_APP_HOME_PATH; // 首页地址

// 处理token自动登录
// 必须是第一个导航守卫
router.beforeEach((to, from, next) => {
  const { token } = to.query;
  if (token) {
    localStorage.setItem("token", token);
  }
  next();
});

// 全局路由守卫
// 用于判断权限
router.beforeEach(async (to, from, next) => {
  NProgress.start();

  if (typeof to.meta.permission === "boolean") {
    if (store.state.layout.menuList.length <= 0) {
      try {
        const { data } = await axios.get("/api/account/authPermList");
        const initialMenuList = data || [];
        if (initialMenuList.length) {
          // 获取首页地址，如果是无固定首页模式，则认为第一个菜单为首页
          if (!process.env.VUE_APP_HOME_PATH)
            homePath = findFirstPath(initialMenuList);

          await store.commit(
            "setMenuList",
            addIndexRoute(parseRoutersDeep(initialMenuList))
          );
          if (process.env.VUE_APP_HOME_PATH) {
            // 如果是固定首页模式，则直接通过，不需要判断是否是'/',因为会有redirect
            next({ ...to, replace: true });
          } else {
            // 如果不是固定首页模式，需要重定向到动态的首页
            // 所谓bug就是在这里产生的，页面刷新时总是会回到首页，它更像是一个应用而不是网页
            next(homePath);
          }
        } else {
          // 理论上如果用户登录成功是一定有菜单的，如果出现这种错误，我认为它是程序错误
          Message.error("程序错误，请联系管理员");
          next("/500");
        }
      } catch (error) {
        // 这里只处理!401的未知情况，401时axios拦截器已经处理了
        if (error.code !== 401) {
          next("/500");
          Promise.reject(error);
        }
      }
    } else {
      if (to.meta.permission) {
        to.path === "/" ? next(homePath) : next();
      } else {
        next("/401"); // 无权限
      }
    }
  } else {
    next(); // 不需要权限验证的页面
  }

  NProgress.done();
});

router.beforeEach((to, from, next) => {
  // 小屏下路由切换时收起侧边栏
  if (store.state.facility.screenWidth < 1200) {
    store.commit("setCollapse", true);
  }

  /**
   * 处理携带参数的路由
   * 如果当前页面tab已存在，但是参数不一样的话会删除缓存再进入
   * 只处理了通过query传参，其他的最好不要使用
   */
  const found = store.state.layout.tabs.find((item) => item.path === to.path);
  if (found && found.to !== to.fullPath) {
    store.commit("setRedirectName", to.name);
    Vue.nextTick(() => {
      next();
      store.commit("setRedirectName", "");
      NProgress.done();
    });
  } else {
    next();
    NProgress.done();
  }
});

/**
 * 路由改变时动态设置网页标题
 */
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = `${process.env.VUE_APP_TITLE}-${to.meta.title}`;
  } else {
    document.title = process.env.VUE_APP_TITLE;
  }
  next();
});

router.afterEach((to) => {
  if (to.path.slice(0, 9) !== "/redirect") {
    NProgress.done();
  }
});

/**
 * 将后端菜单处理成此框架支持的格式
 * @param {array} menuList 后端返回的路由表
 * @returns 格式化后的路由表
 */
const parseRoutersDeep = function(menuList) {
  return menuList.map((item) => ({
    id: item.menuId,
    title: item.menuTitle,
    icon: item.menuIcon,
    path: item.routePath === "/" ? "" : item.routePath,
    type: item.routePath === "/" ? "2" : "1", // 1:菜单 2:目录
    children: parseRoutersDeep(item.listMenus || []),
    affix: item.routePath === homePath, // 将首页设置为固定tab
    buttonList: JSON.parse(item.funcs || "[]"),
  }));
};

/**
 * 向后端返回的路由表中添加首页路由
 * 在固定首页模式下，任何用户都应该拥有首页的权限，如果后端没加我就帮他加
 * @param {array} menuList 后端返回的路由表
 * @returns 添加完首页之后的路由表
 */
const addIndexRoute = function(menuList) {
  const menus = flattenDeep(menuList);
  if (!menus.some((item) => item.path === homePath)) {
    menuList.unshift({
      id: "index",
      title: "首页",
      path: homePath,
      icon: "el-icon-s-home",
      type: "1",
      affix: true,
    });
  }
  return menuList;
};

/**
 * 查找第一个菜单 返回path
 * @param {Array} initialMenuList 后端返回的没有被处理过的路由数组
 * @returns 第一个菜单的routePath
 */
const findFirstPath = function(initialMenuList = []) {
  let path = "/";
  const menuList = objDeepCopy(initialMenuList);

  for (let i = 0; i < menuList.length; i++) {
    const item = menuList[i];
    if (item.routePath === "/") {
      menuList.push(...(item.listMenus || []));
    } else {
      path = item.routePath;
      break;
    }
  }
  return path;
};
