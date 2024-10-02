<template>
  <DescriptionsForm
    :model="form"
    title="供应商信息"
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
        <el-form-item label="供应商名称" prop="name">
          {{ form.name }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="创建时间" prop="createTime">
          {{ form.createTime }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="审核状态" prop="examine">
          <div v-if="form.examine === 1" style="color: rgb(0, 255, 170);">
            待审核
          </div>
          <div v-if="form.examine === 2" style="color: rgb(0, 98, 255);">
            审核通过
          </div>
          <div v-if="form.examine === 3" style="color: rgb(255, 0, 0);">
            审核失败
          </div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="审核原因" prop="examineReason">
          {{ form.examineReason }}
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
  name: "SupplierDetails",
  components: {
    DescriptionsForm,
  },
  data() {
    return {
      form: {
        account: "",
        accountId: "",
        createTime: "",
        headPic: null,
        id: "",
        name: "",
        updateTime: "",
        examine: "",
        examineReason: "",
      },
    };
  },
  created() {
    axios
      .get(`/api/supplier/detail?key=${this.$route.query.id}`)
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
