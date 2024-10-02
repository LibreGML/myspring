<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item
        v-for="item in levelList"
        :key="item.path || item.title"
      >
        <router-link v-if="item.path" :to="{ path: item.path }">
          {{ item.title }}
        </router-link>
        <span v-else>{{ item.title }}</span>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import { treeFilter, objDeepCopy } from "@/utils/index";

export default {
  computed: {
    levelList() {
      const { path, matched } = this.$route;

      const list = treeFilter(
        this.$store.state.layout.menuList,
        (item) => item.path && item.path === path
      );

      const levelList = this.treeTOList(list);
      if (levelList.length) {
        return levelList;
      } else {
        return matched
          .filter((item) => item.meta && item.meta.title)
          .map((item) => ({
            path: item.path,
            title: item.meta.title,
          }));
      }
    },
  },
  methods: {
    // 将一条枝干的树转成一维数组
    treeTOList(treeList) {
      const list = [];
      let treeListCopy = objDeepCopy(treeList);
      while (treeListCopy && treeListCopy.length) {
        const [firstItem] = treeListCopy;
        if (firstItem) {
          list.push({
            title: firstItem.title,
            path: firstItem.path,
          });
          treeListCopy = firstItem.children;
        } else {
          treeListCopy = null;
        }
      }
      return list;
    },
  },
};
</script>

<style scoped>
.app-breadcrumb {
  line-height: 48px;
  padding-left: 8px;
  font-size: 14px;
}
.app-breadcrumb .el-breadcrumb__inner a {
  font-weight: 400;
}
.app-breadcrumb .el-breadcrumb__inner a {
  color: #68728c;
}

/* breadcrumb transition */
.breadcrumb-enter-active,
.breadcrumb-leave-active {
  transition: all 0.5s;
}

.breadcrumb-enter,
.breadcrumb-leave-active {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-move {
  transition: all 0.5s;
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>
