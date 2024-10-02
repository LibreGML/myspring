<script>
export default {
  // 假装修改组件
  props: {
    // 间距大小
    size: {
      type: Number,
      default: 20,
    },
    /**
     * 间距方向
     * vertical(垂直)，horizontal(水平)，together(两个方向都有)
     */
    direction: {
      type: String,
      default: "horizontal",
      validator(value) {
        // 这个值必须匹配下列字符串中的一个
        return ["vertical", "horizontal", "together"].indexOf(value) !== -1;
      },
    },
    // 对齐方式
    align: {
      type: String,
      default: "center",
      validator(value) {
        // 这个值必须匹配下列字符串中的一个
        return ["start", "end", "center", "baseline"].indexOf(value) !== -1;
      },
    },
  },
  render(h) {
    const mySpaceStyle = {};
    // 设置对其方式
    switch (this.align) {
      case "center":
        mySpaceStyle.alignItems = "center";
        break;
      case "start":
        mySpaceStyle.alignItems = "flex-start";
        break;
      case "end":
        mySpaceStyle.alignItems = "flex-end";
        break;
      case "baseline":
        mySpaceStyle.alignItems = "baseline";
        break;
    }

    // 设置间距方向
    switch (this.direction) {
      case "horizontal":
        mySpaceStyle.columnGap = `${this.size}px`;
        break;
      case "vertical":
        mySpaceStyle.rowGap = `${this.size}px`;
        break;
      default:
        mySpaceStyle.gap = `${this.size}px`;
    }

    return h(
      "div",
      {
        class: "my-space",
        style: mySpaceStyle,
      },
      (this.$slots.default || []).map((item) => {
        return h("div", { class: "space-item" }, [item]);
      })
    );
  },
};
</script>

<style scoped>
.my-space {
  display: inline-flex;
  flex-wrap: wrap;
}
</style>
