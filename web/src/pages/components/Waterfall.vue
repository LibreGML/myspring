<template>
  <div>
    <div>这是一个瀑布流布局组件，大部分会在手机端出现</div>
    <Waterfall ref="waterMark"></Waterfall>
  </div>
</template>

<script>
import Waterfall from "@/components/Waterfall";

export default {
  name: "WaterfallDemo",
  components: {
    Waterfall,
  },
  created() {},
  mounted() {
    this.$refs["waterMark"].addItemData([
      { imgSrc: "https://caihai123.com/Dribbble/lists/news_teaser.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/preview_teaser.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/car_teaser.gif" },
      { imgSrc: "https://caihai123.com/documents/static/img/hero02.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/artboard_38_teaser.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/messi-video_teaser.png" },
      {
        imgSrc:
          "https://caihai123.com/Dribbble/lists/stock_app_ui_design_light_tubik_teaser.png",
      },
      { imgSrc: "https://caihai123.com/Dribbble/lists/learning.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/swtich.gif" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/goa_teaser.jpg" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/block-tube.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/148.2.pre.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/ro_2x_teaser.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/portals_10-09.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/screen-shot.jpg" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/race_animation.png" },
      { imgSrc: "https://caihai123.com/Dribbble/lists/dz_xn3lw0aiyrt6.jpg" },
    ]);
  },
  methods: {},
};
</script>

<!-- <style scoped></style> -->
