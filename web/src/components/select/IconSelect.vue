<template>
  <el-select
    class="icon-select"
    filterable
    placeholder="请选择图标"
    clearable
    :popper-append-to-body="false"
    popper-class="popper-box"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <div class="icon-box">
      <el-option
        v-for="item in IconfontList"
        :key="item"
        :value="item"
        class="icon-item"
      >
        <i :class="item"></i>
      </el-option>
    </div>
  </el-select>
</template>

<script>
import IconfontList from "@/utils/iconfont/iconfont";

export default {
  data() {
    return {
      IconfontList,
    };
  },
};
</script>

<style scoped>
.icon-select >>> .el-select-dropdown__wrap {
  min-height: 500px;
}
.icon-select >>> .icon-box {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(60px, 1fr));
}
.icon-select >>> .icon-item {
  text-align: center;
  font-size: 24px;
}
</style>
