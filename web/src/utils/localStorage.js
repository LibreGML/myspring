let isIframe = false; // 当前系统是否在iframe中

try {
  top.location.hostname;
  if (top.location.hostname !== window.location.hostname) {
    isIframe = true;
  }
} catch (e) {
  isIframe = true;
}

const ifrKeyPrefix = "ifr-"; // 在iframe中的localStorage key前缀

// 获取
const getItem = (key) => {
  if (isIframe) {
    return localStorage.getItem(`${ifrKeyPrefix}${key}`);
  } else {
    return localStorage.getItem(key);
  }
};

// 设置
const setItem = (key, value) => {
  if (isIframe) {
    return localStorage.setItem(`${ifrKeyPrefix}${key}`, value);
  } else {
    return localStorage.setItem(key, value);
  }
};

// 删除单个
const removeItem = (key) => {
  if (isIframe) {
    return localStorage.removeItem(`${ifrKeyPrefix}${key}`);
  } else {
    return localStorage.removeItem(key);
  }
};

export default {
  getItem,
  setItem,
  removeItem,
};

/**
 * 没必要做清除所有
 * 如果你非要做，请严格区分isIframe
 */
