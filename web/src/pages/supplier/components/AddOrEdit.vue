<template>
  <el-dialog
    :title="!id ? '新增' : '编辑'"
    :visible.sync="visible"
    width="800px"
    @closed="resetForm"
  >
    <el-form ref="form" :model="form" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="账号"
            prop="account"
            :rules="[
              { required: true, message: '请输入账号', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.account"
              :disabled="form.id !== ''"
              placeholder="请输入账号"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="供应商名称"
            prop="name"
            :rules="[
              { required: true, message: '请输入供应商名称', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.name" placeholder="请输入供应商名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="余额剩余"
            prop="balance"
            :rules="[
              { required: true, message: '请输入余额剩余', trigger: 'blur' },
            ]"
          >
            <el-input-number v-model="form.balance" :min="0"></el-input-number>
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
const defaultForm = {
  id: "",
  account: "",
  name: "",
  balance: "",
};
export default {
  components: {},
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
      form: { ...defaultForm },
    };
  },
  methods: {
    // 新增时启动函数
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    addStart() {
      this.visible = true;
    },

    // 编辑时启动
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    editStart(row) {
      this.id = row.id;
      Object.keys(defaultForm).forEach((key) => {
        this.form[key] = row[key];
      });
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .post(`/api/supplier/${this.form.id ? "update" : "add"}`, {
              ...this.form,
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
