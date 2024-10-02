<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="订单号" prop="order">
          <el-input v-model="head.order" placeholder="请输入订单号" />
        </el-form-item>
      </el-form>
    </DropdownHead>

    <div class="main">
      <el-table
        ref="table"
        v-loading="loading"
        :data="tableData"
        border
        :height="`calc(100vh - ${m_boxHeightAll}px - 240px)`"
        style="width: 100%"
      >
        <el-table-column prop="order" label="订单号"></el-table-column>
        <el-table-column prop="issue" label="理由"></el-table-column>
        <el-table-column prop="reply" label="回复"></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column
          v-if="$store.state.userInfo.account.roleType !== '1'"
          label="操作"
          fixed="right"
          width="150"
        >
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '回复',
                  onClick: () => $refs.Reply.start(row.id),
                  hidden: $store.state.userInfo.account.roleType !== '3',
                },
                {
                  text: '申请介入',
                  onClick: () => jirru(row.id),
                  hidden: $store.state.userInfo.account.roleType !== '2',
                },
              ]"
            />
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: right;margin-top:20px;">
        <el-pagination
          :current-page="pagination.pageNum"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          @current-change="getTableData"
        >
        </el-pagination>
      </div>
    </div>
    <CheckDia ref="checkDia" :callback="getTableData" />
    <Reply ref="Reply" :callback="getTableData" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import CheckDia from "./components/CheckDia";
import Reply from "../order/components/Reply";
import MyButtonGroup from "@/components/MyButtonGroup";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Sales",
  components: {
    DropdownHead,
    CheckDia,
    Reply,
    MyButtonGroup,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        order: "",
      },
      loading: false,
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  created() {
    this.searchHead();
  },
  methods: {
    // 根据当前head表单数据查询第一页
    searchHead() {
      backupFormData = JSON.parse(JSON.stringify(this.head));
      this.getTableData(1);
    },

    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      axios
        .post("/api/aftersales/list", {
          pageNum: page || this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
          ...backupFormData,
        })
        .then((value) => {
          /**
           * 具体代码需要参考接口返回字段
           * 任何情况下接口必须都返回当前页和数据总条数
           * 不需要再次判断code是否等于200
           */
          const { data } = value;
          this.tableData = data.records || [];
          this.pagination.pageNum = data.current;
          this.pagination.total = data.total;
          this.loading = false;
        })
        .finally(() => {
          this.loading = false;
        });
    },

    // 重置搜索栏
    resetHead() {
      this.$refs["headForm"].resetFields();
      this.searchHead();
    },

    jirru(id) {
      this.$confirm("回复不满意，申请介入, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios
          .post("/api/aftersales/update", {
            id,
            status: 2,
          })
          .then(() => {
            this.$message({
              type: "success",
              message: "申请成功!",
            });
            this.getTableData(1);
          });
      });
    },
  },
};
</script>
