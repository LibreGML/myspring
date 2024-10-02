<template>
  <div class="container" :style="{ height: containerHeight + 'px' }">
    <div
      v-for="i in item"
      :key="i[itemkey]"
      class="item"
      :style="{
        width: colsWidth + 'px',
        height: i.imgHeight + 'px',
        left: i.left + 'px',
        top: i.top + 'px',
      }"
    >
      <img :src="i[srcKey]" />
    </div>
  </div>
</template>

<script>
const elementResizeDetectorMaker = require("element-resize-detector");
const erdUltraFast = elementResizeDetectorMaker({
  strategy: "scroll", //<- For ultra performance.
});

export default {
  props: {
    // 图片路径
    srcKey: {
      type: String,
      default: "imgSrc",
    },
    // 列宽度
    colsWidth: {
      type: Number,
      default: 240,
    },
    // 图片加载失败时的默认图片地址
    errSrc: {
      type: String,
      default: ``,
    },
    // 列表渲染的 key ，默认为 srcKey
    idKey: {
      type: String,
      default: "",
    },
    /**
     * 默认是等所有img预加载完之后再渲染，但是这给用户的体验会很慢，所有我还加了个loading
     * 如果你的数据的顺序不重要，可以设置此参数为true，他会让用户更快看到图片，但是会打乱数据的顺序，同时去掉loading功能
     * 更好的方式是传入的数据中就包含有 imgHeight（图片高度），这样会跳过预加载环节
     *  */
    fastWay: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      item: [], // 实际渲染的瀑布流数据
      itemkey: this.idKey || this.srcKey, // 渲染时的key值
      loading: false, // 控制loading状态
      containerHeight: 0, // 父容器的高度
      column: 0, // 总列数
    };
  },
  mounted() {
    this.calcColumn();
    // 容器宽度改变时重新布局
    erdUltraFast.listenTo(this.$el, this.calcElement);
  },
  beforeDestroy() {
    erdUltraFast.removeListener(this.$el, this.calcElement);
  },
  methods: {
    // 用来向item中添加数据
    // eslint-disable-next-line vue/no-unused-properties
    addItemData(items) {
      let index = 0; // 用来记录已经预加载完成的个数
      // 出口
      const exit = (i, index) => {
        if (this.fastWay) {
          this.vnodeItem(i, index);
        } else {
          if (index >= items.length) {
            this.loading = false;
            items.forEach((item, index) => {
              this.vnodeItem(item, index + 1);
            });
          }
        }
      };

      items.forEach((item) => {
        if (!this.fastWay) {
          this.loading = true;
        }
        // 如果高度已知，就不需要预加载
        if (item.imgHeight) {
          index++;
          exit(item, index);
        } else {
          const oImg = new Image();
          oImg.src = item[this.srcKey];
          // 预加载完成时
          oImg.onload = () => {
            index++;
            item.imgHeight = (oImg.height / oImg.width) * this.colsWidth;
            exit(item, index);
          };
          // 加载期间发生错误时
          oImg.onerror = () => {
            index++;
            item[this.srcKey] = this.errSrc;
            item.imgHeight = this.colsWidth;
            exit(item, index);
          };
        }
      });
    },

    // 计算单个的位置
    vnodeItem(item, index) {
      if (this.item.length < this.column) {
        item.top = 0;
        item.left = ((index - 1) % this.column) * this.colsWidth;
        item.cols = index; // 所在的列
      } else {
        for (let i = 1; i <= this.column; i++) {
          const colsHeight = this.calcColsHeight(i);
          if (!item.top || colsHeight < item.top) {
            item.top = colsHeight;
            item.left = (i - 1) * this.colsWidth;
            item.cols = i;
          }
        }
      }

      this.item.push(item);
      // 在这里重新计算父容器的高度
      this.calcContainerHeight();
    },

    // 容器宽度改变时重新布局
    calcElement() {
      this.calcColumn();
      const list = [...this.item];
      this.item = [];
      list.forEach((item, index) => {
        item.cols = undefined;
        item.top = undefined;
        item.left = undefined;
        this.vnodeItem(item, index + 1);
      });
    },

    // 计算 container 的高度
    calcContainerHeight() {
      this.containerHeight = 0; // 初始化父容器高度
      for (let i = 1; i <= this.column; i++) {
        const colsHeight = this.calcColsHeight(i);
        if (!this.containerHeight || this.containerHeight < colsHeight) {
          this.containerHeight = colsHeight;
        }
      }
    },

    // 计算某一列的高度
    calcColsHeight(cols) {
      let colsHeight = 0;
      this.item
        .filter((item) => item.cols === cols)
        .forEach((item) => {
          colsHeight += item.imgHeight;
        });
      return colsHeight;
    },

    // 计算当前宽度下能排的列数
    calcColumn() {
      const elWidth = this.$el.offsetWidth; // 获取容器的宽度
      this.column = Math.floor(elWidth / this.colsWidth); // 当前容器最多能放几列
    },

    // 清空item
    // eslint-disable-next-line vue/no-unused-properties
    delItem() {
      this.item = [];
      this.containerHeight = 0;
    },
  },
};
</script>

<style scoped>
.container {
  position: relative;
}
.item {
  position: absolute;
  box-sizing: border-box;
  padding: 10px;
  animation: show-card-data 0.4s;
  transition: left 0.6s, top 0.6s;
  transition-delay: 0.1s;
}
.item > img {
  width: 100%;
}
@keyframes show-card-data {
  0% {
    -webkit-transform: scale(0.5);
    transform: scale(0.5);
  }
  100% {
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}
</style>
