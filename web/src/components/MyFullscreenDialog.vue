<template>
  <el-dialog
    class="my-fullscreen-dialog"
    fullscreen
    :title="title"
    :modal="false"
    v-bind="$attrs"
    :visible="visible"
    :class="{
      'is-collapse': isCollapse,
    }"
    :style="{ transition: visible ? 'padding-left 0.5s' : 'none' }"
    v-on="$listeners"
  >
    <template slot="title">
      <slot name="title"></slot>
    </template>
    <slot></slot>
    <template slot="footer">
      <slot name="footer"></slot>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
    title: {
      type: String,
      default: "",
    },
  },
  computed: {
    //控制侧边栏展开收起状态
    isCollapse() {
      return this.$store.state.layout.isCollapse;
    },
  },
};
</script>

<style scoped>
.my-fullscreen-dialog {
  padding-top: 100px;
  padding-left: 211px;
  pointer-events: none;
}
.my-fullscreen-dialog >>> .el-dialog {
  pointer-events: auto;
}
.my-fullscreen-dialog.is-collapse {
  padding-left: 65px;
}
/* .my-fullscreen-dialog >>> .el-dialog__body {
  height: calc(100vh - 282px);
  overflow-y: auto;
} */

@media screen and (max-width: 1200px) {
  .my-fullscreen-dialog {
    padding-left: 0px !important;
  }
}
</style>
