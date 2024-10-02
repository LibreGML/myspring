<template>
  <el-upload
    ref="upload"
    :class="{ heh: !!limit && fileList.length >= limit, 'upload-my': min }"
    :action="action"
    :file-list="fileList"
    :on-preview="handlePreview"
    :list-type="type === 'img' ? 'picture-card' : 'text'"
    :http-request="httpRequest"
    :before-upload="beforeUpload"
    :accept="accept"
    :limit="limit"
    :on-exceed="handleExceed"
    :on-change="handleFileChange"
    :on-remove="handleFileChange"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <el-button v-if="type === 'file'" size="small" icon="el-icon-upload">
      点击上传
    </el-button>
    <i v-else class="el-icon-plus"></i>

    <div slot="tip" v-if="tip">{{ tip }}</div>
  </el-upload>
</template>

<script>
import axios from "@/utils/axios";

export default {
  props: {
    value: {
      type: Array,
      default: () => [],
    },
    // 文件上传地址
    action: {
      type: String,
      default: "/api/localFile/upload",
    },

    // 上传组件类型，可选值'img','file'
    type: {
      type: String,
      default: "img",
      validator(value) {
        // 这个值必须匹配下列字符串中的一个
        return ["img", "file"].indexOf(value) !== -1;
      },
    },

    // 文件大小限制 单位m
    fileSize: {
      type: Number,
      default: 0,
      validator(value) {
        // 这个值必须大于等于0
        return value >= 0;
      },
    },

    // 可上传的文件类型
    accept: {
      type: String,
      default: "",
    },

    // 提示说明文字
    tip: {
      type: String,
      default: "",
    },

    min: {
      type: Boolean,
      default: false,
    },

    // eslint-disable-next-line vue/require-default-prop
    limit: Number,
  },
  computed: {
    fileList: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },
  methods: {
    // 点击图片预览
    handlePreview(file) {
      this.type === "img" &&
        this.$imgPreview({ urlList: [file.url], zIndex: 9999 });
    },

    // 自定义文件上传
    httpRequest(content) {
      const formData = new FormData();
      formData.append(content.filename, content.file);
      Object.keys(content.data || {}).forEach((key) => {
        formData.append(key, content.data[key]);
      });

      axios({
        url: content.action,
        method: "post",
        headers: {
          "Content-Type": "multipart/form-data",
          ...content.headers,
        },
        data: formData,
      })
        .then((res) => {
          content.onSuccess(res.data, content.file);
        })
        .catch((err) => {
          content.onError(err, content.file);
        });
    },

    // 文件上传前的勾子
    beforeUpload(file) {
      const words = file.name.split(".");
      const suffix = words[words.length - 1]; // 文件后缀
      const fileType = this.accept.split(",");

      const isCorrectType =
        this.accept === "" ? true : fileType.includes(`.${suffix}`);

      const isLtSize =
        this.fileSize === 0 ? true : file.size / 1024 / 1024 < this.fileSize;

      if (!isCorrectType) {
        this.$message.error(`只能上传${this.accept}格式!`);
      }
      if (!isLtSize) {
        this.$message.error(`上传文件大小不能超过${this.fileSize}MB!`);
      }
      return isCorrectType && isLtSize;
    },

    // 文件超出个数限制时的钩子
    handleExceed() {
      this.$message.error(`最多只能上传${this.limit}个文件`);
    },

    /**
     * file-list 改变时
     * on-change 和 on-remove
     */
    handleFileChange(file, fileList) {
      const data = [];
      for (const item of fileList) {
        const item2 = item;
        if (item.response && item.response.url) {
          item2.url = item.response.url;
        }
        data.push(item2);
      }
      this.fileList = data;
    },
  },
};
</script>

<style lang="scss">
.upload-my {
  /* 上传图片框样式 */
  .el-upload--picture-card {
    border: 1px dashed #13c3c7;
  }

  .el-upload--picture-card:hover {
    border-color: #13c3c7;
    color: #13c3c7;
  }

  .el-upload--picture-card {
    width: 80px;
    height: 80px;
    font-size: 16px !important;
  }

  .el-upload {
    width: 80px;
    height: 80px;
    line-height: 80px;
    font-size: 16px;
  }

  .el-upload-list--picture-card .el-upload-list__item {
    width: 80px;
    height: 80px;
    line-height: 80px;
    font-size: 16px;
  }

  .el-upload-list--picture-card .el-upload-list__item-actions:hover {
    opacity: 1;
    font-size: 16px;
  }

  .el-upload-list--picture-card .el-upload-list__item-thumbnail {
    width: 80px;
    height: 80px;
    line-height: 80px;
    font-size: 16px;
  }

  .avatar {
    width: 80px;
    height: 80px;
  }
}

.heh {
  .el-upload {
    display: none;
  }
}
</style>
