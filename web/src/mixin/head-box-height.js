import { debounce } from "throttle-debounce";

const elementResizeDetectorMaker = require("element-resize-detector");
const erd = elementResizeDetectorMaker({
  strategy: "scroll", //<- For ultra performance.
});

/**
 * mixin 中的所有可访问属性都必须以'm_'开头
 * 这样的好处有两点：
 * 1. 提升代码可读性
 * 2. 回避 no-unused-properties 的报错
 */
export default {
  data() {
    return {
      m_boxHeightAll: 0,
    };
  },
  mounted() {
    const handle = debounce(500, false, () => {
      // 重新布局el-table
      this.$refs["table"] && this.$refs["table"].doLayout();

      // 实时计算出所有名为'auto-box'块高度
      const heads = this.$el.querySelectorAll(".auto-box");
      const allHeadList = [];
      heads.forEach((item) => allHeadList.push(item.offsetHeight));
      this.m_boxHeightAll = allHeadList.reduce((a, b) => a + b, 0);
    });

    erd.listenTo(this.$el, handle);
  },
};
