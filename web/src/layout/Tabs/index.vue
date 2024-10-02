<template>
  <div id="tabs-box" class="container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper">
      <draggable v-model="tabsItem" v-bind="options">
        <transition-group tag="span" name="flip-list">
          <router-link
            v-for="item in tabsItem"
            :key="item.to"
            ref="tag"
            class="tabs-item"
            :to="item.to"
            @contextmenu.prevent.native="openMenu(item, $event)"
          >
            {{ item.title }}
            <i
              v-if="!item.affix"
              class="el-icon-close"
              @click.prevent.stop="delTabs(item)"
            ></i>
          </router-link>
        </transition-group>
      </draggable>
    </scroll-pane>
    <ul
      v-show="contextmenu.visible"
      :style="{ left: contextmenu.actuallyLeft + 'px' }"
      class="contextmenu"
    >
      <li @click="refresh(contextmenu.item)">刷新</li>
      <!-- eslint-disable-next-line vue/no-undef-properties -->
      <li v-if="!contextmenu.item.affix" @click="delTabs(contextmenu.item)">
        关闭
      </li>
      <li @click="closeOthers(contextmenu.item)">关闭其他</li>
      <li @click="closeAll(contextmenu.item)">关闭所有</li>
    </ul>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import ScrollPane from "./ScrollPane";

export default {
  components: {
    draggable,
    ScrollPane,
  },
  data() {
    return {
      options: {
        animation: 300, //拖动过程中的延时动画
        forceFallback: true, //是否显示原生的html5的拖放
        ghostClass: "ghost-class", //拖动项的类名
        chosenClass: "chosen-class", // 拖动项影子类名
        scroll: true, //当排序的容器是个可滚动的区域，拖放可以引起区域滚动
        scrollSensitivity: 100, //就是鼠标靠近边缘多远开始滚动默认30
        scrollSpeed: 500, //滚动速度，单位应该是:像素/秒
        group: "people",
        tag: "span",
      },
      contextmenu: {
        visible: false,
        item: {}, //当前操作的tabs
        actuallyLeft: 0, //实际上的left
      },
    };
  },
  computed: {
    // tabs标签列表
    tabsItem: {
      get() {
        return this.$store.state.layout.tabs;
      },
      set(value) {
        this.$store.commit("replaceTabs", value);
      },
    },
  },
  watch: {
    $route() {
      this.$store.commit("addTabs", this.$route);
      this.moveToCurrentTag();
    },
    "contextmenu.visible"(value) {
      if (value) {
        document.body.addEventListener("click", this.closeMenu);
      } else {
        document.body.removeEventListener("click", this.closeMenu);
      }
    },
  },
  mounted() {
    this.$store.commit("addTabs", this.$route);
  },
  methods: {
    moveToCurrentTag() {
      this.$nextTick(() => {
        const tags = this.$refs.tag || [];
        for (const tag of tags) {
          if (tag.to === this.$route.path) {
            this.$refs.scrollPane.moveCenter(tag);
            break;
          }
        }
      });
    },
    //tabs点击右键执行的函数
    openMenu(item, event) {
      const menuMinWidth = 80;
      const offsetLeft = this.$el.getBoundingClientRect().left; // container margin left
      const { offsetWidth } = this.$el; // container width
      const maxLeft = offsetWidth - menuMinWidth; // left boundary
      const left = event.clientX - offsetLeft + 15; // 15: margin right

      if (left > maxLeft) {
        this.contextmenu.actuallyLeft = maxLeft;
      } else {
        this.contextmenu.actuallyLeft = left;
      }
      this.contextmenu.visible = true;
      this.contextmenu.item = item;
    },
    //关闭右键菜单
    closeMenu() {
      this.contextmenu.visible = false;
    },
    //刷新
    refresh(item) {
      this.$store.commit("setRedirectName", item.name);
      this.$nextTick(() => {
        this.$router.replace({ path: `/redirect${item.to}` });
      });
    },
    //删除tabs
    delTabs(item) {
      if (item.affix) return;
      this.$store.commit("delTabs", item);
    },
    //关闭其他
    closeOthers(item) {
      this.$store.commit("closeOthers", item);
    },
    //关闭所有
    closeAll() {
      this.$store.commit("emptyTabs"); //清空tabs
    },
  },
};
</script>

<style scoped lang="scss">
.container {
  position: relative;
  background-color: $tabs-container-bg;
}
.tabs-item {
  display: inline-block;
  overflow: hidden;
  height: 32px;
  line-height: 32px;
  margin-right: 8px;
  padding: 0 12px;
  background-color: #f1f3f4;
  color: #909399;
  cursor: pointer;
}
.tabs-item:hover {
  background-color: #fff;
}
// .tabs-item:hover::before {
//   background: $tabs-item-active-color;
// }
.tabs-item i {
  margin-left: 5px;
}

.router-link-exact-active {
  background: #fff;
}

.router-link-exact-active:hover {
  background: #fff;
  color: #909399;
}
.tabs-item::before {
  content: "";
  background: #dcdfe6;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin: 0 2px;
}
.router-link-exact-active::before {
  content: "";
  background: $tabs-item-active-color;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin: 0 2px;
}

.ghost-class {
  opacity: 0;
}
.chosen-class {
  background: #fff;
}
.flip-list-enter-active,
.flip-list-leave-active {
  transition: all 0.5s;
}
.flip-list-enter,
.flip-list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
.el-icon-close {
  border-radius: 50%;
  font-size: 12px;
}
.el-icon-close:hover {
  background-color: #c0c4cc;
  color: #fff;
}
.contextmenu {
  width: 80px;
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  top: 30px;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
}

.contextmenu li {
  margin: 0;
  padding: 7px 16px;
  cursor: pointer;
}
.contextmenu li:hover {
  background: #eee;
}

// 黑夜模式
.dark-theme {
  .container {
    background-color: $dark-tabs-container-bg;
  }
  .tabs-item {
    background-color: $dark-tabs-bg;
    color: $dark-tabs-font-color;
  }
  .tabs-item:hover {
    background-color: #1e1e1e;
  }
  .router-link-exact-active {
    background: #1e1e1e;
  }
  .contextmenu {
    color: #ccc;
    background: #232323;
    li:hover {
      background: #111;
    }
  }
}
</style>
