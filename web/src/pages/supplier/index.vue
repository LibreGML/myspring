<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="账号id" prop="accountId">
          <el-input v-model="head.accountId" placeholder="请输入账号id" />
        </el-form-item>
        <el-form-item label="审核状态" prop="examine">
          <DictSelect v-model="head.examine" dict-type="examine-stu" />
        </el-form-item>
      </el-form>
    </DropdownHead>

    <!--这部分按钮必须是文字类型按钮加且必须加图标-->
    <MyButtonGroup
      :max-number="-1"
      :space-size="20"
      class="auto-box"
      style="padding-bottom:16px"
      :but-list="[
        {
          text: '新增',
          icon: 'el-icon-document-add',
          onClick: () => $refs['addOrEdit'].addStart(),
        },
      ]"
    >
    </MyButtonGroup>

    <div class="main">
      <el-table
        ref="table"
        v-loading="loading"
        :data="tableData"
        border
        :height="`calc(100vh - ${m_boxHeightAll}px - 240px)`"
        style="width: 100%"
      >
        <el-table-column prop="accountId" label="账号id"></el-table-column>
        <el-table-column prop="account" label="账号"></el-table-column>
        <el-table-column prop="name" label="供应商名称"></el-table-column>
        <el-table-column
          prop="totalOrders"
          label="总订单量"
          min-width="50px"
        ></el-table-column>
        <el-table-column
          prop="balance"
          label="余额剩余"
          min-width="50px"
        ></el-table-column>
        <el-table-column prop="examine" label="审核状态" min-width="50px">
          <template slot-scope="{ row }">
            <div v-if="row.examine === 1" style="color: rgb(0, 255, 170);">
              待审核
            </div>
            <div v-else-if="row.examine === 2" style="color: rgb(0, 98, 255);">
              审核通过
            </div>
            <div v-else-if="row.examine === 3" style="color: rgb(255, 0, 0);">
              审核失败
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="examineReason"
          label="审核原因"
          min-width="100px"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          min-width="0"
        ></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '详情',
                  // onClick: () => $router.push(`/supplier/details?id=${row.id}`),
                  onClick: () =>
                    $router.push(`/account/details?id=${row.accountId}`),
                },
                {
                  text: '审核',
                  onClick: () => $refs.checkDia.start(row.id),
                  disabled: row.examine === 2,
                },
                {
                  text: '编辑',
                  onClick: () => $refs.addOrEdit.editStart(row),
                },
                {
                  text: '删除',
                  onClick: () => delRow(row.id),
                  color: '#f56c6c',
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
    <AddOrEdit ref="addOrEdit" :callback="getTableData" />
    <CheckDia ref="checkDia" :callback="getTableData" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import AddOrEdit from "./components/AddOrEdit";
import CheckDia from "./components/CheckDia";
import MyButtonGroup from "@/components/MyButtonGroup";
import DictSelect from "@/components/select/DictSelect";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Supplier",
  components: {
    DropdownHead,
    AddOrEdit,
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
        examine: "",
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
        .post("/api/supplier/list", {
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
        axios.post(`/api/supplier/del?key=${id}`).then(() => {
          this.getTableData(1);
        });
      });
    },
  },
};
</script>
