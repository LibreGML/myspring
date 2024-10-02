<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item
          v-if="$store.state.userInfo.account.roleType === '1'"
          label="用户ID"
          prop="accountId"
        >
          <el-input v-model="head.accountId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="提现状态" prop="status">
          <DictSelect v-model="head.status" dict-type="with-stu" />
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
        <el-table-column
          v-if="$store.state.userInfo.account.roleType === '1'"
          prop="userId"
          label="用户ID"
        ></el-table-column>
        <el-table-column
          prop="withdrawalAmount"
          label="提现金额"
        ></el-table-column>
        <el-table-column prop="free" label="费率"></el-table-column>
        <el-table-column
          prop="accountAmount"
          label="到账金额"
        ></el-table-column>
        <el-table-column prop="status" label="提现状态" min-width="50px">
          <template slot-scope="{ row }">
            <div v-if="row.status === '1'" style="color: rgb(0, 255, 170);">
              待处理
            </div>
            <div v-else-if="row.status === '2'" style="color: rgb(255, 0, 0);">
              已退回
            </div>
            <div v-else-if="row.status === '3'" style="color: rgb(0, 98, 255);">
              已打款
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="remarks"
          label="备注"
          min-width="100px"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="申请时间"
          min-width="0"
        ></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '处理',
                  onClick: () => $refs.checkDia.start(row.id, row),
                  disabled: row.status !== '1',
                  hidden: $store.state.userInfo.account.roleType !== '1',
                },
                {
                  text: '删除',
                  onClick: () => delRow(row.id),
                  disabled: row.status !== '1',
                  hidden: $store.state.userInfo.account.roleType === '1',
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
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import CheckDia from "./components/CheckDia";
import MyButtonGroup from "@/components/MyButtonGroup";
import DictSelect from "@/components/select/DictSelect";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Withdrawal",
  components: {
    DropdownHead,
    MyButtonGroup,
    CheckDia,
    DictSelect,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        accountId: "",
        status: "",
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
        .post("/api/withdrawal/list", {
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

    // 单个删除，一定要二次确认
    delRow(id) {
      this.$confirm("确定要删除该条数据吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios.post(`/api/withdrawal/del?key=${id}`).then(() => {
          this.getTableData(1);
        });
      });
    },
  },
};
</script>
