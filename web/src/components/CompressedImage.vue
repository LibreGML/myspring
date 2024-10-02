<template>
  <el-image
    :src="compressedSrc"
    :style="{ width: `${width}px`, height: `${height}px` }"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <template slot="placeholder">
      <slot name="placeholder"></slot>
    </template>

    <template slot="error">
      <slot name="error"></slot>
    </template>
  </el-image>
</template>

<script>
export default {
  props: {
    src: {
      type: String,
      default: "",
    },
    width: {
      type: Number,
      required: true,
    },
    height: {
      type: Number,
      required: true,
    },
    fit: {
      type: String,
      default: "lfit",
      validator(value) {
        // 这个值必须匹配下列字符串中的一个
        return ["lfit", "mfit", "fill", "pad", "fixed"].indexOf(value) !== -1;
      },
    },
  },
  computed: {
    compressedSrc() {
      const { src, fit, height, width } = this;
      if (!src) {
        return ""; // 如果 URL 为空，直接返回空字符串
      }
      const urlObject = new URL(src);
      const params = new URLSearchParams(urlObject.search);

      if (!params.has("x-oss-process")) {
        params.append(
          "x-oss-process",
          `image/resize,m_${fit},h_${height},w_${width}`
        );
        urlObject.search = params.toString();
      }

      return urlObject.toString();
    },
  },
};
</script>
