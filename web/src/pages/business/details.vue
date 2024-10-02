<template>
  <DescriptionsForm
    :model="form"
    title="商家信息"
    label-width="130px"
    @opened="cacheFormData"
    @closed="resetFormData"
  >
    <el-row>
      <el-col :md="8" :lg="6">
        <el-form-item label="账号" prop="account">
          {{ form.account }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="账号Id" prop="accountId">
          {{ form.accountId }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="商家名称" prop="name">
          {{ form.name }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="会员等级" prop="level">
          {{ form.level }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="额度包" prop="quota">
          {{ form.quota }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="钱包" prop="wallet">
          {{ form.wallet }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="到期时间" prop="expirationTime">
          {{ form.expirationTime }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="创建时间" prop="createTime">
          {{ form.createTime }}
        </el-form-item>
      </el-col>
    </el-row>
  </DescriptionsForm>
</template>

<script>
import axios from "@/utils/axios";
import DescriptionsForm from "@/components/DescriptionsForm";
let initialFormData = null;
// 表单默认项
export default {
  name: "BusinessDetails",
  components: {
    DescriptionsForm,
  },
  data() {
    return {
      form: {
        account: "",
        accountId: "",
        createTime: "",
        expirationTime: "2",
        headPic: null,
        id: "",
        level: null,
        name: "",
        quota: null,
        updateTime: "",
        wallet: null,
      },
    };
  },
  created() {
    axios
      .get(`/api/business/detail?key=${this.$route.query.id}`)
      .then((value) => {
        const { data } = value;
        Object.keys(this.form).forEach((key) => {
          this.form[key] = data[key];
        });
        this.cacheFormData();
      });
  },
  methods: {
    // 缓存form表单数据
    cacheFormData() {
      initialFormData = JSON.parse(JSON.stringify(this.form)); // 缓存初始表单数据 ;
    },

    // 恢复表单数据
    resetFormData() {
      this.form = initialFormData;
    },
  },
};
</script>
