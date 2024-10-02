<template>
  <el-form-item
    :label="label"
    :rules="[
      {
        required: required,
        message: `请输入${label}`,
        trigger: 'blur',
      },
      {
        // 正则出处：https://any86.github.io/any-rule/
        pattern: /^(?:(?:\+|00)86)?1[3-9]\d{9}$/,
        message: `请输入正确的${label}`,
        trigger: 'blur',
      },
    ]"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <el-input
      v-if="isEdit"
      v-model="valueVice"
      :clearable="clearable"
      maxlength="11"
      :placeholder="placeholder"
    ></el-input>
    <slot v-else name="preview">
      <div class="ellipsis">{{ valueVice }}</div>
    </slot>
  </el-form-item>
</template>

<script>
export default {
  props: {
    value: {
      type: String,
      required: true,
    },
    required: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: "手机号",
    },
    placeholder: {
      type: String,
      default: "请输入手机号",
    },
    clearable: {
      type: Boolean,
      default: true,
    },
    /**
     * 是否是在编辑状态
     * 完全是为了配合 DescriptionsForm 组件，心里一万个不情愿
     */
    isEdit: {
      type: Boolean,
      default: true,
    },
  },
  computed: {
    valueVice: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },
};
</script>
