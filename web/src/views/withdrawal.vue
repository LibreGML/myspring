<template>
  <el-dialog title="余额提现" :visible.sync="visible" width="800px">
    <div class="withdrawal-container">
      <div class="withdrawal-info">
        <div class="withdrawal-item">
          <span class="label">可提现余额:</span>
          <span class="value">{{ data.balance }}</span>
        </div>
        <div class="withdrawal-item">
          <span class="label">手续费:</span>
          <span class="value">{{ data.fee }}</span>
        </div>
        <!-- 添加其他提现相关信息 -->
      </div>
      <!-- 添加提现表单 -->
      <el-form class="withdrawal-form">
        <!-- 添加表单项 -->
        <el-form-item label="提现金额">
          <el-input-number
            v-model="withdrawalAmount"
            :precision="2"
            placeholder="请输入提现金额"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="提现二维码">
          <MyUpload v-model="imageCode" :limit="1" type="img" />
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer">
      <el-button type="primary" @click="visible = false">关 闭</el-button>
      <el-button type="primary" @click="apply">申 请</el-button>
    </span>
  </el-dialog>
</template>

<script>
// 表单默认项
import axios from "@/utils/axios";
import MyUpload from "@/components/my-upload";
export default {
  components: {
    MyUpload,
  },
  data() {
    return {
      visible: false,
      data: {
        balance: 0,
        fee: 0.0,
      },
      withdrawalAmount: 0,
      imageCode: [],
    };
  },
  methods: {
    // 新增时启动函数
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    start() {
      this.visible = true;
      this.getTable();
    },
    getTable() {
      axios.get("/api/account/withdrawal").then((value) => {
        this.data.balance = value.data.balance || 0;
        this.data.fee = value.data.free || 0.0;
      });
    },
    apply() {
      axios
        .post("/api/withdrawal/add", {
          withdrawalAmount: this.withdrawalAmount,
          imageCode: this.imageCode[0].response.url,
        })
        .then(() => {
          this.$message.success("申请成功！请耐心等待！");
          this.visible = false;
        });
    },
  },
};
</script>

<style scoped>
.withdrawal-container {
  padding: 20px;
}

.withdrawal-info {
  margin-bottom: 20px;
}

.withdrawal-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.label {
  font-weight: bold;
  margin-right: 10px;
}

.value {
  color: #409eff;
}

.withdrawal-form {
  width: 100%;
}
</style>
