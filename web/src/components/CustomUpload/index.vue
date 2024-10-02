<template>
  <el-dialog
    title="文件上传"
    :visible.sync="visible"
    width="810px"
    :append-to-body="appendToBody"
    @closed="diaClosed"
  >
    <panel
      v-if="!isUnmounted"
      v-model="fileList"
      :action="action"
      :limit="limit"
      :accept="accept"
      :name="name"
    />

    <span slot="footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="onConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import panel from "./panel";
export default {
  components: { panel },
  props: {
    action: {
      type: String,
      default: "/api/system/oss/upload",
    },
    // eslint-disable-next-line vue/require-default-prop
    limit: Number,
    // eslint-disable-next-line vue/require-default-prop
    accept: String,
    // eslint-disable-next-line vue/require-default-prop
    name: String,
    appendToBody: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      visible: false,
      isUnmounted: true,

      fileList: [],
    };
  },
  methods: {
    // eslint-disable-next-line vue/no-unused-properties
    onStart(fileList = []) {
      this.fileList = fileList;
      this.isUnmounted = false;
      this.visible = true;
    },

    onConfirm() {
      this.$emit("confirm", this.fileList);
      this.visible = false;
    },

    // 弹窗关闭之后
    diaClosed() {
      this.isUnmounted = true;
      this.fileList = [];
    },
  },
};
</script>
