<template>
  <div>
    <NoticeBar
      v-if="validTime > 0"
      color="#1989fa"
      background="#ecf9ff"
      left-icon="info-o"
      wrapable
    >
      此页面将在 {{ validTime }}s 后失效，请尽快完成上传。
    </NoticeBar>
    <NoticeBar v-else left-icon="info-o" wrapable>
      页面已过期，请重新扫码进入。
    </NoticeBar>

    <div class="container">
      <Uploader
        v-model="fileList"
        :accept="accept"
        :after-read="afterRead"
        :before-delete="beforeDelete"
      />
      <div v-if="tipDefault" class="tip">{{ tipDefault }}</div>
    </div>
  </div>
</template>

<script>
import axios from "./utils/axios";
import { allUrlParam } from "./utils/index";
import Uploader from "vant/lib/uploader";
import NoticeBar from "vant/lib/notice-bar";
import Notify from "vant/lib/notify";
import "vant/lib/uploader/style";
import "vant/lib/notice-bar/style";
import "vant/lib/notify/style";

let intervalId;

export default {
  components: { Uploader, NoticeBar },
  data() {
    return {
      fileList: [],

      uuid: undefined,
      limit: undefined,
      accept: undefined,

      validTime: 300, // 过期倒计时
      isUnmounted: false, // 组件是否已被销毁
    };
  },
  computed: {
    tipDefault() {
      const { accept, limit } = this;
      let string = "";
      if (limit) string += `，最多只能上传${limit}个文件`;
      if (accept) string += `，只能上传${accept}文件`;
      return string.substring(1);
    },
  },
  created() {
    const { uuid, limit, accept } = allUrlParam(); // 目前没使用router,所以用这种方式获取地址栏参数
    this.uuid = uuid;
    this.limit = limit;
    this.accept = accept;

    this.initFile();
  },
  beforeDestroy() {
    this.isUnmounted = true;

    // 停止定时器
    clearInterval(intervalId);
  },
  methods: {
    initFile() {
      axios({
        url:
          "https://test-sysfile.gshbzw.com/api/core/pro-upload/file-list/init",
        method: "post",
        data: { uuid: this.uuid },
      })
        .then((value) => {
          const { data } = value;
          this.fileList = (data.files || []).map((url) => ({ url }));
          // 同步一下过期时间
          this.validTime = data.validTime;
          this.countDown();
          this.watchFileList();
        })
        .catch(() => {
          this.validTime = 0;
        });
    },

    afterRead() {
      this.handlePush();
    },

    // 上传本地的文件
    handlePush() {
      if (this.limit && this.fileList.length > this.limit) {
        Notify({ type: "warning", message: "文件数量超过限制" });
      } else {
        Promise.all(
          this.fileList
            .filter((item) => !item.url)
            .map((item) => {
              const formData = new FormData();
              formData.append("file", item.file);
              item.status = "uploading";
              item.message = "上传中...";
              return axios({
                url: "/api/system/oss/upload",
                method: "post",
                headers: {
                  "Content-Type": "multipart/form-data",
                },
                data: formData,
              })
                .then((res) => {
                  item.url = res.data.url;
                  item.status = "success";
                })
                .catch(() => {
                  item.status = "failed";
                  item.message = "上传失败";
                });
            })
        ).then(() => {
          axios.post(
            "https://test-sysfile.gshbzw.com/api/core/pro-upload/file-list/update",
            {
              uuid: this.uuid,
              source: "h5",
              files: this.fileList
                .filter((item) => !!item.url)
                .map((item) => item.url),
            }
          );
        });
      }
    },

    // 我需要在文件删除之后取消上传按钮的禁用
    beforeDelete() {
      this.handlePush();
      return true;
    },

    // 监测后台文件改变
    watchFileList() {
      if (!this.uuid) return;
      if (this.isUnmounted) return;
      if (this.validTime <= 0) return;
      axios({
        url:
          "https://test-sysfile.gshbzw.com/api/core/pro-upload/file-list/get",
        method: "post",
        data: { uuid: this.uuid, source: "h5" },
        timeout: 0, // 此接口不能设置超时，因为这就是用来等待数据更新以模拟实时的
      })
        .then((value) => {
          const { data } = value;

          if (data.files) {
            this.fileList = data.files.map((url) => ({ url }));
          }

          // 同步一下过期时间
          this.validTime = data.validTime;
          this.countDown();

          if (!data.isStop) this.watchFileList();
        })
        .catch(() => {
          setTimeout(() => {
            this.watchFileList();
          }, 1000);
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
  },
};
</script>

<style scoped>
.container {
  margin-bottom: 76px;
  padding: 16px;
}
.tip {
  font-size: 12px;
  color: #606266;
}
.footer {
  width: 100%;
  position: fixed;
  left: 0;
  bottom: 0;
  padding: 16px;
  box-sizing: border-box;
  background-color: #fff;
}
</style>
