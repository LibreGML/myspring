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
            label="标题"
            prop="title"
            :rules="[
              { required: true, message: '请输入标题', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.title" placeholder="请输入标题" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="公告类型"
            prop="type"
            :rules="[
              { required: true, message: '请选择公告类型', trigger: 'change' },
            ]"
          >
            <DictSelect
              v-model="form.type"
              dict-type="notice-type"
              style="width:100%;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="内容"
            prop="content"
            :rules="[
              { required: true, message: '请输入文字内容', trigger: 'blur' },
            ]"
          >
            <Editor v-model="form.content"></Editor>
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
import DictSelect from "@/components/select/DictSelect";
import Editor from "@/components/Editor";
import axios from "@/utils/axios";
const defaultForm = {
  id: "",
  title: "",
  type: "",
  content: "",
};
export default {
  components: { DictSelect, Editor },
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
            .post(`/api/notice/${this.form.id ? "update" : "add"}`, {
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
