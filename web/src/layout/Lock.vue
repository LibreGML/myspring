<template>
  <transition name="slide-fade">
    <div class="lock-container">
      <div class="form">
        <div class="title">锁屏中</div>
        <div class="group">
          <el-input
            v-model="password"
            placeholder="请输入锁屏密码"
            clearable
            :type="inputType"
            @keyup.enter.native="unlock"
          ></el-input>
          <el-button type="primary" plain icon="el-icon-unlock" @click="unlock">
          </el-button>
          <el-tooltip content="退出登录" placement="bottom" effect="light">
            <el-button
              type="primary"
              plain
              icon="el-icon-switch-button"
              @click="unLogin"
            >
            </el-button>
          </el-tooltip>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import localStorage from "@/utils/localStorage";

const key = "lock-password";

export default {
  data() {
    return {
      password: "",
      inputType: "password",
    };
  },
  activated() {
    this.inputType = "password";
  },
  methods: {
    unlock() {
      if (localStorage.getItem(key) === this.password) {
        this.password = "";
        this.$store.commit("updateLock", null);
        this.inputType = "text";
      } else {
        this.$message.error("锁屏密码错误！");
      }
    },
    // 登出
    unLogin() {
      this.password = "";
      this.$store.commit("updateLock", null);
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.lock-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 2001;
  background-color: #2d3a4b;
}
.form {
  width: 400px;
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 4px;
  background-color: #fff;
}
.title {
  line-height: 60px;
  padding-left: 160px;
  text-align: left;
  font-size: 24px;
}
.title::after {
  content: "";
  animation-name: example;
  animation-duration: 4s;
  animation-iteration-count: infinite;
}

.group {
  display: flex;
}
.group .el-button {
  margin-left: -1px;
  border-radius: 0;
}
.group >>> .el-input__inner {
  border-radius: 0;
}
.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.3s;
}
.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateY(-100%);
}

@keyframes example {
  0% {
    content: "";
  }
  25% {
    content: ".";
  }
  50% {
    content: "..";
  }
  75% {
    content: "...";
  }
  100% {
    content: "";
  }
}
</style>
