<template>
  <transition name="viewer-fade">
    <Main
      v-if="visible"
      :url-list="urlList"
      :z-index="zIndex"
      :on-switch="onSwitch"
      :on-close="onClose"
      :initial-index="initialIndex"
    />
  </transition>
</template>

<script>
import Main from "./src/main";
export default {
  components: { Main },
  props: {
    urlList: {
      type: Array,
      default: () => [],
    },
    zIndex: {
      type: Number,
      default: 2000,
    },
    onSwitch: {
      type: Function,
      default: () => {},
    },
    initialIndex: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      visible: false,
    };
  },
  methods: {
    onClose() {
      this.visible = false;
    },
  },
};
</script>
