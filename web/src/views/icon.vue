<template>
  <el-tabs v-model="active" type="border-card">
    <el-tab-pane label="iconfont" name="first">
      <el-input
        v-model="searchValue"
        suffix-icon="el-icon-search"
        placeholder="在此搜索图标，点击图标可复制"
        style="width: 300px"
      ></el-input>
      <div class="item-box">
        <div
          v-for="item in iconfont.filter(
            (item) =>
              !searchValue ||
              item.toLowerCase().includes(searchValue.toLowerCase())
          )"
          :key="item"
          v-waves
          class="icon-item"
          @click="handleClipboard(`${item}`, $event)"
        >
          <i :class="item"></i>
          <span>{{ item }}</span>
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="symbol" name="symbol">
      <el-input
        v-model="searchValue"
        suffix-icon="el-icon-search"
        placeholder="在此搜索图标，点击图标可复制"
        style="width: 300px"
      ></el-input>
      <div class="item-box">
        <div
          v-for="item in symbolIcon.filter(
            (item) =>
              !searchValue ||
              item.toLowerCase().includes(searchValue.toLowerCase())
          )"
          :key="item"
          v-waves
          class="icon-item"
          @click="
            handleClipboard(
              `<svg class='icon' aria-hidden='true'>
                <use xlink:href='#${item}'></use>
              </svg>`,
              $event
            )
          "
        >
          <i>
            <svg class="icon" aria-hidden="true">
              <use :xlink:href="'#' + item"></use>
            </svg>
          </i>
          <span>{{ item }}</span>
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import iconfont from "@/utils/iconfont/iconfont";
import symbolIcon from "@/utils/iconfont/symbol-icon";
import clipboard from "@/utils/clipboard";

export default {
  name: "Icon",
  data() {
    return {
      searchValue: "",
      active: "first",
      iconfont,
      symbolIcon,
    };
  },
  methods: {
    handleClipboard(text, event) {
      clipboard(text, event);
    },
  },
};
</script>

<style scoped>
.item-box {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  margin-top: 20px;
  border-radius: 4px;
  border-top: 1px solid #eee;
}
.icon-item {
  height: 120px;
  padding: 0 10px;
  line-height: 120px;
  text-align: center;
  border: 1px solid #eee;
  margin-top: -1px;
  margin-right: -1px;
  cursor: pointer;
}
.icon-item:hover {
  color: #2b7bfb;
}
.icon-item i {
  display: block;
  height: 80px;
  line-height: 80px;
  font-size: 32px;
}
.icon-item span {
  display: block;
  height: 40px;
  line-height: 20px;
}
</style>
