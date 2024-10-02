<template>
  <div>
    <div>
      <p>
        这是一个ElementUi自带的图片预览组件，他本身只能在'el-image'中使用，我将其封装成一个全局组件，可直接通过'$filePreview'函数启动
      </p>

      <el-button
        type="primary"
        @click="
          $filePreview({
            urlList: [
              'https://caihai123.com/Dribbble/lists/preview_teaser.png',
              'https://caihai123.com/Dribbble/lists/news_teaser.png',
              'https://caihai123.com/mock-api/d0bdccec-44fe-4a8b-be69-f427cfca6605.pdf',
            ],
          })
        "
      >
        点击预览图片
      </el-button>
    </div>

    <div>
      <p>oss缩略图，需要固定宽高</p>
      <div>
        <CompressedImage
          src="https://caihai123.com/Dribbble/lists/preview_teaser.png"
          :width="100"
          :height="100"
          :preview-src-list="[
            'https://caihai123.com/Dribbble/lists/preview_teaser.png',
            'https://caihai123.com/Dribbble/lists/news_teaser.png',
          ]"
        />
      </div>
    </div>
  </div>
</template>

<script>
import CompressedImage from "@/components/CompressedImage";

export default {
  components: { CompressedImage },
  props: {},
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>

<!-- <style scoped></style> -->
