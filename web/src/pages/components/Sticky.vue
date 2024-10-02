<template>
  <div>
    <div>
      <Sticky :sticky-top="stickyTop" :z-index="zIndex">
        <el-button type="primary">向上滚动时我会固定在顶部</el-button>
      </Sticky>
    </div>

    <el-form label-width="90px" style="margin-top:20px">
      <el-form-item>
        <h2>props</h2>
      </el-form-item>
      <el-form-item label="stickyTop">
        <el-input-number v-model="stickyTop"></el-input-number>
      </el-form-item>
      <el-form-item label="zIndex">
        <el-input-number v-model="zIndex"></el-input-number>
      </el-form-item>
    </el-form>

    <el-alert v-for="i in 100" :key="i" title="向上滚动查看效果"> </el-alert>
  </div>
</template>

<script>
import Sticky from "@/components/Sticky";

export default {
  name: "StickyDemo",
  components: { Sticky },
  props: {},
  data() {
    return {
      stickyTop: 90,
      zIndex: 100,
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>

<!-- <style scoped></style> -->
