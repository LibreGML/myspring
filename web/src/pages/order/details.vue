<template>
  <div>
    <DescriptionsForm
      :model="form"
      title="订单信息"
      label-width="130px"
      @opened="cacheFormData"
      @closed="resetFormData"
    >
      <el-row>
        <el-col :span="24">
          <el-form-item label="订单号" prop="orderNumber">
            {{ form.orderNumber }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品名" prop="productName">
            {{ form.productName }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="下单信息" prop="orderInfo">
            {{ form.orderInfo }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="卡密信息" prop="kmData">
            {{ form.kmData }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="份数" prop="quantity">
            {{ form.quantity }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商家账户余额" prop="merchantBalance">
            {{ form.merchantBalance }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商家额度包余额" prop="merchantQuotaBalance">
            {{ form.merchantQuotaBalance }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="订单时间" prop="orderTime">
            {{ form.orderTime }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="状态" prop="status">
            <div v-if="form.status === '-1'" style="color: rgb(0, 0, 0);">
              下单失败
            </div>
            <div v-if="form.status === '1'" style="color: rgb(0, 255, 170);">
              下单成功
            </div>
            <div
              v-else-if="form.status === '2'"
              style="color: rgb(0, 98, 255);"
            >
              已申请售后
            </div>
            <div
              v-else-if="form.status === '3'"
              style="color: rgb(0, 98, 255);"
            >
              已后台介入
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </DescriptionsForm>
    <DescriptionsForm
      v-if="form.status !== '1'"
      :model="form2"
      title="售后信息"
      label-width="130px"
    >
      <el-row>
        <el-col :span="24">
          <el-form-item label="售后理由" prop="issue">
            {{ form2.issue }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="回复" prop="reply">
            {{ form2.reply }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="状态" prop="status">
            <div v-if="form2.status === '1'" style="color: rgb(0, 255, 170);">
              正常
            </div>
            <div
              v-else-if="form2.status === '2'"
              style="color: rgb(0, 98, 255);"
            >
              已申请介入
            </div>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-button
          v-if="
            $store.state.userInfo.account.roleType === '2' &&
              form2.status === '1'
          "
          style="float: right;"
          type="primary"
          size="small"
          @click="jirru"
        >
          申请介入
        </el-button>
        <el-button
          v-if="$store.state.userInfo.account.roleType === '3' && !form2.reply"
          style="float: right;"
          type="primary"
          size="small"
          @click="$refs.Reply.start(form2.id)"
        >
          回复
        </el-button>
      </el-row>
      <Reply ref="Reply" :callback="getissue" />
    </DescriptionsForm>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DescriptionsForm from "@/components/DescriptionsForm";
import Reply from "./components/Reply";
let initialFormData = null;
// 表单默认项
export default {
  components: {
    DescriptionsForm,
    Reply,
  },
  data() {
    return {
      form: {
        id: "",
        integrationResponse: null,
        merchantBalance: null,
        merchantId: "",
        merchantQuotaBalance: null,
        orderInfo: null,
        orderNumber: "",
        orderTime: "",
        paymentAmount: null,
        paymentMethod: null,
        productId: "",
        productName: "",
        quantity: 0,
        status: "",
        supplierId: null,
        kmData: "",
      },
      form2: {
        id: "",
        orderId: "",
        issue: "",
        reply: "",
        status: "",
        createTime: "",
      },
    };
  },
  created() {
    axios.get(`/api/order/detail?key=${this.$route.query.id}`).then((value) => {
      const { data } = value;
      Object.keys(this.form).forEach((key) => {
        this.form[key] = data[key];
      });
      if (data.status !== "1") {
        this.getissue();
      }
      this.cacheFormData();
    });
  },
  methods: {
    // 缓存form表单数据
    cacheFormData() {
      initialFormData = JSON.parse(JSON.stringify(this.form)); // 缓存初始表单数据 ;
    },

    getissue() {
      axios
        .get(`/api/aftersales/detail?key=${this.$route.query.id}`)
        .then((value) => {
          const { data } = value;
          Object.keys(this.form2).forEach((key) => {
            this.form2[key] = data[key];
          });
        });
    },

    jirru() {
      this.$confirm("回复不满意，申请介入, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios
          .post("/api/aftersales/update", {
            id: this.form2.id,
            status: 2,
          })
          .then(() => {
            this.$message({
              type: "success",
              message: "申请成功!",
            });
            this.getissue();
          });
      });
    },

    // 恢复表单数据
    resetFormData() {
      this.form = initialFormData;
    },
  },
};
</script>
