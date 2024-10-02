import Vue from "vue";
import Clipboard from "clipboard";

const clipboardSuccess = function() {
  Vue.prototype.$message({
    message: "复制成功！",
    type: "success",
    duration: 1500,
  });
};

const clipboardError = function() {
  Vue.prototype.$message({
    message: "复制失败",
    type: "error",
  });
};

export default function handleClipboard(text, event) {
  const clipboard = new Clipboard(event.target, {
    text: () => text,
  });
  clipboard.on("success", () => {
    clipboardSuccess();
    clipboard.destroy();
  });
  clipboard.on("error", () => {
    clipboardError();
    clipboard.destroy();
  });
  clipboard.onClick(event);
}
