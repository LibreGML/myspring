<template>
  <DescriptionsForm
    :model="form"
    title="公告详情"
    label-width="130px"
    @opened="cacheFormData"
    @closed="resetFormData"
  >
    <el-row>
      <el-col :md="8" :lg="6">
        <el-form-item label="标题" prop="title">
          {{ form.title }}
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="公告类型" prop="type">
          <DictParse :value="form.type" dict-type="notice-type" />
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="创建时间" prop="createTime">
          {{ form.createTime }}
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="内容" prop="content">
          <div v-html="form.content"></div>
        </el-form-item>
      </el-col>
    </el-row>
  </DescriptionsForm>
</template>

<script>
import axios from "@/utils/axios";
import DictParse from "@/components/DictParse";
import DescriptionsForm from "@/components/DescriptionsForm";
let initialFormData = null;
// 表单默认项
export default {
  name: "NoticeDetails",
  components: {
    DescriptionsForm,
    DictParse,
  },
  data() {
    return {
      form: {
        id: "",
        title: "",
        type: "",
        content: "",
        createTime: "",
      },
    };
  },
  created() {
    axios
      .get(`/api/notice/detail?key=${this.$route.query.id}`)
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
