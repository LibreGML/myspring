<template>
  <div class="tabs-scroll">
    <div class="arrows el-icon-arrow-left" @click="prev"></div>
    <div class="box" @wheel.prevent="handleScroll">
      <div class="tabs">
        <slot />
      </div>
    </div>
    <div class="arrows el-icon-arrow-right" @click="next"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      domBox: null,
    };
  },
  mounted() {
    this.domBox = this.$el.childNodes[1];
  },
  methods: {
    //向左
    prev() {
      const target = this.domBox.scrollLeft - 200;
      this.uniformScroll(target);
    },
    //向右
    next() {
      const target = this.domBox.scrollLeft + 200;
      this.uniformScroll(target);
    },
    //使当前tab居中
    // 父组件会通过ref调用
    // eslint-disable-next-line vue/no-unused-properties
    moveCenter(tag) {
      this.uniformScroll(
        tag.$el.offsetLeft +
          tag.$el.clientWidth / 2 -
          this.domBox.clientWidth / 2
      );
    },
    //使滚动条匀速滚动
    uniformScroll(index) {
      const actualIndex = Math.max(0, index);
      /*
      滚动条实际能滚动到的位置是有限的，最小为0，最大看你内容的多少，
      如果你将要滚动到位置永远不能到达，将会出现死循环，所以使用record记录上次滚动到的位置，
      如果两次的位置相等，就说明到达最大或最小值，然后结束定时器
      */
      let record;
      if (this.domBox.scrollLeft > actualIndex) {
        const interval = setInterval(() => {
          if (this.domBox.scrollLeft <= actualIndex) {
            clearInterval(interval);
            return;
          }
          this.domBox.scrollLeft -= 10;
          if (this.domBox.scrollLeft === record) {
            clearInterval(interval);
            return;
          }
          record = this.domBox.scrollLeft;
        }, 20);
      } else {
        const interval = setInterval(() => {
          if (this.domBox.scrollLeft >= actualIndex) {
            clearInterval(interval);
            return;
          }
          this.domBox.scrollLeft += 10;
          if (this.domBox.scrollLeft === record) {
            clearInterval(interval);
            return;
          }
          record = this.domBox.scrollLeft;
        }, 20);
      }
    },
    handleScroll(e) {
      const eventDelta = e.wheelDelta || -e.deltaY * 40;
      this.domBox.scrollLeft = this.domBox.scrollLeft + eventDelta / 4;
    },
  },
};
</script>

<style scoped lang="scss">
.tabs-scroll {
  display: flex;
  justify-content: space-between;
  // box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}
.tabs {
  display: inline-block;
  height: 40px;
  padding: 4px 0;
  box-sizing: border-box;
  font-size: 12px;
}
.box {
  width: calc(100% - 39px);
  height: 40px;
  position: relative;
  white-space: nowrap;
  overflow: auto;
  text-align: left;
  z-index: 999;
}
.box::-webkit-scrollbar {
  display: none;
}
.tabs-scroll .arrows {
  line-height: 34px;
  width: 15px;
  border: 1px solid #dcdfe6;
  cursor: pointer;
  background-color: #fff;
  z-index: 1000;
}

// 黑色模式
.dark-theme {
  .tabs-scroll .arrows {
    border: 1px solid #676767;
    background-color: #676767;
    color: #fff;
  }
}
</style>
