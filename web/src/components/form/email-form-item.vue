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
        pattern: rule,
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
      default: "邮箱",
    },
    placeholder: {
      type: String,
      default: "请输入电子邮箱",
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
  data() {
    return {
      // 正则出处：https://any86.github.io/any-rule/
      rule: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
    };
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
