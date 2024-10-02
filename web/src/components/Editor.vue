<template>
  <div>
    <quill-editor
      ref="myTextEditor"
      v-model="content"
      class="editor ql-editor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    >
    </quill-editor>

    <el-upload
      ref="imgUpload"
      action="/api/localFile/upload"
      :on-success="upScuccess"
      :http-request="httpRequest"
      :show-file-list="false"
      accept=".jpg,.png,.jpeg,.gif"
    ></el-upload>
  </div>
</template>

<script>
// 工具栏配置
import axios from "@/utils/axios";
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

const toolbarOptions = [
  ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
  ["blockquote", "code-block"], // 引用  代码块
  [{ header: 1 }, { header: 2 }], // 1、2 级标题
  [{ list: "ordered" }, { list: "bullet" }], // 有序、无序列表
  [{ script: "sub" }, { script: "super" }], // 上标/下标
  // [{ indent: "-1" }, { indent: "+1" }], // 缩进
  // [{'direction': 'rtl'}],                         // 文本方向
  [{ size: ["small", false, "large", "huge"] }], // 字体大小
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
  [{ font: [] }], // 字体种类
  [{ align: [] }], // 对齐方式
  ["clean"], // 清除文本格式
  //   ["link", "image", "video"], // 链接、图片、视频
  ["image"],
];

export default {
  components: {
    quillEditor,
  },
  props: {
    /*编辑器的内容*/
    value: {
      type: String,
      required: true,
    },
  },
  data() {
    const that = this;
    return {
      content: this.value,
      editorOption: {
        theme: "snow", // or 'bubble'
        placeholder: "请编辑内容",
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              image() {
                //触发图片上传的时候返回的信息
                that.$refs["imgUpload"].$refs["upload-inner"].handleClick();
              },
            },
          },
        },
      },
    };
  },
  watch: {
    value(newValue) {
      this.content = newValue;
    },
  },

  methods: {
    onEditorBlur() {
      //失去焦点事件
    },
    onEditorFocus() {
      //获得焦点事件
    },
    onEditorChange() {
      //内容改变事件
      this.$emit("input", this.content);
    },
    // 图片上传成功后
    upScuccess(response) {
      const imageUrl = response.url;

      const range = this.$refs.myTextEditor.quill.getSelection();
      const length = range.index;
      this.$refs.myTextEditor.quill.insertEmbed(length, "image", imageUrl);

      this.$refs["imgUpload"].clearFiles(); // 插入成功后清除input的内容
    },

    // 自定义上传
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
        })
        .finally(() => {});
    },
  },
};
</script>

<style>
.editor {
  line-height: normal;
}
.ql-snow .ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: "保存";
  padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode="video"]::before {
  content: "请输入视频地址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
</style>
