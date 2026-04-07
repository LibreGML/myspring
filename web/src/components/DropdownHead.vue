<!-- <template>
  <div v-click-outside="packUp" class="dropdown">
    <div class="slot-box" :style="slotBoxStyle">
      <slot />
      <div class="handle">
        <el-button type="primary" @click="submit">查 询</el-button>
        <el-button @click="reset">重 置</el-button>
        <el-button type="text" @click="visible = !visible">
          <template v-if="!visible">
            展开<i class="el-icon-caret-bottom"></i>
          </template>
          <template v-else>收起<i class="el-icon-caret-top"></i> </template>
        </el-button>
      </div>
    </div>
  </div>
</template> -->

<!-- 
  搜索栏展开收起组件 
  默认在操作栏提供查询（onSubmit）和重置(onReset)两个事件,会在触发时自动收起
  也可以通过name为action的slot自定义操作栏，必要时可以通过ref操作组件实例
-->
<script>
export default {
  props: {
    // 背景颜色
    slotBgColor: {
      type: String,
      default: "#fff",
    },
  },
  data() {
    return {
      visible: false, // 展开or收起
    };
  },
  computed: {
    slotBoxStyle() {
      return {
        height: this.visible ? "auto" : "58px",
        "padding-right": this.visible ? "0" : "232px",
        "box-shadow": this.visible ? "0 5px 5px 0 rgb(0 0 0 / 10%)" : "none",
        "background-color": this.slotBgColor,
      };
    },
  },
  methods: {
    // 收起
    packUp() {
      this.visible = false;
    },
    submit() {
      this.packUp();
      this.$emit("onSubmit");
    },
    reset() {
      this.packUp();
      this.$emit("onReset");
    },
  },
  render(h) {
    /**
     * 如果你认为以下渲染函数可读性不高，则可以参考文件顶部被注释的模板代码，他们有着相同的功能和意义
     * 这样写是因为vue模板语法无法在 slot 上添加 class
     */
    (this.$slots.default || []).forEach((item) => {
      if (!item.data) item.data = {};
      item.data.class = "dropdown-slot"; // 在 defaultSlot 上添加class
    });

    return h(
      "div",
      {
        class: "dropdown",
        directives: [
          {
            name: "click-outside",
            value: this.packUp,
          },
        ],
      },
      [
        h(
          "div",
          {
            class: "slot-box",
            style: this.slotBoxStyle,
          },
          [
            this.$slots.default || [],
            h(
              "div",
              {
                class: "handle",
              },
              [
                h(
                  "el-button",
                  {
                    attrs: {
                      type: "primary",
                    },
                    on: {
                      click: this.submit,
                    },
                  },
                  "查 询"
                ),
                h(
                  "el-button",
                  {
                    on: {
                      click: this.reset,
                    },
                  },
                  "重 置"
                ),
                h(
                  "el-button",
                  {
                    attrs: {
                      type: "text",
                    },
                    on: {
                      click: () => {
                        this.visible = !this.visible;
                      },
                    },
                  },
                  [
                    this.visible ? "收起" : "展开",
                    h("i", {
                      class: {
                        "el-icon-caret-top": this.visible,
                        "el-icon-caret-bottom": !this.visible,
                      },
                    }),
                  ]
                ),
              ]
            ),
          ]
        ),
      ]
    );
  },
};
</script>

<style scoped>
.dropdown {
  position: relative;
  height: 58px;
  margin: 0 -20px;
}
.dropdown .slot-box {
  width: 100%;
  position: absolute;
  left: 0;
  top: 0;
  overflow: hidden;
  padding: 0 20px;
  box-sizing: border-box;
  /* transition: height 0.5s; */
  z-index: 999;
}

.dropdown .dropdown-slot:after {
  content: "";
  display: inline-block;
  width: 232px;
  height: 36px;
  vertical-align: top;
  margin-bottom: 22px;
}

.dropdown .handle {
  position: absolute;
  right: 20px;
  bottom: 0;
  margin-bottom: 22px;
  z-index: 1000;
}
</style>
