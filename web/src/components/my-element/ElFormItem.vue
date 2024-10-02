<template>
  <FormItem v-bind="$attrs" v-on="$listeners">
    <label slot="label">
      {{ label }}
      <Tooltip v-if="extra" :content="extra" placement="top">
        <i class="el-icon-question"></i>
      </Tooltip>
      {{ labelSuffix }}
    </label>
    <slot />
  </FormItem>
</template>

<script>
import { FormItem, Tooltip } from "element-ui";

export default {
  name: "ElFormItem",
  components: { FormItem, Tooltip },
  inject: ["elForm"],
  props: {
    label: {
      type: String,
      default: "",
    },
    extra: {
      type: String,
      default: "",
    },
  },
  computed: {
    labelSuffix() {
      return this.elForm.labelSuffix;
    },
  },
};
</script>
