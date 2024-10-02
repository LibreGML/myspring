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
        <h1 class="title">重置密码</h1>
        <el-divider></el-divider>

        <div class="form-item-box">
          <template v-if="steps === 1">
            <el-form-item prop="userAccount" label="账号">
              <el-input
                v-model="form.userAccount"
                placeholder="请输入账号"
                clearable
                prefix-icon="el-icon-user-solid"
              ></el-input>
            </el-form-item>

            <el-form-item style="margin-top:36px;">
              <el-button
                type="primary"
                style="width:100%"
                @click="validationAccount"
              >
                下一步
              </el-button>
            </el-form-item>
          </template>

          <template v-else-if="steps === 2">
            <el-form-item prop="phone" label="注册手机号" required>
              <el-input :value="DesensitizationPhone" disabled></el-input>
            </el-form-item>

            <el-form-item prop="code" label="短信验证码">
              <el-input v-model="form.code" placeholder="请输入收到的验证码">
                <template slot="append">
                  <el-button
                    type="warning"
                    :disabled="countdown > 0"
                    @click="getPhoneCode"
                  >
                    {{ countdown === 0 ? "获取验证码" : countdown + "秒" }}
                  </el-button>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item style="margin-top:36px;">
              <el-button
                type="primary"
                style="width:100%"
                @click="resetPassword"
              >
                密码重置
              </el-button>
            </el-form-item>
          </template>

          <template v-else-if="steps === 3">
            <div style="text-align: center;">
              <el-image :src="require('@/assets/password-success.png')" />
              <div style="margin-top:16px;font-size:24px;color:#3D3C41">
                【{{ form.userAccount }}】密码重置成功！
              </div>
              <div style="margin-top:20px;font-size:14px;color:#3D3C41">
                密码已经重置为：{{ newPassword }}，请尽快登录修改个人密码。
              </div>
            </div>
          </template>

          <el-form-item style="margin-top:36px;">
            <el-button
              style="width:100%"
              icon="el-icon-Import"
              @click="$router.push('/login')"
            >
              返回登录页
            </el-button>
          </el-form-item>

          <el-divider class="divider">
            {{ contacts }}
          </el-divider>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  data() {
    return {
      steps: 1,
      form: {
        userAccount: "",
        phone: "",
        code: "",
      },
      rules: {
        userAccount: [
          { required: true, message: "请输入账号", trigger: "blur" },
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },

      countdown: 0, // 获取验证码之后的倒计时

      newPassword: "",
      contacts: "客服电话：xxx-xxxx-xxx",
    };
  },
  computed: {
    // 脱敏后手机号
    DesensitizationPhone() {
      return this.form.phone.replace(/(\d{3})\d{4}(\d{4})/, "$1****$2");
    },
  },
  methods: {
    // 验证手机号
    validationAccount() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .get(`/api/sso/getPhoneByAccount/${this.form.userAccount}`)
            .then((value) => {
              this.form.phone = value.data;
              this.steps = 2;
            });
        }
      });
    },

    // 发送手机验证码
    getPhoneCode() {
      axios.put(`/api/sso/sendMsg/${this.form.phone}`).then(() => {
        this.$message.success("验证码发送成功，请注意查收");
        this.countdown = 120;

        const sleep = (n) => new Promise((res) => setTimeout(res, n));

        // 循环120次
        const countdown = async () => {
          for (let i = 120; i > 0; i--) {
            this.countdown = i;
            await sleep(1000);
          }
          this.countdown = 0;
        };

        countdown();
      });
    },

    // 重置密码
    resetPassword() {
      // 二次确认
      this.$confirm(
        `你正在为账号【${this.form.userAccount}】重置密码，是否确认？`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            axios
              .put("/api/sso/forgetPassword", {
                code: this.form.code,
                userAccount: this.form.userAccount,
              })
              .then((value) => {
                this.newPassword = value.data;
                this.steps = 3;
              });
          }
        });
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
        margin: 24px 0;
        padding: 0 64px;
        font-size: 32px;
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
</style>
