<template>
  <Button v-if="!isDisable" v-waves v-bind="$attrs" v-on="$listeners">
    <slot />
  </Button>
</template>

<script>
import { Button } from "element-ui";
export default {
  name: "ElButton",
  components: { Button },
  props: {
    authButKey: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      isDisable: false, // 通过权限判断是否显示
    };
  },
  computed: {
    buttonList() {
      return this.$route.meta.buttonList;
    },
  },
  watch: {
    authButKey() {
      this.handle();
    },
    buttonList() {
      this.handle();
    },
  },
  created() {
    this.handle();
  },
  methods: {
    handle() {
      if (this.$checkPermission.call(this, this.authButKey)) {
        this.isDisable = false;
      } else {
        this.isDisable = true;
      }
    },
  },
};
</script>
