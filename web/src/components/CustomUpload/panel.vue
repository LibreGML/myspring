<template>
  <div>
    <div class="upload-body">
      <el-upload
        :action="action"
        multiple
        :show-file-list="false"
        :accept="accept"
        :name="name"
        drag
        :on-success="onSuccess"
        :http-request="httpRequest"
        :file-list="fileList"
        :on-change="(_, files) => (fileList = files)"
        :limit="limit"
        :on-exceed="() => $message.error('文件数量超过限制！')"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div v-if="tipDefault" class="el-upload__tip" slot="tip">
          {{ tipDefault }}
        </div>
      </el-upload>

      <div>
        <div v-loading="loading" class="qr-code">
          <el-image
            :src="qrCodeUrl"
            class="qr-code-img"
            :style="{ opacity: validTime === 0 ? '0.1' : '1' }"
          />
          <div class="qrcode-drawer">
            <span class="refresh" title="点击刷新" @click="initH5upload">
              <i class="el-icon-refresh-right"></i>
            </span>
          </div>
        </div>
        <div class="el-upload__tip">
          手机扫码上传（
          <span v-if="validTime > 0">{{ validTime }}s后过期</span>
          <span v-else style="color:#f56c6c">已过期</span>
          ）
        </div>
      </div>
    </div>

    <!-- 文件列表 -->
    <ul class="el-upload-list el-upload-list--picture-card">
      <li
        v-for="(item, index) in fileList"
        :key="index"
        class="el-upload-list__item is-success"
      >
        <CompressedImage
          v-if="item.status !== 'uploading'"
          :src="item.url"
          :width="118"
          :height="118"
          class="el-upload-list__item-thumbnail"
        >
          <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
        </CompressedImage>
        <label class="el-upload-list__item-status-label">
          <i class="el-icon-upload-success el-icon-check"></i>
        </label>
        <i class="el-icon-close"></i>
        <el-progress
          v-if="item.status === 'uploading'"
          type="circle"
          :stroke-width="6"
          :percentage="parseInt(item.percentage, 10)"
          :width="100"
          class="img-progress"
        >
        </el-progress>
        <span class="el-upload-list__item-actions">
          <span
            class="el-upload-list__item-preview"
            @click="
              $filePreview({
                urlList: value,
                initialIndex: index,
                zIndex: 3100,
              })
            "
          >
            <i class="el-icon-zoom-in"></i>
          </span>
          <span class="el-upload-list__item-delete" @click="onDelete(index)">
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import CompressedImage from "@/components/CompressedImage";

let intervalId;

export default {
  components: { CompressedImage },
  props: {
    value: {
      type: Array,
      required: true,
    },
    action: {
      type: String,
      required: true,
    },
    // eslint-disable-next-line vue/require-default-prop
    limit: Number,
    // eslint-disable-next-line vue/require-default-prop
    accept: String,
    // eslint-disable-next-line vue/require-default-prop
    tip: String,
    // eslint-disable-next-line vue/require-default-prop
    name: String,
  },
  data() {
    return {
      uuid: "",
      qrCodeUrl: "",
      validTime: 0,
      loading: false,

      isUnmounted: false, // 组件是否已被销毁

      fileList: [],
    };
  },
  computed: {
    tipDefault() {
      const { tip, accept, limit } = this;
      if (tip) {
        return tip;
      } else if (tip === false) {
        return "";
      } else {
        let string = "";
        if (limit) string += `，最多只能上传${limit}个文件`;
        if (accept) string += `，只能上传${accept}文件`;
        return string.substring(1);
      }
    },
  },
  watch: {
    value: {
      handler(val) {
        this.fileList = val.map((url) => ({ url }));
      },
      immediate: true,
    },
  },
  created() {
    this.initH5upload();
  },
  beforeDestroy() {
    this.isUnmounted = true;

    // 停止定时器
    clearInterval(intervalId);

    this.stop();
  },
  methods: {
    // 初始化手机上传
    initH5upload() {
      this.uuid && this.stop();
      this.loading = true;
      axios
        .post(
          "https://test-sysfile.gshbzw.com/api/core/pro-upload/create-qrcode",
          {
            limit: this.limit,
            accept: this.accept,

            // TODO：目前的接口是一个单独唯一的服务，我认为h5也应该是一个单独唯一的，但现在不是，所以他们说这样传一个域名
            origin: location.origin,
          }
        )
        .then((value) => {
          const { data } = value;
          this.uuid = data.uuid;
          this.qrCodeUrl = data.qrCode;
          this.validTime = data.validTime || 0;

          this.countDown();

          this.pushFileList();
          this.watchFileList();
        })
        .finally(() => {
          this.loading = false;
        });
    },

    // 有效日期倒计时
    countDown() {
      clearInterval(intervalId);
      intervalId = setInterval(() => {
        if (this.validTime > 0) {
          this.validTime--;
        } else {
          clearInterval(intervalId);
        }
      }, 1000);
    },

    // 监测后台文件改变
    watchFileList() {
      const { uuid } = this;
      if (this.isUnmounted) return;
      if (this.validTime <= 0) return;
      axios({
        url:
          "https://test-sysfile.gshbzw.com/api/core/pro-upload/file-list/get",
        method: "post",
        data: { uuid, source: "pc" },
        timeout: 0, // 此接口不能设置超时，因为这就是用来等待数据更新以模拟实时的
      })
        .then((value) => {
          // 如果uuid和当前的不同说明用户刷新过了
          if (this.uuid === uuid) {
            const { data } = value;
            if (data.files) this.$emit("input", data.files || []);
            this.validTime = data.validTime || 0;
            this.countDown();
            if (!data.isStop) this.watchFileList();
          }
        })
        .catch(() => {
          if (this.uuid === uuid) {
            setTimeout(() => {
              this.watchFileList();
            }, 1000);
          }
        });
    },

    // 删除文件
    onDelete(index) {
      const valueCopy = [...this.value];
      valueCopy.splice(index, 1);
      this.$emit("input", valueCopy);
      this.pushFileList();
    },

    // 本地的操作导致的value改变后，需要向后台推送
    pushFileList() {
      this.$nextTick(() => {
        axios
          .post(
            "https://test-sysfile.gshbzw.com/api/core/pro-upload/file-list/update",
            {
              uuid: this.uuid,
              source: "pc",
              files: this.value,
            }
          )
          .catch(() => {
            this.$confirm("文件推送失败!", "提示", {
              confirmButtonText: "重试",
              cancelButtonText: "关闭",
              type: "error",
            }).then(() => this.pushFileList());
          });
      });
    },

    // 文件上传成功的回调
    onSuccess(response, file, files) {
      // 注意：这里可能有个隐藏的bug，假设总共五张图片 前面四张都成功了 但是最后一张失败了，此时前面五张都会上传失败
      // 可能有个办法就是将此部分代码移动到on-change
      if (files.some((item) => ["uploading", "ready"].includes(item.status))) {
        // 如果还有图片正在上传中，则等待都上传成功后再提交
        return;
      }
      const fileUrls = files.map((item) => item.url || item.response);
      this.$emit("input", fileUrls);
      this.pushFileList();
    },

    // 自定义上传组件的上传事件
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
        onUploadProgress(e) {
          content.onProgress({
            percent: (e.loaded / e.total) * 100,
          });
        },
      })
        .then((res) => {
          const { data } = res;
          content.onSuccess(data.url, content.file);
        })
        .catch((err) => {
          content.onError(err, content.file);
        });
    },

    stop() {
      // 通知后台停止本次上传
      axios.post("https://test-sysfile.gshbzw.com/api/core/pro-upload/stop", {
        uuid: this.uuid,
      });
    },
  },
};
</script>

<style scoped>
.el-upload-list {
  display: block;
  margin-top: 8px;
}
.el-upload--picture-card {
  width: 120px;
  height: 120px;
  line-height: 120px;
}
.el-upload-list--picture-card .el-upload-list__item {
  width: 120px;
  height: 120px;
}
.el-upload-list >>> .image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 24px;
}
.el-upload-list >>> .img-progress {
  width: 120px;
  height: 120px;
  padding: 10px;
  box-sizing: border-box;
}

.upload-body {
  display: grid;
  grid-template-columns: auto 180px;
  grid-column-gap: 20px;
}
.upload-body >>> .el-upload {
  display: block;
}
.upload-body >>> .el-upload-dragger {
  width: 100%;
}

.qr-code {
  width: 180px;
  height: 180px;
  position: relative;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  box-sizing: border-box;
  overflow: hidden;
}

.qr-code .qr-code-img {
  width: 100%;
  height: 100%;
}

.qrcode-drawer {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  opacity: 0;
  font-size: 20px;
  background-color: rgba(0, 0, 0, 0.5);
  transition: opacity 0.3s;
}
.qrcode-drawer:hover {
  opacity: 1;
}
.qrcode-drawer .refresh {
  cursor: pointer;
}
</style>
