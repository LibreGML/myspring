<template>
  <el-card>
    <div slot="header" class="card-header">
      <slot name="title">
        <div>{{ title }}</div>
      </slot>
      <div v-if="!isDisable">
        <el-button
          v-if="!isEdit"
          style="padding: 3px 0"
          type="text"
          @click="isEdit = true"
        >
          <i class="el-icon-edit"></i>
          修改
        </el-button>
        <template v-else>
          <el-button style=" padding: 3px 0" type="text" @click="cancel">
            取消
          </el-button>
          <el-button style=" padding: 3px 0" type="text" @click="submit">
            保存
          </el-button>
        </template>
      </div>
    </div>

    <el-form
      ref="form"
      :class="{ 'descriptions-form': !isEdit }"
      :label-suffix="isEdit ? '' : ':'"
      v-bind="$attrs"
      v-on="$listeners"
      @submit.native.prevent
    >
      <slot :isEdit="isEdit"></slot>
    </el-form>
  </el-card>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "",
    },
    // 编辑按钮权限标识，用来控制是否可编辑
    authButKey: {
      type: String,
      default: "edit",
    },
  },
  data() {
    return {
      isEdit: false,
      isDisable: false,
    };
  },
  watch: {
    isEdit(val) {
      if (val) {
        this.$emit("opened"); // 打开表单的回调
      } else {
        this.$emit("closed"); // 取消表单的回调
      }
    },
    authButKey() {
      this.handle();
    },
  },
  created() {
    this.handle();
  },
  methods: {
    // 点击保存
    submit() {
      this.$refs["form"].validate((valid) => {
        this.$emit("submit", valid);
      });
    },
    /**
     * 点击取消时执行
     * 必须重置数据和
     */
    cancel() {
      this.isEdit = false;
      this.$refs["form"].resetFields();
    },

    handle() {
      if (this.$checkPermission.call(this, this.authButKey)) {
        this.isDisable = false;
      } else {
        this.isDisable = true;
      }
    },
  },
};
</script>

<style scoped>
/* .my-form >>> .el-form-item {
  transition: margin-bottom 0.3s;
} */
.descriptions-form >>> .el-form-item {
  margin-bottom: 0;
}
.descriptions-form >>> .el-form-item .el-form-item__label:before {
  content: "" !important;
  margin-right: 0px !important;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
