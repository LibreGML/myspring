<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="商品id" prop="id">
          <el-input v-model="head.id" placeholder="请输入商品id" />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="head.name" placeholder="请输入商品名称" />
        </el-form-item>
      </el-form>
    </DropdownHead>
    <MyButtonGroup
      :max-number="-1"
      :space-size="20"
      class="auto-box"
      style="padding-bottom:16px"
      :but-list="[
        {
          text: '更换分类',
          icon: 'el-icon-document-add',
          onClick: () => eidtCa(),
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
        <el-table-column
          prop="id"
          label="商品id"
          :width="200"
        ></el-table-column>
        <el-table-column prop="accountId" label="商品图片" :width="100">
          <template slot-scope="{ row }">
            <el-image
              style="width: 50px; height: 50px"
              :src="row.image"
              fit="fill"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column
          prop="price"
          label="商品价格"
          :width="100"
        ></el-table-column>
        <el-table-column
          prop="categoryName"
          label="商品分类"
          min-width="50px"
        ></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
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
    <AddSoure ref="addSoure" :callback="getTableData" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import MyButtonGroup from "@/components/MyButtonGroup";
import AddSoure from "./components/AddSoure";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Product",
  components: {
    DropdownHead,
    MyButtonGroup,
    AddSoure,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        id: "",
        name: "",
        examine: "",
      },
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
        .post("/api/product/list", {
          pageNum: page || this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
          ...backupFormData,
          category: this.$route.query.id,
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

    eidtCa() {
      if (this.multipleSelection.length < 1) {
        this.$message.error("请先选择一个分类或商品");
        return;
      }
      this.$refs.addSoure.addStart(this.multipleSelection);
    },
  },
};
</script>
