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
            label="类型"
            prop="type"
            :rules="[
              { required: true, message: '请选择类型', trigger: 'change' },
            ]"
          >
            <DictSelect
              v-model="form.type"
              dict-type="price-type"
              style="width:100%;"
            />
          </el-form-item>
        </el-col>
        <el-col v-if="form.type === '1'" :span="12">
          <el-form-item
            label="名称"
            prop="name"
            :rules="[
              { required: true, message: '请输入名称', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.name" placeholder="请输入名称" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.type === '1'" :span="12">
          <el-form-item
            label="描述"
            prop="info"
            :rules="[
              { required: true, message: '请输入描述', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.info" placeholder="请输入描述" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="价格"
            prop="price"
            :rules="[
              { required: true, message: '请输入价格', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form.price"
              :min="0.0"
              :precision="2"
              placeholder="请输入价格"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="赠送额度"
            prop="limitb"
            :rules="[
              { required: true, message: '请输入赠送额度', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form.limitb"
              :min="0"
              placeholder="请输入赠送额度"
            />
          </el-form-item>
        </el-col>
        <el-col v-if="form.type === '1'" :span="12">
          <el-form-item
            label="等级"
            prop="grade"
            :rules="[
              { required: true, message: '请输入等级', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form.grade"
              :min="1"
              placeholder="请输入等级"
            />
          </el-form-item>
        </el-col>
        <el-col v-if="form.type === '1'" :span="12">
          <el-form-item
            label="天数"
            prop="duration"
            :rules="[
              { required: true, message: '请输入天数', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form.duration"
              :min="1"
              placeholder="请输入天数"
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
import DictSelect from "@/components/select/DictSelect";
const defaultForm = {
  name: "",
  info: "",
  price: "",
  limitb: "",
  grade: "",
  duration: "",
  type: "1",
};
export default {
  components: { DictSelect },
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
      if (this.form.type === "2") {
        this.form.grade = null;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios
            .post(this.id ? `/api/member/update` : `/api/member/add`, {
              ...this.form,
              id: this.id,
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
