<template>
  <DescriptionsForm
    :model="form"
    title="基础信息"
    label-width="130px"
    @submit="onSubmit"
    @opened="cacheFormData"
    @closed="resetFormData"
  >
    <el-row slot-scope="{ isEdit }">
      <el-col :md="8" :lg="6">
        <el-form-item
          label="姓名"
          prop="name"
          :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]"
        >
          <el-input
            v-if="isEdit"
            v-model="form.name"
            placeholder="请输入姓名"
          />
          <div v-else>{{ form.name }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <IdentityFormItem
          v-model="form.identity"
          prop="identity"
          required
          :is-edit="isEdit"
        >
          <div slot="preview">{{ form.identity }}</div>
        </IdentityFormItem>
      </el-col>
      <el-col :md="8" :lg="6">
        <PhoneFormItem
          v-model="form.phone"
          prop="phone"
          required
          :is-edit="isEdit"
        />
      </el-col>
      <el-col :md="8" :lg="6">
        <EmailFormItem
          v-model="form.email"
          prop="email"
          required
          :is-edit="isEdit"
        />
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="请求方式" prop="axiosType">
          <DictSelect
            v-if="isEdit"
            v-model="form.axiosType"
            dict-type="axios-type"
            style="width:100%;"
          />
          <div v-else>{{ form.axiosType | dictParse("axios-type") }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="请求方式" prop="axiosType">
          <DictSelect
            v-if="isEdit"
            v-model="form.axiosType"
            dict-type="axios-type"
            style="width:100%;"
          />
          <div v-else>{{ form.axiosType }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="请求方式" prop="axiosType">
          <DictSelect
            v-if="isEdit"
            v-model="form.axiosType"
            dict-type="axios-type"
            style="width:100%;"
          />
          <div v-else>{{ form.axiosType }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="请求方式" prop="axiosType">
          <DictSelect
            v-if="isEdit"
            v-model="form.axiosType"
            dict-type="axios-type"
            style="width:100%;"
          />
          <div v-else>{{ form.axiosType }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="计数器" prop="number">
          <el-input-number
            v-if="isEdit"
            v-model="form.number"
            :min="1"
            :max="10"
          ></el-input-number>
          <div v-else>{{ form.number }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="请求方式" prop="axiosType">
          <DictSelect
            v-if="isEdit"
            v-model="form.axiosType"
            dict-type="axios-type"
            style="width:100%;"
          />
          <div v-else>{{ form.axiosType }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="时间范围" prop="date">
          <el-date-picker
            v-if="isEdit"
            v-model="form.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width:100%"
          >
          </el-date-picker>
          <div v-else>{{ form.date }}</div>
        </el-form-item>
      </el-col>
      <el-col :md="8" :lg="6">
        <el-form-item label="菜单类型" prop="menuType">
          <DictSelect
            v-if="isEdit"
            v-model="form.menuType"
            dict-type="menu-type"
            style="width:100%;"
          />
          <div v-else>{{ form.menuType | dictParse("menu-type") }}</div>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          label="多选条件多"
          extra="如果你的名称过长，你应该考虑设置别名并在此填写详细描述"
          prop="resource"
          :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]"
        >
          <el-checkbox-group v-if="isEdit" v-model="form.resource">
            <el-checkbox
              v-for="item in options"
              :key="item.value"
              :label="item.value"
            >
              {{ item.label }}
            </el-checkbox>
          </el-checkbox-group>

          <div v-else>
            <div v-for="val in form.resource" :key="val">
              {{ val | transverter }}
            </div>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="备注" prop="remark">
          <el-input
            v-if="isEdit"
            v-model="form.remark"
            type="textarea"
            :rows="3"
            maxlength="50"
            show-word-limit
            placeholder="文本输入框必须独占一行"
          ></el-input>
          <div v-else>{{ form.remark }}</div>
        </el-form-item>
      </el-col>
    </el-row>
  </DescriptionsForm>
</template>

<script>
import DescriptionsForm from "@/components/DescriptionsForm";
import DictSelect from "@/components/select/DictSelect";
import PhoneFormItem from "@/components/form/phone-form-item";
import EmailFormItem from "@/components/form/email-form-item";
import IdentityFormItem from "@/components/form/identity-form-item";

const options = [
  { value: "1", label: "选项一" },
  { value: "2", label: "选项二" },
  { value: "3", label: "选项三" },
  { value: "4", label: "选项四" },
  { value: "5", label: "选项五" },
];

let initialFormData = null; // 最初的表单值，用于重置
// 表单默认项
export default {
  name: "TemplateDetails",
  components: {
    DescriptionsForm,
    DictSelect,
    PhoneFormItem,
    EmailFormItem,
    IdentityFormItem,
  },
  filters: {
    transverter(value) {
      const found = options.find((item) => item.value === value);
      return found ? found.label : value;
    },
  },
  data() {
    return {
      options,
      form: {
        name: "hahah",
        identity: "",
        phone: "",
        email: "",
        axiosType: "",
        menuType: "",
        resource: [],
        remark: "",
        date: [],
        number: 1,
      },
    };
  },
  created() {
    // 模拟通过请求获取数据
    setTimeout(() => {
      const data = {
        name: "蔡海",
        identity: "520122199405083016",
        phone: "15519060282",
        email: "ch15519060282@163.com",
        axiosType: "1",
        menuType: "1",
        resource: ["1"],
        remark: "蔡海的呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵",
        date: [],
      };
      // 不要加进去多余变量
      Object.keys(this.form).forEach((key) => {
        this.form[key] = data[key];
      });
      this.cacheFormData();
    }, 5000);
  },
  methods: {
    onSubmit(valid) {
      if (valid) {
        // 表单验证通过
        // 提交表单成功之后可以通过ref关闭form状态
      } else {
        // 表单验证不通过
      }
    },

    // 缓存form表单数据
    cacheFormData() {
      initialFormData = JSON.parse(JSON.stringify(this.form)); // 缓存初始表单数据 ;
    },

    // 恢复表单数据
    resetFormData() {
      this.form = initialFormData;
    },
  },
};
</script>
