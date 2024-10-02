<template>
  <div class="header">
    <div>
      <div id="fold-link" class="link" @click="setCollapse(!isCollapse)">
        <i
          :class="{
            'el-icon-s-unfold': isCollapse,
            'el-icon-s-fold': !isCollapse,
          }"
        ></i>
      </div>
      <Breadcrumb id="layout-breadcrumb" />
    </div>
    <div>
      <Screenfull id="screenfull-link" class="link" />

      <el-dropdown
        class="dropdown link"
        trigger="click"
        @command="handleCommand"
      >
        <div class="user">
          <span>你好，</span>
          <span class="user-name ellipsis">{{ userName }}</span>
          <i class="el-icon-arrow-down el-icon--right"></i>
          <el-badge
            is-dot
            :hidden="roleType === '1' || unRead === 0"
            style="top: -15px;"
          >
            <div></div>
          </el-badge>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">个人中心</el-dropdown-item>
          <el-dropdown-item v-if="roleType !== '1'" command="b">
            <el-badge :value="unRead" :hidden="unRead === 0" class="item">
              <div>消息中心</div>
            </el-badge>
          </el-dropdown-item>
          <el-dropdown-item command="c" divided>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import Breadcrumb from "./Breadcrumb";
import Screenfull from "./Screenfull";
import { logout } from "@/utils/index";
import localStorage from "@/utils/localStorage";

export default {
  components: { Breadcrumb, Screenfull },
  data() {
    return {
      value: true,
    };
  },
  computed: {
    isCollapse() {
      return this.$store.state.layout.isCollapse;
    },
    userName() {
      return this.$store.state.userInfo.account.userName;
    },
    roleType() {
      return this.$store.state.userInfo.account.roleType;
    },
    unRead() {
      return this.$store.state.unRead;
    },
  },
  watch: {
    value(val) {
      if (val) {
        localStorage.setItem("custom-theme", "");
        this.updateHtmlBodyClass("");
      } else {
        localStorage.setItem("custom-theme", "dark-theme");
        this.updateHtmlBodyClass("dark-theme");
      }
    },
  },
  created() {
    if (localStorage.getItem("custom-theme") === "dark-theme") {
      this.value = false;
    }
    this.getunRead();
  },
  methods: {
    // 控制侧边栏导航
    setCollapse(collapse) {
      this.$store.commit("setCollapse", collapse);
    },
    // 更新bodyclass
    updateHtmlBodyClass(className) {
      const { body } = document;
      body.className = className;
    },
    // 下拉菜单事件处理
    handleCommand(command) {
      const handle = {
        a: () => {
          this.$router.push({ path: "/my-user" });
        },
        b: () => {
          this.$router.push({ path: "/message" });
        },
        // 登出
        c: logout,
      };
      handle[command] && handle[command]();
    },
    getunRead() {
      axios.get(`/api/notice/unread`).then((value) => {
        const { data } = value;
        this.$store.state.unRead = data;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.header {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: $header-link-color;
  background-color: $header-bg;
}
.dark-theme .header {
  color: $dark-header-link-color;
  background-color: $dark-header-bg;
}

.header > div {
  display: flex;
  align-items: center;
}

.link {
  height: 50px;
  display: flex;
  align-items: center;
  padding: 0 20px;
  line-height: 50px;
  font-size: 24px;
  cursor: pointer;
}
.link:hover {
  background: $header-link-hover;
}
.dark-theme .link:hover {
  background: $dark-header-link-hover;
}
.dropdown {
  font-size: 14px;
  color: $header-link-color;
}
.dark-theme .dropdown {
  color: $dark-header-link-color;
}

.user {
  display: flex;
  align-items: center;
}
.user-name {
  display: inline-block;
  max-width: 80px;
}
</style>
