<template>
  <el-dialog :visible.sync="visible" width="800px" @closed="resetForm">
    <el-form ref="form" :model="form" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="分类" prop="parentId">
            <el-cascader
              v-model="form.parentId"
              :props="{ checkStrictly: true, value: 'id', label: 'name' }"
              placeholder="请选择分类"
              :options="options"
              filterable
            ></el-cascader>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="统一商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入统一商品名称" />
        </el-form-item>
      </el-row>
      <el-row>
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
// 表单默认项
import MyUpload from "@/components/my-upload";
import axios from "@/utils/axios";
const defaultForm = {
  name: "",
  image: [],
  parentId: [],
  cunType: "",
  pic: "",
  uppic: [],
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
      upType: true,
      id: "", // 编辑时存在，为空时为新增
      visible: false,
      options: [],
      form: { ...defaultForm },
      selectList: [],
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
    addStart(val) {
      this.form.parentId = [];
      this.form.parentId.push(this.$route.query.id);
      this.selectList = val;
      this.getSourceList();
      this.visible = true;
    },

    // 提交表单
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const parentId = this.form.parentId.length
            ? this.form.parentId[this.form.parentId.length - 1]
            : "";
          axios
            .post(`/api/product/edit-fl`, {
              ...this.form,
              selectList: this.selectList,
              flId: parentId,
              upType: this.upType,
            })
            .then(() => {
              this.$message.success("修改成功");
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
