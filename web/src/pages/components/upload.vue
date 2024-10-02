<template>
  <div>
    <el-button @click="$refs['customUpload'].onStart(flieList)">
      文件上传
    </el-button>

    <el-image
      v-for="(item, index) in flieList"
      :key="index"
      :src="item"
      style="width:100px;height:100px"
    />
    <CustomUpload
      ref="customUpload"
      :limit="5"
      accept=".png"
      @confirm="(files) => (flieList = files)"
    />
  </div>
</template>

<script>
import CustomUpload from "@/components/CustomUpload/index";
export default {
  components: {
    CustomUpload,
  },
  props: {},
  data() {
    return {
      flieList: [],
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
