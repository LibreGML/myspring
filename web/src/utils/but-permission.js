/**
 * 权限按钮相关代码
 */

/**
 * 传入authkey判断是否拥有权限
 * 只能在某个vue实例上下中文使用
 * @param {String, Array} key 权限标识
 * 传入数组key的情况大多是在按钮组上，用来控制拥有多个按钮的组件的隐藏或显示，
 * 所以对于这种情况的逻辑是：只要拥有数组里面某一个按钮的权限，那就返回true
 * @returns true或者false
 */

// 我以后可能会添加其他导出
// eslint-disable-next-line import/prefer-default-export
export const checkPermission = function(key) {
  if (!key || key.length === 0) return true;

  /**
   * 鉴别单个按钮的权限
   * @param {String} key 单个的 authkey
   * @returns true或者false
   */
  const validation = (key) =>
    (this.$route.meta.buttonList || []).some((item) => item.funcCode === key);

  if (Array.isArray(key)) {
    return key.some((i) => validation(i));
  } else {
    return validation(key);
  }
};
