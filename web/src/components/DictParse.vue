<template>
  <span>{{ label }}</span>
</template>

<script>
import dict from "@/utils/dict";

export default {
  props: {
    // eslint-disable-next-line vue/require-prop-types
    value: {
      // type: [String, Number],
      required: true,
    },
    dictType: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      options: [],
    };
  },
  computed: {
    label() {
      const found = this.options.find((item) => item.value === this.value);
      return found ? found.label : this.value;
    },
  },
  created() {
    this.options = dict[this.dictType];
  },
};
</script>
