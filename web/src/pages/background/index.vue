<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="后台提现费率" name="withdrawal">
        <el-form label-width="150px" class="demo-ruleForm">
          <el-form-item label="商家提现费率">
            <el-input-number
              v-model="class3.busfree"
              :precision="2"
              :step="0.01"
              :min="0.0"
              :max="1.0"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="供应商提现费率">
            <el-input-number
              v-model="class3.subfree"
              :precision="2"
              :step="0.01"
              :min="0.0"
              :max="1.0"
            ></el-input-number>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="123云盘配置" name="123pan">
        <el-form label-width="200px" class="demo-ruleForm" style="width: 70%;">
          <el-form-item label="上传域名">
            <el-input v-model="class1.uploadAddress"></el-input>
          </el-form-item>
          <el-form-item label="访问域名">
            <el-input v-model="class1.accessing"></el-input>
          </el-form-item>
          <el-form-item label="上传CLIENT_ID">
            <el-input v-model="class1.clientId"></el-input>
          </el-form-item>
          <el-form-item label="上传CLIENT_SECRET">
            <el-input v-model="class1.clientSecret"></el-input>
          </el-form-item>
          <el-form-item label="是否开启">
            <el-switch
              v-model="class1.open"
              active-text="使用"
              inactive-text="关闭"
            >
            </el-switch>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="樱花支付配置" name="yinghua">
        <el-form label-width="200px" class="demo-ruleForm" style="width: 70%;">
          <el-form-item label="支付域名">
            <el-input v-model="class2.host"></el-input>
          </el-form-item>
          <el-form-item label="支付账户id">
            <el-input v-model="class2.pid"></el-input>
          </el-form-item>
          <el-form-item label="支付key">
            <el-input v-model="class2.key"></el-input>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <el-button class="buttonPrimary" type="primary" @click="saveData">
      保存
    </el-button>
  </div>
</template>

<script>
import axios from "@/utils/axios";
export default {
  components: {},
  data() {
    return {
      activeName: "withdrawal",
      class1: {
        uploadAddress: "",
        accessing: "",
        clientId: "",
        clientSecret: "",
        open: true,
      },
      class2: {
        host: "",
        pid: "",
        key: "",
      },
      class3: {
        busfree: 0.0,
        subfree: 0.0,
      },
    };
  },
  created() {
    this.getBase();
  },
  methods: {
    handleClick() {
      this.getBase();
    },
    getBase() {
      const fieldName = this.activeName;
      axios.get(`/api/website/get-base?key=${fieldName}`).then((val) => {
        if (val.data && val.data.fieldValue) {
          this.dealData(JSON.parse(val.data.fieldValue));
        }
      });
    },
    dealData(val) {
      if (this.activeName === "withdrawal") {
        this.class3 = val;
      }
      if (this.activeName === "yinghua") {
        this.class2 = val;
      }
      if (this.activeName === "123pan") {
        this.class1 = val;
      }
    },
    saveData() {
      let fieldName = "";
      let fieldValue = "";
      if (this.activeName === "withdrawal") {
        fieldName = "withdrawal";
        fieldValue = this.class3;
      }
      if (this.activeName === "123pan") {
        fieldName = "123pan";
        fieldValue = this.class1;
      }
      if (this.activeName === "yinghua") {
        fieldName = "yinghua";
        fieldValue = this.class2;
      }
      axios
        .post("/api/website/base", {
          fieldName,
          fieldValue: JSON.stringify(fieldValue),
        })
        .then(() => {
          this.$message.success(`保存成功`);
        });
    },
  },
};
</script>

<style scoped>
.buttonPrimary {
  position: fixed !important;
  right: 50px;
  bottom: 50px;
}
</style>
