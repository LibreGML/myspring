<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="订单号" prop="accountId">
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
        <el-table-column prop="orderNumber" label="订单号"></el-table-column>
        <el-table-column
          prop="productName"
          label="商品名"
          min-width="350px"
        ></el-table-column>
        <el-table-column prop="orderInfo" label="下单信息"></el-table-column>
        <el-table-column
          prop="quantity"
          label="份数"
          min-width="50px"
        ></el-table-column>
        <el-table-column
          prop="paymentAmount"
          label="支付金额"
        ></el-table-column>
        <el-table-column
          v-if="$store.state.userInfo.account.roleType === '1'"
          prop="merchantId"
          label="商家ID"
        ></el-table-column>
        <el-table-column
          v-if="$store.state.userInfo.account.roleType === '1'"
          prop="supplierId"
          label="供应商ID"
        ></el-table-column>
        <el-table-column
          prop="integrationResponse"
          label="对接返回信息"
        ></el-table-column>
        <el-table-column
          prop="paymentMethod"
          label="订单状态"
          min-width="100px"
        >
          <template slot-scope="{ row }">
            <div v-if="row.status === '-1'" style="color: rgb(0, 0, 0);">
              下单失败
            </div>
            <div v-if="row.status === '1'" style="color: rgb(0, 255, 170);">
              下单成功
            </div>
            <div v-else-if="row.status === '2'" style="color: rgb(0, 98, 255);">
              已申请售后
            </div>
            <div v-else-if="row.status === '3'" style="color: rgb(0, 98, 255);">
              已后台介入
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="merchantBalance"
          label="商家账户余额"
        ></el-table-column>
        <el-table-column
          prop="merchantQuotaBalance"
          label="商家额度包余额"
        ></el-table-column>
        <el-table-column
          prop="orderTime"
          label="订单时间"
          min-width="0"
        ></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '详情',
                  onClick: () => $router.push(`/order/details?id=${row.id}`),
                },
                {
                  text: '补单',
                  onClick: () => mentary(row.id),
                  disabled: row.status !== '-1',
                  hidden: $store.state.userInfo.account.roleType !== '2',
                },
                {
                  text: '申请售后',
                  onClick: () => $refs.checkDia.start(row.id),
                  disabled: row.status !== '1',
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
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import CheckDia from "./components/CheckDia";
import MyButtonGroup from "@/components/MyButtonGroup";

// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Order",
  components: {
    DropdownHead,
    MyButtonGroup,
    CheckDia,
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
        .post("/api/order/list", {
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

    mentary(id) {
      this.$confirm("确定要补单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios.post(`/api/order/mentary?key=${id}`).then(() => {
          this.$message({
            type: "success",
            message: "提交成功！请刷新查看结果!",
          });
          this.getTableData(1);
        });
      });
    },
  },
};
</script>
