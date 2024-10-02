<script>
import Space from "@/components/Space";

export default {
  components: { Space },
  props: {
    /**
     * 按钮列表
     * text 按钮文本
     * authButKey 权限标识
     * disabled 是否禁用
     * color 按钮文字颜色
     * onClick 按钮点击后执行的事件
     * icon 按钮文本前的图标
     * hidden 是否隐藏
     */
    butList: {
      type: Array,
      required: true,
    },
    // 按钮间距，单位px
    spaceSize: {
      type: Number,
      default: 10,
    },
    /**
     * 最多显示几个按钮
     * -1时显示所有按钮
     * 0时会以一种意料之外的方式的渲染
     */
    maxNumber: {
      type: Number,
      default: 3,
    },
  },

  render(h) {
    const butList = this.butList
      // 过滤掉不需要显示的按钮
      .filter((item) => !item.hidden)
      // 过滤掉没有权限的按钮
      .filter((item) => {
        return this.$checkPermission.call(this, item.authButKey);
      });

    // 如果没有按钮直接返回空节点
    if (!butList.length) return h();

    // 创建button
    const createButVNode = (butItem) => {
      const attribute = {
        attrs: {
          type: "text",
          disabled: !!butItem.disabled,
          icon: butItem.icon,
        },
        style: {},
        on: {},
      };
      // 设置按钮文字颜色
      if (butItem.color) attribute.style.color = butItem.color;
      // 设置点击事件
      if (butItem.onClick) attribute.on.click = butItem.onClick;

      return h("el-button", attribute, butItem.text);
    };

    let vnodeList = [];
    if (this.maxNumber !== -1 && butList.length > this.maxNumber) {
      vnodeList = butList
        .slice(0, this.maxNumber - 1)
        .map((item) => createButVNode(item));
      vnodeList.push(
        h(
          "el-dropdown",
          {
            on: {
              command: (onClick) => {
                if (onClick) onClick();
              },
            },
          },
          [
            h("el-button", { attrs: { type: "text" } }, [
              "更多",
              h("i", { class: "el-icon-arrow-down" }),
            ]),
            h(
              "el-dropdown-menu",
              { slot: "dropdown" },
              butList.slice(this.maxNumber - 1).map((item) =>
                h(
                  "el-dropdown-item",
                  {
                    attrs: {
                      command: item.onClick,
                      disabled: !!item.disabled,
                      icon: item.icon,
                    },
                  },
                  item.text
                )
              )
            ),
          ]
        )
      );
    } else {
      vnodeList = butList.map((item) => createButVNode(item));
    }

    return h("Space", { attrs: { size: this.spaceSize } }, vnodeList);
  },
};
</script>
