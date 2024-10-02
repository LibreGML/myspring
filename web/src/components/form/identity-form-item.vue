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
        pattern: /(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0[1-9]|10|11|12)([0-2]\d|30|31)\d{3}(\d|X|x)$)/,
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
      maxlength="18"
      :clearable="clearable"
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
      default: "身份证号",
    },
    placeholder: {
      type: String,
      default: "请输入身份证",
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
