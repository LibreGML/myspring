import router from "@/router/index";
import localStorage from "@/utils/localStorage";

// 对象深拷贝
export const objDeepCopy = function(obj) {
  return JSON.parse(JSON.stringify(obj));
};

// 我需要一个会改变原数组的过滤
const arrayFilert = function(list, fn) {
  for (let i = 0; i < list.length; ) {
    if (!fn(list[i], i)) {
      list.splice(i, 1);
    } else {
      i++;
    }
  }
  return list;
};
/**
 * 用来过滤数组开始的树结构数据，采用的剔除不相干枝干的逻辑，所以会改变原数组，使用时请传入数组的副本
 * @param {Array} treeList 要过滤的树
 * @param {Funtction} fn 过滤规则
 */
const deepFilter = function(treeList, fn) {
  return arrayFilert(treeList, (item) => {
    if (item.children && item.children.length) {
      deepFilter(item.children, fn);
      if (item.children.length) {
        return true;
      } else {
        // 之前的逻辑是它只有在属于目录的时候才会有children，所以不需要考虑本身匹配的情况
        // 此系统中菜单下还可能会有菜单，所以要处理他自己就能匹配的情况
        return fn(item);
      }
    } else {
      return fn(item);
    }
  });
};

/** deepFilter的包装，使方法不改变传入的数据  */
export const treeFilter = function(treeList, fn) {
  return deepFilter(objDeepCopy(treeList), fn);
};

// 文件流下载
export const downLoadBinaryFills = function(response) {
  const blob = new Blob([response.data]);
  const filename = decodeURIComponent(
    response.headers["content-disposition"].split(";")[1].split("=")[1]
  );
  if (window.navigator.msSaveOrOpenBlob) {
    navigator.msSaveBlob(blob, filename);
  } else {
    const a = document.createElement("a");
    a.download = filename;
    a.href = window.URL.createObjectURL(blob);
    a.click();
  }
};

// 通过文件路径获取文件名
export const parseUrlFileName = function(url) {
  let [path] = url.split("?");
  path = decodeURI(path);
  return path.substring(path.lastIndexOf("/") + 1);
};

// 登出系统
export const logout = function() {
  // 向父框架发送登出命令
  window.parent.postMessage(
    {
      data: null,
      type: "logout",
    },
    "*"
  );
  localStorage.removeItem("token");
  localStorage.removeItem("jbj_userInfo");
  router.push("/login");
};
