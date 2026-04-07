<template>
  <el-select v-bind="$attrs" v-on="$listeners">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<script>
import dict from "@/utils/dict";
export default {
  props: {
    dictType: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      options: dict[this.dictType],
    };
  },
};
</script>
