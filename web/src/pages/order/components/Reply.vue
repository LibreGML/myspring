<template>
  <el-dialog
    title="售后回复"
    :visible.sync="visible"
    width="800px"
    @closed="resetForm"
  >
    <el-form ref="form" :model="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item
            label="回复"
            prop="reply"
            :rules="[
              {
                required: true,
                message: '请输入理由',
                trigger: 'blur',
              },
            ]"
          >
            <el-input
              v-model="form.reply"
              type="textarea"
              :rows="3"
              maxlength="100"
              show-word-limit
              placeholder="请填写内容"
            ></el-input>
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
import axios from "@/utils/axios";

// 表单默认项
const defaultForm = {
  reply: "",
};

export default {
  props: {
    callback: {
      type: Function,
      default: () => null,
    },
  },
  data() {
    return {
      id: "", //
      visible: false,
      form: { ...defaultForm },
    };
  },
  methods: {
    // 新增时启动函数

    // eslint-disable-next-line vue/no-unused-properties
    start(id) {
      this.id = id;
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .post("/api/aftersales/update", {
              id: this.id,
              ...this.form,
            })
            .then(() => {
              this.$message.success("回复成功");
              this.visible = false;
              this.callback();
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
