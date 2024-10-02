<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card><Introduce /></el-card>
      </el-col>
      <el-col :span="12" style="margin-top:20px">
        <el-card header="7天内订单走势"><Chart1 /></el-card>
      </el-col>
      <el-col :span="12" style="margin-top:20px">
        <el-card header="分类销售/购买总量top7"><Chart3 /></el-card>
      </el-col>
      <el-col v-if="roleType === '1'" :span="12" style="margin-top:20px">
        <el-card header="今日交易额供应商top5"><Chart2 /></el-card>
      </el-col>
      <el-col v-if="roleType === '1'" :span="12" style="margin-top:20px">
        <el-card header="今日交易额商家top5"><Chart4 /></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import "echarts"; // echarts文件很大，大部分系统最好不要全量引入
import Introduce from "./components/Introduce";
import Chart1 from "./components/Chart1";
import Chart2 from "./components/Chart2";
import Chart3 from "./components/Chart3";
import Chart4 from "./components/Chart4";

export default {
  components: { Introduce, Chart1, Chart2, Chart3, Chart4 },
  props: {},
  data() {
    return {};
  },
  computed: {
    roleType() {
      return this.$store.state.userInfo.account.roleType;
    },
  },
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
