<template>
  <el-card>
    <div>
      <el-descriptions title="个人信息" :column="1" border>
        <el-descriptions-item
          v-if="userInfo.account && userInfo.account.headPic"
          label="头像"
        >
          <el-avatar size="large" :src="userInfo.account.headPic"></el-avatar>
        </el-descriptions-item>
        <el-descriptions-item
          v-if="userInfo.account && userInfo.account.account"
          label="账号"
        >
          {{ userInfo.account.account }}
        </el-descriptions-item>
        <el-descriptions-item
          v-if="userInfo.account && userInfo.account.createTime"
          label="注册时间"
        >
          {{ userInfo.account.createTime }}
        </el-descriptions-item>

        <el-descriptions-item v-if="userInfo.supplier" label="账户余额">
          {{ userInfo.supplier.balance || 0 }}
          <el-button
            style="float: right;"
            type="primary"
            @click="$refs.Withdrawal.start()"
          >
            余额提现
          </el-button>
        </el-descriptions-item>

        <template v-if="userInfo.business">
          <el-descriptions-item
            v-if="userInfo.business && userInfo.business.name"
            label="名称"
          >
            {{ userInfo.business.name }}
          </el-descriptions-item>
          <el-descriptions-item
            v-if="userInfo.business && userInfo.business.expirationTime"
            label="会员时间"
          >
            {{ userInfo.business.expirationTime }}
            <el-button
              style="float: right;"
              type="primary"
              @click="$refs.Member.start('member')"
            >
              购买
            </el-button>
          </el-descriptions-item>
          <el-descriptions-item label="额度包">
            {{ userInfo.business.quota || 0 }}
            <el-button
              style="float: right;"
              type="primary"
              @click="$refs.Member.start('quota')"
            >
              购买
            </el-button>
          </el-descriptions-item>
          <el-descriptions-item label="账户余额">
            {{ userInfo.business.wallet || 0 }}
            <el-button
              style="float: right;margin-left: 10px;"
              type="primary"
              @click="$refs.Member.start('yue')"
            >
              充值
            </el-button>
            <el-button
              style="float: right;"
              type="primary"
              @click="$refs.Withdrawal.start()"
            >
              余额提现
            </el-button>
          </el-descriptions-item>
        </template>
      </el-descriptions>
      <div style="margin-top:20px;text-align: right;">
        <el-button type="primary" @click="visible = true">修改密码</el-button>
      </div>
    </div>
    <div v-if="userInfo.business">
      <el-descriptions title="对接信息" :column="1" border>
        <el-descriptions-item
          v-if="userInfo.business && userInfo.business.website"
          label="对接网站"
        >
          {{ userInfo.business.website }}
        </el-descriptions-item>
        <el-descriptions-item
          v-if="userInfo.account && userInfo.account.id"
          label="对接账号"
        >
          {{ userInfo.account.id }}
        </el-descriptions-item>
        <el-descriptions-item
          v-if="userInfo.business && userInfo.business.abutmentKey"
          label="对接密匙"
        >
          {{ userInfo.business.abutmentKey }}
          <el-button
            style="float: right;"
            type="primary"
            @click="updateAbutmentKey"
          >
            更新
          </el-button>
        </el-descriptions-item>
        <el-descriptions-item
          v-if="userInfo.business && userInfo.business.website"
          label="对接开发文档"
        >
          <el-link
            :href="userInfo.business.website + '/api/doc.html'"
            type="primary"
            target="_blank"
          >
            点击跳转
          </el-link>
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <el-dialog
      title="修改密码"
      :visible.sync="visible"
      width="600px"
      @closed="resetForm"
    >
      <el-form ref="form" label-width="90px" :model="form" :rules="rules">
        <el-form-item label="原始密码" prop="oldPassword">
          <el-input
            v-model="form.oldPassword"
            show-password
            placeholder="请输入原始密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="form.newPassword"
            show-password
            placeholder="请输入新密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            show-password
            placeholder="请再次输入新密码"
          ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>

    <Member ref="Member" :callback="resh"></Member>
    <Withdrawal ref="Withdrawal"></Withdrawal>
  </el-card>
</template>

<script>
import axios from "@/utils/axios";
import Member from "./member";
import Withdrawal from "./withdrawal";
const initialForm = {
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
};
export default {
  name: "MyUser",
  components: { Member, Withdrawal },
  props: {},
  data() {
    const confirmPassword = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      visible: false,
      form: { ...initialForm },
      rules: {
        oldPassword: [
          { required: true, message: "请输入原始密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请再次输入新密码", trigger: "blur" },
          { validator: confirmPassword, trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  watch: {},
  created() {},
  mounted() {},
  methods: {
    // 提交
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const { oldPassword, newPassword } = this.form;
          axios({
            method: "post",
            url: "api/account/update-password",
            data: {
              oldPassword,
              newPassword,
            },
          }).then(() => {
            this.$message.success("密码修改成功");
            this.visible = false;
          });
        }
      });
    },

    // 重置表单
    resetForm() {
      this.form = { ...initialForm };
      this.$refs["form"].resetFields();
    },

    updateAbutmentKey() {
      this.$confirm("确认更新, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios({
          method: "post",
          url: "/api/business/update/key",
        }).then(() => {
          this.resh();
        });
      });
    },

    resh() {
      axios.get("/api/account/getUserInfo").then(({ data }) => {
        this.$message({
          type: "success",
          message: "更新成功!",
        });
        localStorage.setItem("jbj_userInfo", JSON.stringify(data));
        this.$store.state.userInfo = data;
      });
    },
  },
};
</script>

<!-- <style scoped></style> -->
