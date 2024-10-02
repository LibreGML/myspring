<template>
  <div class="aside" :class="{ collapse: isCollapse }">
    <div id="layout-logo" class="sider-logo">
      <router-link to="/">
        <img src="@/assets/logo.png" alt="logo" />
      </router-link>
    </div>
    <el-scrollbar id="layout-menu" wrap-class="scrollbar-wrapper">
      <el-menu
        class="el-menu-vertical"
        unique-opened
        :collapse="isCollapse"
        :default-active="activeMenu"
      >
        <MenuItem
          v-for="(route, index) in menulist"
          :key="index"
          :item="route"
        ></MenuItem>
      </el-menu>
    </el-scrollbar>
    <!-- <div class="sider-links" @click="setCollapse(!isCollapse)">
      <i
        :class="{
          'el-icon-right': isCollapse,
          'el-icon-back': !isCollapse,
        }"
      ></i>
    </div> -->
  </div>
</template>

<script>
import MenuItem from "./MenuItem";
import { debounce } from "throttle-debounce";

// 自适应侧边栏断点，修改时还需要修改对应的css
const minWidth = 1200;

export default {
  components: { MenuItem },
  data() {
    return {
      resizeEnet: debounce(150, false, () => {
        const screenWidth = document.body.clientWidth;
        if (
          this.$store.state.facility.screenWidth >= minWidth &&
          screenWidth < minWidth
        ) {
          // 从大屏切换到小屏
          this.$store.commit("setCollapse", true);
        } else if (
          this.$store.state.facility.screenWidth < minWidth &&
          screenWidth >= minWidth
        ) {
          // 从小屏切换到大屏
          this.$store.commit("setCollapse", false);
        }
        this.$store.commit("setScreenWidth", document.body.clientWidth);
      }),
    };
  },
  computed: {
    //控制侧边栏展开收起状态
    isCollapse() {
      return this.$store.state.layout.isCollapse;
    },
    //获取后端路由表
    menulist() {
      return this.$store.state.layout.menuList;
    },
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
  },
  mounted() {
    if (this.$store.state.facility.screenWidth < minWidth) {
      // 小屏状态下默认收起
      this.$store.commit("setCollapse", true);
    }

    window.addEventListener("resize", this.resizeEnet);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeEnet);
  },
  methods: {
    // 控制侧边栏导航
    // 父组件会通过ref调用
    // eslint-disable-next-line vue/no-unused-properties
    setCollapse(collapse) {
      this.$store.commit("setCollapse", collapse);
    },
  },
};
</script>

<style scoped lang="scss">
.aside {
  width: 210px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: $menu-bg;
  transition: width 0.3s;
}
.collapse {
  width: 64px;
}

// 小屏下的样式
@media screen and (max-width: 1200px) {
  .aside {
    position: absolute;
    left: 0;
    z-index: 2000;
  }
  .collapse {
    width: 0px;
  }
}

.aside ::v-deep .el-menu {
  border-right: none;
  background-color: $menu-bg;
}
// .el-menu-vertical {
//   text-align: left;
// }
.el-menu-vertical:not(.el-menu--collapse) {
  width: 210px;
}
.aside ::v-deep .el-scrollbar {
  flex: 1 1 0%;
  overflow: hidden auto;
}
.aside ::v-deep .scrollbar-wrapper {
  overflow-x: hidden !important;
}
.aside ::v-deep .el-menu-item,
.aside ::v-deep .el-submenu__title {
  color: $menu-font-color;
}

.aside ::v-deep .el-menu-item:hover,
.aside ::v-deep .el-menu-item:focus,
.aside ::v-deep .el-submenu__title:hover,
.aside ::v-deep .el-submenu__title:focus {
  background-color: $menu-item-hover;
}

.aside ::v-deep .el-menu-item.is-active {
  color: $menu-item-active-coloe;
}

/* 底部箭头按钮样式 */
.sider-links {
  text-align: center;
  height: 48px;
  border-top: 1px solid #f0f0f0;
  font-size: 24px;
  color: #fff;
  cursor: pointer;
}
.sider-links:hover {
  color: $menu-item-active-coloe;
}
.sider-links i {
  display: inline-block;
  width: 64px;
  line-height: 48px;
  text-align: center;
}

/* logo样式 */
.sider-logo {
  height: 50px;
  display: flex;
  align-items: center;
  overflow: hidden;
  line-height: 32px;
  cursor: pointer;
  transition: padding 0.2s;
  background: $menu-logo-bg;
}
.sider-logo a {
  margin-left: 12px;
  display: flex;
  align-items: center;
}
.sider-logo img {
  display: block;
  height: 40px;
  transition: height 0.2s;
}
.sider-logo h1 {
  display: block;
  height: 32px;
  color: #303133;
  font-weight: 600;
  font-size: 18px;
  line-height: 32px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

// 黑夜模式
.dark-theme {
  .aside {
    background-color: $dark-menu-bg;
  }
  .aside ::v-deep .el-menu {
    background-color: $dark-menu-bg;
  }
  .aside ::v-deep .el-menu-item,
  .aside ::v-deep .el-submenu__title {
    color: $dark-menu-font-color;
  }

  .aside ::v-deep .el-menu-item:hover,
  .aside ::v-deep .el-menu-item:focus,
  .aside ::v-deep .el-submenu__title:hover,
  .aside ::v-deep .el-submenu__title:focus {
    background-color: $dark-menu-item-hover;
  }

  .aside ::v-deep .el-menu-item.is-active {
    color: $dark-menu-item-active-coloe;
  }

  .aside ::v-deep .el-menu-item,
  .aside ::v-deep .el-submenu__title {
    color: $dark-menu-font-color;
  }
  .sider-logo {
    background: $dark-menu-logo-bg;
  }
  .sider-logo h1 {
    color: $dark-menu-font-color;
  }
}
</style>
