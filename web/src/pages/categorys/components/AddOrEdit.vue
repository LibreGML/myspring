<template>
  <el-dialog
    :title="!id ? '新增' : '编辑'"
    :visible.sync="visible"
    width="800px"
    @closed="resetForm"
  >
    <el-form ref="form" :model="form" label-width="95px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="分类图标"
            prop="image"
            :rules="[
              { required: true, message: '请选择分类图标', trigger: 'change' },
            ]"
          >
            <MyUpload v-model="form.image" :limit="1" type="img" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="分类名称"
            prop="name"
            :rules="[
              { required: true, message: '请输入分类名称', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.name" placeholder="请输入分类名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="父分类" prop="parentId">
            <el-cascader
              v-model="form.parentId"
              :props="{ checkStrictly: true, value: 'id', label: 'name' }"
              placeholder="请选择父分类"
              :options="options"
              filterable
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="排序"
            prop="sort"
            :rules="[
              { required: true, message: '请输入排序', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form.sort"
              :min="1"
              placeholder="请输入排序"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
// 表单默认项
import axios from "@/utils/axios";
import MyUpload from "@/components/my-upload";
const defaultForm = {
  name: "",
  image: [],
  parentId: [],
  sort: 1,
};
export default {
  components: {
    MyUpload,
  },
  props: {
    callback: {
      type: Function,
      default: null,
    },
  },
  data() {
    return {
      id: "", // 编辑时存在，为空时为新增
      visible: false,
      options: [],
      form: { ...defaultForm },
    };
  },
  created() {},
  methods: {
    getSourceList() {
      axios.get("/api/category/all?name=").then((res) => {
        this.options = res.data;
      });
    },
    // 新增时启动函数
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    addStart() {
      this.getSourceList();
      this.visible = true;
    },

    // 编辑时启动
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    editStart(row) {
      this.getSourceList();
      this.id = row.id;
      Object.keys(defaultForm).forEach((key) => {
        this.form[key] = row[key];
      });
      this.form.parentId = row.parentId ? [row.parentId] : [];
      this.form.image = [{ response: { url: row.image }, url: row.image }];
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const parentId = this.form.parentId.length
            ? this.form.parentId[this.form.parentId.length - 1]
            : "";
          const image = this.form.image.length
            ? this.form.image[this.form.image.length - 1]
            : "";
          axios
            .post(`/api/category/${this.id ? "update" : "add"}`, {
              id: this.id,
              ...this.form,
              parentId,
              image: image.response.url,
            })
            .then(() => {
              this.callback();
              this.visible = false;
            });
        }
      });
    },

    // 清空表单
    resetForm() {
      this.id = "";
      this.$refs["form"].resetFields();
      // // 一定要手动清空表单
      this.form = { ...defaultForm };
    },
  },
};
</script>
