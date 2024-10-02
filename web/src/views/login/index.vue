<template>
  <div class="container">
    <div class="content">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        class="form"
        @submit.native.prevent
      >
        <div style="display: flex;height: 90px;">
          <h1 class="title">欢迎登录</h1>
          <el-image :src="$store.state.logo" width="200px" alt="logo" />
        </div>
        <el-divider></el-divider>
        <div class="form-item-box">
          <el-form-item prop="userAccount" label="账号">
            <el-input
              v-model="form.userAccount"
              placeholder="请输入用户名"
              clearable
              prefix-icon="el-icon-user-solid"
            ></el-input>
          </el-form-item>
          <FormItem prop="password" label="密码" style="position:relative;">
            <el-input
              v-model="form.password"
              placeholder="请输入密码"
              clearable
              show-password
              prefix-icon="el-icon-lock"
            ></el-input>
          </FormItem>
          <el-form-item style="margin-top:48px;">
            <el-button
              type="primary"
              style="width:100%"
              :loading="loginLoading"
              @click="onSubmit"
            >
              登 录
            </el-button>
          </el-form-item>
          <!-- <el-divider class="divider">
            {{ contacts }}
          </el-divider> -->
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import { FormItem } from "element-ui";
import localStorage from "@/utils/localStorage";

export default {
  components: {
    FormItem,
  },
  data() {
    return {
      form: {
        userAccount: "",
        password: "",
      },
      rules: {
        userAccount: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
      loginLoading: false,
    };
  },
  mounted() {
    this.getlogo();
  },
  methods: {
    getlogo() {
      axios.get(`/api/website/get-base?key=mian-logo`).then((val) => {
        if (val.data && val.data.fieldValue) {
          this.$store.state.logo = JSON.parse(val.data.fieldValue)[0];
        }
      });
    },
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loginLoading = true;
          axios
            .post("/api/account/login", {
              userAccount: this.form.userAccount,
              userPassword: this.form.password,
            })
            .then((value) => {
              const { token } = value.data;
              localStorage.setItem("token", token);
              this.$store.commit("setMenuList", []); // 清空侧边栏菜单
              this.$store.commit("updateLock", null); // 清除锁屏
              this.$router.push("/");
            })
            .finally(() => {
              this.loginLoading = false;
            });
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.container {
  display: flex;
  align-items: center;
  height: 100%;
  .content {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    right: 12%;
    border-radius: 5px;
    box-sizing: border-box;
    background-color: #fff;
    box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
    .form {
      width: 525px;
      padding-bottom: 40px;
      .title {
        width: 200px;
        margin: 24px 0;
        padding: 0 64px;
        font-size: 35px;
      }
      .form-item-box {
        margin-top: 48px;
        padding: 0 64px;
        .divider {
          margin-top: 48px;
          ::v-deep .el-divider__text {
            color: #c4c6cf;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 1440px) {
  .container {
    .content {
      width: 600px;
      height: calc(100vh - 140px);
      right: 0;
      border-radius: 0;
      box-shadow: none;
      background-size: 100%;
      .el-divider {
        display: none;
      }
    }
  }
}

@media screen and (max-width: 992px) {
  .container .content {
    width: 100%;
    .form .form-item-box {
      padding: 0 32px;
    }
  }
}

.my-label {
  display: flex;
  justify-content: space-between;
}

.code-append {
  width: 60px;
}
.code-append ::v-deep .circular {
  height: 28px;
  width: 28px;
}
.code-append ::v-deep .el-loading-spinner {
  top: 0;
  margin-top: 0;
}
</style>
