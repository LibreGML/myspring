<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="货源系统" prop="sourceId">
          <el-select v-model="head.sourceId" placeholder="请选择货源系统">
            <el-option
              v-for="item in sourceOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
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
          hidden: $store.state.userInfo.account.roleType !== '3',
        },
        {
          text: '添加在售商品入口',
          icon: 'el-icon-document-add',
          onClick: () => addpro(),
          hidden: $store.state.userInfo.account.roleType !== '1',
        },
        {
          text: '刷新商品数据',
          icon: 'el-icon-document-add',
          onClick: () => reshProList(),
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
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="source" label="货源系统">
          <template slot-scope="{ row }">
            {{ getsourceOptions(row.sourceSystem) }}
          </template>
        </el-table-column>
        <el-table-column
          v-if="$store.state.userInfo.account.roleType === '1'"
          prop="accountId"
          label="供应商Id"
        ></el-table-column>
        <el-table-column
          v-if="$store.state.userInfo.account.roleType === '1'"
          prop="supplierName"
          label="供应商名称"
        ></el-table-column>
        <el-table-column
          prop="sourceWebsite"
          label="对接网址"
        ></el-table-column>
        <el-table-column
          prop="dockingAccount"
          label="对接账号"
        ></el-table-column>
        <el-table-column prop="dockingKey" label="对接密匙"></el-table-column>
        <el-table-column prop="connectivity" label="连通性" min-width="50px">
          <template slot-scope="{ row }">
            <DictParse :value="row.connectivity" dict-type="yes-no" />
          </template>
        </el-table-column>
        <!-- <el-table-column prop="balance" label="账户余额"></el-table-column> -->
        <el-table-column prop="examine" label="审核状态" min-width="50px">
          <template slot-scope="{ row }">
            <div v-if="row.examine === '1'" style="color: rgb(0, 255, 170);">
              待审核
            </div>
            <div
              v-else-if="row.examine === '2'"
              style="color: rgb(0, 98, 255);"
            >
              审核通过
            </div>
            <div v-else-if="row.examine === '3'" style="color: rgb(255, 0, 0);">
              审核失败
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="examineReason"
          label="审核结果"
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
                  text: '详情',
                  onClick: () => $router.push(`/source/details?id=${row.id}`),
                },
                {
                  text: '编辑',
                  onClick: () => $refs['addOrEdit'].editStart(row),
                  hidden: $store.state.userInfo.account.roleType !== '1',
                },
                {
                  text: '审核',
                  onClick: () => $refs.checkDia.start(row.id),
                  disabled: row.examine === 2,
                  hidden: $store.state.userInfo.account.roleType !== '1',
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
    <AddOrEdit ref="addOrEdit" :callback="getTableData" />
    <AddProduct ref="addProduct" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import CheckDia from "./components/CheckDia";
import MyButtonGroup from "@/components/MyButtonGroup";
import AddOrEdit from "./components/AddOrEdit";
import AddProduct from "./components/AddProduct";
import DictParse from "@/components/DictParse";
import DictSelect from "@/components/select/DictSelect";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Source",
  components: {
    DictParse,
    DropdownHead,
    MyButtonGroup,
    CheckDia,
    AddOrEdit,
    DictSelect,
    AddProduct,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        sourceId: "",
        examine: "",
      },
      sourceOptions: [],
      loading: false,
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      multipleSelection: [],
    };
  },
  created() {
    this.searchHead();
    this.getSourceList();
  },
  methods: {
    getsourceOptions(val) {
      const e = this.sourceOptions.find((e) => e.id === val);
      if (e) {
        return e.name || "";
      }
      return "";
    },
    getSourceList() {
      axios.get("/api/official/account/all-source").then((res) => {
        this.sourceOptions = res.data;
      });
    },
    // 根据当前head表单数据查询第一页
    searchHead() {
      backupFormData = JSON.parse(JSON.stringify(this.head));
      this.getTableData(1);
    },

    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      axios
        .post("/api/source/list", {
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

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    addpro() {
      if (this.multipleSelection.length < 1) {
        this.$message.error("请先至少选择一个货源");
        return;
      }
      this.$refs.addProduct.addStart(this.multipleSelection);
    },

    reshProList() {
      axios.get("/api/source/reshProList").then(() => {
        this.$message.success("刷新开始，后台处理中");
      });
    },
  },
};
</script>
