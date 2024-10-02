const baseSize = 16
// 设置 rem 函数
function setRem() {
  // 页面宽度相对于 2048宽的缩放比例，根据需要修改。
  const scale = document.documentElement.clientWidth / 2048
  // 设置页面根节点字体大小（“Math.min(scale, 2)” 指最高放大比例为2，根据需要修改）
  document.documentElement.style.fontSize = baseSize * Math.min(scale, 2) + 'px'
}
// 初始化
setRem()
// 改变窗口大小时重新设置 rem
window.onresize = function () {
  setRem()
}

