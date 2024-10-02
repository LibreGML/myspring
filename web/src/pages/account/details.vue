<template>
  <div>
    <DescriptionsForm
      :model="form"
      title="基础信息"
      label-width="130px"
      @submit="onSubmit"
      @opened="cacheFormData"
      @closed="resetFormData"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="头像" prop="account.headPic">
            <el-image
              style="width: 100px;height: 100px;"
              :src="form.account.headPic"
            ></el-image>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="账户ID" prop="account.id">
            <div>{{ form.account.id }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="账户类型" prop="account.roleType">
            <div>
              {{
                form.account.roleType === "2"
                  ? "商家"
                  : form.account.roleType === "3"
                  ? "供应商"
                  : ""
              }}
            </div>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="form.account.roleType === '2'">
        <el-col :md="8" :lg="6">
          <el-form-item label="商家名称" prop="business.name">
            {{ form.business.name }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="会员等级" prop="business.level">
            {{ form.business.level }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="额度包" prop="business.quota">
            {{ form.business.quota }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="钱包" prop="business.wallet">
            {{ form.business.wallet }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="到期时间" prop="business.expirationTime">
            {{ form.business.expirationTime }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="创建时间" prop="business.createTime">
            {{ form.business.createTime }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="form.account.roleType === '3'">
        <el-col :md="8" :lg="6">
          <el-form-item label="供应商名称" prop="supplier.name">
            {{ form.supplier.name }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="余额剩余" prop="supplier.balance">
            {{ form.supplier.balance }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="创建时间" prop="supplier.createTime">
            {{ form.supplier.createTime }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="审核状态" prop="supplier.examine">
            <div
              v-if="form.supplier.examine === 1"
              style="color: rgb(0, 255, 170);"
            >
              待审核
            </div>
            <div
              v-if="form.supplier.examine === 2"
              style="color: rgb(0, 98, 255);"
            >
              审核通过
            </div>
            <div
              v-if="form.supplier.examine === 3"
              style="color: rgb(255, 0, 0);"
            >
              审核失败
            </div>
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="审核原因" prop="supplier.examineReason">
            {{ form.supplier.examineReason }}
          </el-form-item>
        </el-col>
      </el-row>
    </DescriptionsForm>
    <DescriptionsForm
      v-if="form.account.roleType !== '1'"
      :model="form"
      title="其他信息"
      label-width="130px"
    >
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="订单明细" name="1">
          <el-table
            ref="table"
            v-loading="loading"
            :data="tableData"
            border
            :height="500"
            style="width: 100%"
          >
            <el-table-column
              prop="orderNumber"
              label="订单号"
            ></el-table-column>
            <el-table-column
              prop="productName"
              label="商品名"
              min-width="350px"
            ></el-table-column>
            <el-table-column
              prop="orderInfo"
              label="下单信息"
            ></el-table-column>
            <el-table-column
              prop="quantity"
              label="份数"
              min-width="50px"
            ></el-table-column>
            <el-table-column
              prop="paymentAmount"
              label="支付金额"
            ></el-table-column>
            <el-table-column prop="merchantId" label="商家ID"></el-table-column>
            <el-table-column
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
                <div v-if="row.status === '1'" style="color: rgb(0, 255, 170);">
                  下单成功
                </div>
                <div
                  v-else-if="row.status === '2'"
                  style="color: rgb(0, 98, 255);"
                >
                  已申请售后
                </div>
                <div
                  v-else-if="row.status === '3'"
                  style="color: rgb(0, 98, 255);"
                >
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
        </el-tab-pane>
        <el-tab-pane label="收益明细" name="2">
          <el-table
            ref="table"
            v-loading="loading"
            :data="tableData"
            border
            :height="500"
            style="width: 100%"
          >
            <el-table-column prop="type" label="流水类型"></el-table-column>
            <el-table-column prop="amount" label="金额"></el-table-column>
            <el-table-column prop="balance" label="余额"></el-table-column>
            <el-table-column
              prop="info"
              label="信息"
              min-width="350px"
            ></el-table-column>
            <el-table-column prop="createTime" label="时间"></el-table-column>
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
        </el-tab-pane>
        <el-tab-pane label="操作日志" name="3">
          <el-table
            ref="table"
            v-loading="loading"
            :data="tableData"
            border
            :height="500"
            style="width: 100%"
          >
            <el-table-column
              prop="type"
              label="操作类型"
              width="150"
            ></el-table-column>
            <el-table-column
              prop="acceptParam"
              label="请求类型"
            ></el-table-column>
            <el-table-column
              prop="responseParam"
              label="响应类型"
            ></el-table-column>
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
        </el-tab-pane>
      </el-tabs>
    </DescriptionsForm>
  </div>
</template>

<script>
// 最初的表单值，用于重置
// 表单默认项

import DescriptionsForm from "@/components/DescriptionsForm";
import axios from "@/utils/axios";
let initialFormData = null;
export default {
  name: "AccountDetails",
  components: { DescriptionsForm },
  data() {
    return {
      loading: false,
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      activeName: "1",
      form: {
        account: {
          id: "",
          headPic: "",
          roleType: "",
        },
        business: {
          account: "",
          accountId: "",
          createTime: "",
          expirationTime: "2",
          headPic: null,
          id: "",
          level: null,
          name: "",
          quota: null,
          updateTime: "",
          wallet: null,
        },
        supplier: {
          account: "",
          accountId: "",
          createTime: "",
          headPic: null,
          id: "",
          balance: "",
          name: "",
          updateTime: "",
          examine: "",
          examineReason: "",
        },
      },
    };
  },
  created() {
    axios
      .get(`/api/account/detail?key=${this.$route.query.id}`)
      .then((value) => {
        const { data } = value;
        Object.keys(this.form).forEach((key) => {
          this.form[key] = data[key];
        });
        this.cacheFormData();
        this.getTableData();
      });
  },
  methods: {
    handleClick() {
      this.getTableData();
    },
    onSubmit(valid) {
      if (valid) {
        // 表单验证通过
        // 提交表单成功之后可以通过ref关闭form状态
      } else {
        // 表单验证不通过
      }
    },

    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      let url = "";
      if (this.activeName === "1") {
        url = `/api/order/list?key=${this.$route.query.id}`;
      }
      if (this.activeName === "2") {
        url = `/api/fund/list?key=${this.$route.query.id}`;
      }
      if (this.activeName === "3") {
        url = `/api/operate-log/list?key=${this.$route.query.id}`;
      }
      axios
        .post(url, {
          pageNum: page || this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
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

    // 缓存form表单数据
    cacheFormData() {
      initialFormData = JSON.parse(JSON.stringify(this.form)); // 缓存初始表单数据 ;
    },

    // 恢复表单数据
    resetFormData() {
      this.form = initialFormData;
    },
  },
};
</script>
