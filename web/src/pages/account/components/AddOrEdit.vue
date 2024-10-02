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
            label="账号"
            prop="account"
            :rules="[
              { required: true, message: '请输入账号', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.account" placeholder="请输入账号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" placeholder="请输入会更新密码" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="头像"
            prop="fileList"
            :rules="[
              { required: true, message: '请添加头像', trigger: 'change' },
            ]"
          >
            <MyUpload v-model="form.fileList" type="img" :limit="1" />
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
import MyUpload from "@/components/my-upload";
import axios from "@/utils/axios";
// 表单默认项
const defaultForm = {
  account: "",
  password: "",
  fileList: [],
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
      this.form.fileList = [];
      this.form.fileList.push({
        response: { url: row.headPic },
        url: row.headPic,
      });
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .post(`/api/account/update`, {
              id: this.id,
              headPic:
                this.form.fileList[0] && this.form.fileList[0].response
                  ? this.form.fileList[0].response.url
                  : "",
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
