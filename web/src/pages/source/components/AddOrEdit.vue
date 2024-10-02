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
            label="货源系统"
            prop="sourceSystem"
            :rules="[
              { required: true, message: '请选择货源系统', trigger: 'change' },
            ]"
          >
            <el-select v-model="form.sourceSystem" placeholder="请选择货源系统">
              <el-option
                v-for="item in sourceOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="货源网址"
            prop="sourceWebsite"
            :rules="[
              { required: true, message: '请输入货源网址', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.sourceWebsite"
              placeholder="请输入货源网址"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="对接账号"
            prop="dockingAccount"
            :rules="[
              { required: true, message: '请输入对接账号', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.dockingAccount"
              placeholder="请输入对接账号"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="对接密匙"
            prop="dockingKey"
            :rules="[
              { required: true, message: '请输入对接密匙', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.dockingKey" placeholder="请输入对接密匙" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="申请理由"
            prop="applyReason"
            :rules="[
              { required: true, message: '请输入申请理由', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.applyReason" placeholder="请输入申请理由" />
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
  supplierId: "",
  sourceSystem: "",
  sourceWebsite: "",
  dockingAccount: "",
  dockingKey: "",
  applyReason: "",
};
export default {
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
      sourceOptions: [],
      form: { ...defaultForm },
    };
  },
  created() {
    this.getSourceList();
  },
  methods: {
    getSourceList() {
      axios.get("/api/official/account/all-source").then((res) => {
        this.sourceOptions = res.data;
      });
    },
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
            .post(this.id ? `/api/source/update` : `/api/source/save`, {
              id: this.id,
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
