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
            label="姓名"
            prop="name"
            :rules="[
              { required: true, message: '请输入姓名', trigger: 'blur' },
            ]"
          >
            <el-input v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <IdentityFormItem v-model="form.identity" required prop="identity" />
        </el-col>
        <el-col :span="12">
          <PhoneFormItem v-model="form.phone" required prop="phone" />
        </el-col>
        <el-col :span="12">
          <EmailFormItem v-model="form.email" required prop="email" />
        </el-col>
        <el-col :span="12">
          <el-form-item label="计数器" prop="number">
            <el-input-number
              v-model="form.number"
              :min="1"
              :max="10"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请求方式" prop="axiosType">
            <DictSelect
              v-model="form.axiosType"
              dict-type="axios-type"
              style="width:100%;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="时间范围" prop="date">
            <el-date-picker
              v-model="form.date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width:100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="菜单类型菜单类型菜单类型"
            prop="menuType"
            label-width="180px"
          >
            <DictSelect
              v-model="form.menuType"
              dict-type="menu-type"
              style="width:100%;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="特殊别名"
            extra="如果你的名称过长，你应该考虑设置别名并在此填写详细描述"
            prop="resource"
          >
            <el-radio-group v-model="form.resource">
              <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
              <el-checkbox label="地推活动" name="type"></el-checkbox>
              <el-checkbox label="线下主题活动" name="type"></el-checkbox>
              <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              :rows="3"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文件上传">
            <MyUpload v-model="form.fileList" type="file" />
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
import PhoneFormItem from "@/components/form/phone-form-item";
import EmailFormItem from "@/components/form/email-form-item";
import IdentityFormItem from "@/components/form/identity-form-item";
import DictSelect from "@/components/select/DictSelect";
import MyUpload from "@/components/my-upload";

// 表单默认项
const defaultForm = {
  name: "",
  identity: "",
  phone: "",
  email: "",
  axiosType: "",
  menuType: "",
  resource: [],
  remark: "",
  number: 0,
  date: [],
  fileList: [
    {
      name: "food.jpeg",
      url:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
    },
    {
      name: "food2.jpeg",
      url:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
    },
  ],
};

export default {
  components: {
    DictSelect,
    PhoneFormItem,
    EmailFormItem,
    IdentityFormItem,
    MyUpload,
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
          //
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
