<template>
  <el-dialog
    title="审核"
    :visible.sync="visible"
    width="800px"
    @closed="resetForm"
  >
    <el-form ref="form" :model="form" label-width="110px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="审核"
            prop="status"
            :rules="[
              { required: true, message: '请选择状态', trigger: 'change' },
            ]"
          >
            <!--和 check-state 枚举字典强耦合-->
            <el-select v-model="form.status">
              <el-option label="通过" value="2"></el-option>
              <el-option label="不通过" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="审核结果"
            prop="remarks"
            :rules="
              form.examine === '3'
                ? [
                    {
                      required: true,
                      message: '请输入审核结果',
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
      <el-row>
        <el-col v-if="form.status === '2'" :span="12">
          <el-form-item
            label="商品分类"
            prop="categoryId"
            :rules="[
              { required: true, message: '请选择状态', trigger: 'change' },
            ]"
          >
            <!--和 check-state 枚举字典强耦合-->
            <el-cascader
              v-model="form.categoryId"
              :props="{ checkStrictly: true, value: 'id', label: 'name' }"
              placeholder="请选择父分类"
              :options="options"
              filterable
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col
          v-if="
            form.status === '2' &&
              !((upType && form.pic) || (!upType && form.uppic.length > 0))
          "
          :span="12"
        >
          <el-form-item label="存储方式" prop="cunType">
            <el-select v-model="form.cunType" placeholder="请选择">
              <el-option
                v-for="item in cunTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="form.status === '2'">
        <el-form-item label="统一商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入统一商品名称" />
        </el-form-item>
      </el-row>
      <el-row v-if="form.status === '2'">
        <el-form-item label="统一商品图片" prop="pic">
          <el-switch
            v-model="upType"
            active-text="已有地址"
            inactive-text="上传"
          >
          </el-switch>
          <el-input
            v-if="upType"
            v-model="form.pic"
            placeholder="请输入图片地址"
          />
          <MyUpload v-else v-model="form.uppic" :limit="1" type="img" />
        </el-form-item>
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
import MyUpload from "@/components/my-upload";
// 表单默认项
const defaultForm = {
  status: "", // 审核结果
  categoryId: "",
  remarks: "", // 审核结果
  name: "",
  uppic: [],
  cunType: "",
};

export default {
  components: {
    MyUpload,
  },
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
      options: [],
      upType: true,
      cunTypeList: [
        { value: "", label: "原有" },
        { value: "1", label: "本地" },
        { value: "2", label: "123云盘" },
      ],
    };
  },
  methods: {
    // 新增时启动函数
    getSourceList() {
      axios.get("/api/category/all?name=").then((res) => {
        this.options = res.data;
      });
    },
    // eslint-disable-next-line vue/no-unused-properties
    start(id) {
      this.getSourceList();
      this.id = id;
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const categoryId = this.form.categoryId.length
            ? this.form.categoryId[this.form.categoryId.length - 1]
            : "";
          axios
            .post("/api/product/update", {
              id: this.id,
              ...this.form,
              upType: this.upType,
              categoryId,
            })
            .then(() => {
              this.$message.success("审核完成");
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
