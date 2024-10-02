<template>
  <el-dialog
    title="提现处理"
    :visible.sync="visible"
    width="800px"
    @closed="resetForm"
  >
    <el-form ref="form" :model="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="提现金额" prop="withdrawalAmount">
            {{ row.withdrawalAmount }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="费率" prop="free">
            {{ row.free }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="实收金额">
            {{ row.withdrawalAmount - row.free * row.withdrawalAmount }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="提现二维码" prop="imageCode">
            <el-image
              :src="row.imageCode"
              style="width: 250px;height: 250px;"
            ></el-image>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="是否提现"
            prop="status"
            :rules="[
              { required: true, message: '请选择状态', trigger: 'change' },
            ]"
          >
            <!--和 check-state 枚举字典强耦合-->
            <el-select v-model="form.status">
              <el-option label="通过" :value="3"></el-option>
              <el-option label="不通过" :value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="备注"
            prop="remarks"
            :rules="
              form.remarks === 2
                ? [
                    {
                      required: true,
                      message: '请输入备注',
                      trigger: 'blur',
                    },
                  ]
                : []
            "
          >
            <el-input
              v-model="form.remarks"
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
  status: "", // 审核结果
  remarks: "", // 审核结果
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
      row: {
        imageCode: "",
        withdrawalAmount: "",
        free: "",
      },
    };
  },
  methods: {
    // 新增时启动函数

    // eslint-disable-next-line vue/no-unused-properties
    start(id, row) {
      this.row = row;
      this.id = id;
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .post("/api/withdrawal/deal", {
              id: this.id,
              ...this.form,
            })
            .then(() => {
              this.$message.success("处理完成");
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
