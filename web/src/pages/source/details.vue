<template>
  <div class="page">
    <DescriptionsForm :model="form" title="货源信息" label-width="130px">
      <el-row>
        <el-col :md="8" :lg="6">
          <el-form-item label="货源系统" prop="sourceSystem">
            {{ getsourceOptions(form.sourceSystem) }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="对接网址" prop="sourceWebsite">
            {{ form.sourceWebsite }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="对接账号" prop="dockingAccount">
            {{ form.dockingAccount }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="连通性" prop="connectivity">
            <DictParse :value="form.connectivity" dict-type="yes-no" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :md="8" :lg="6">
          <el-form-item label="对接密匙" prop="dockingKey">
            {{ form.dockingKey }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :md="8" :lg="6">
          <el-form-item label="账户余额" prop="balance">
            {{ form.balance }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :md="8" :lg="6">
          <el-form-item label="申请时间" prop="createTime">
            {{ form.createTime }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="申请理由" prop="applyReason">
            {{ form.applyReason }}
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="审核状态" prop="examine">
            <div v-if="form.examine === '1'" style="color: rgb(0, 255, 170);">
              待审核
            </div>
            <div v-if="form.examine === '2'" style="color: rgb(0, 98, 255);">
              审核通过
            </div>
            <div v-if="form.examine === '3'" style="color: rgb(255, 0, 0);">
              审核失败
            </div>
          </el-form-item>
        </el-col>
        <el-col :md="8" :lg="6">
          <el-form-item label="审核原因" prop="examineReason">
            {{ form.examineReason }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row style="padding-top: 20px;">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane
            v-if="$store.state.userInfo.account.roleType === '1'"
            label="预售专区"
            name="first"
          >
            <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
              <el-form ref="headForm" :model="head" inline>
                <el-form-item label="分类名称" prop="name">
                  <el-input v-model="head.name" placeholder="请输入分类名称">
                  </el-input>
                </el-form-item>
                <el-form-item label="商品名称" prop="name">
                  <el-input v-model="head.proName" placeholder="请输入商品名称">
                  </el-input>
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
                  text: '放入在售区',
                  icon: 'el-icon-document-add',
                  onClick: () => addStart(),
                  hidden: $store.state.userInfo.account.roleType !== '1',
                },
              ]"
            >
            </MyButtonGroup>

            <div class="main">
              <el-table
                v-loading="loading"
                :data="tableData"
                style="width: 100%"
                :height="`calc(100vh - ${m_boxHeightAll}px - 240px)`"
                row-key="id"
                border
                lazy
                :load="load"
                :tree-props="{
                  children: 'children',
                  hasChildren: 'hasChildren',
                }"
                @selection-change="handleSelectionChange"
              >
                <el-table-column type="selection" width="35"> </el-table-column>
                <el-table-column
                  prop="id"
                  label="货源分类id/商品id"
                  width="150"
                >
                </el-table-column>
                <el-table-column prop="type" label="类型" width="150">
                  <template slot-scope="{ row }">
                    {{ row.type === "1" ? "分类" : "商品" }}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="imgUrl"
                  label="分类/商品图片"
                  width="150"
                >
                  <template slot-scope="{ row }">
                    <el-image
                      style="width: 50px; height: 50px"
                      :src="row.imgUrl"
                      fit="fill"
                    ></el-image>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="分类/商品名称"
                ></el-table-column>
              </el-table>
              <div style="text-align: right;margin-top:20px;">
                <el-pagination
                  :current-page="pagination.pageNum"
                  :page-size="pagination.pageSize"
                  :page-sizes="[100, 200, 300, 400]"
                  :total="pagination.total"
                  layout="total, sizes, prev, pager, next"
                  @size-change="handleSizeChange"
                  @current-change="getTableData"
                >
                </el-pagination>
              </div>
            </div>
          </el-tab-pane>
          <!-- <el-tab-pane label="在售专区" name="second">在售专区</el-tab-pane> -->
          <el-tab-pane label="插队管理" name="third">
            <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
              <el-form ref="headForm" :model="head" inline>
                <el-form-item label="商品名称" prop="name">
                  <el-input v-model="head.name" placeholder="请选择商品名称">
                  </el-input>
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
                <el-table-column prop="id" label="货源商品id" width="150">
                </el-table-column>
                <el-table-column prop="imgUrl" label="商品图片" width="150">
                  <template slot-scope="{ row }">
                    <el-image
                      style="width: 50px; height: 50px"
                      :src="row.imgUrl"
                      fit="fill"
                    ></el-image>
                  </template>
                </el-table-column>
                <el-table-column prop="name" label="商品名称"></el-table-column>
                <el-table-column
                  prop="price"
                  label="商品价格"
                ></el-table-column>
                <el-table-column prop="state" label="状态">
                  <template slot-scope="{ row }">
                    <div
                      v-if="row.state === '0'"
                      style="color: rgb(42, 46, 45);"
                    >
                      未提交
                    </div>
                    <div
                      v-if="row.state === '1'"
                      style="color: rgb(0, 98, 255);"
                    >
                      待审核
                    </div>
                    <div
                      v-if="row.state === '2'"
                      style="color: rgb(0, 255, 170);"
                    >
                      审核通过
                    </div>
                    <div
                      v-if="row.state === '3'"
                      style="color: rgb(255, 0, 0);"
                    >
                      审核失败
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="申请结果备注"
                ></el-table-column>
                <el-table-column
                  prop="productId"
                  label="插队id"
                ></el-table-column>
                <!-- 表格项太多时 设置操作栏固定在最右侧 -->
                <el-table-column
                  v-if="roleType === '3'"
                  label="操作"
                  fixed="right"
                  width="150"
                >
                  <template slot-scope="{ row }">
                    <MyButtonGroup
                      :but-list="[
                        {
                          text: '插队申请',
                          onClick: () => apllyDo(row.id),
                          disabled: form.examine !== '2',
                          hidden: roleType !== '3',
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
          </el-tab-pane>
        </el-tabs>
      </el-row>
    </DescriptionsForm>
    <AddOrEdit ref="addOrEdit" :callback="getTableData" />
    <AddSoure ref="addSoure" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import MyButtonGroup from "@/components/MyButtonGroup";
import AddOrEdit from "./components/AddOrEdit";
import AddSoure from "./components/AddSoure";
import DescriptionsForm from "@/components/DescriptionsForm";
import DictParse from "@/components/DictParse";
// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "SourceDetails",
  components: {
    DropdownHead,
    MyButtonGroup,
    AddOrEdit,
    DictParse,
    DescriptionsForm,
    AddSoure,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      form: {
        sourceSystem: "",
        sourceWebsite: "",
        dockingAccount: "",
        dockingKey: "",
        connectivity: null,
        createTime: "",
        applyReason: "",
        examine: "",
        examineReason: "",
        balance: null,
      },
      head: {
        name: "",
        proName: "",
      },
      sourceOptions: [],
      loading: false,
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 100,
        total: 0,
      },
      activeName:
        this.$store.state.userInfo.account.roleType === "1" ? "first" : "third",
      selectList: "",
    };
  },
  computed: {
    roleType() {
      return this.$store.state.userInfo.account.roleType;
    },
  },
  created() {
    this.searchHead();
    this.getDel();
    this.getSourceList();
  },
  methods: {
    apllyDo(val) {
      this.$prompt("插队申请理由", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(({ value }) => {
        axios
          .get(
            `/api/source/apply?key=${val}&ly=${value}&sourceId=${this.$route.query.id}`
          )
          .then(() => {
            this.getTableData();
            this.$message({
              type: "success",
              message: "已提交申请!",
            });
          });
      });
    },
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
    getDel() {
      axios
        .get(`/api/source/detail?key=${this.$route.query.id}`)
        .then((value) => {
          const { data } = value;
          Object.keys(this.form).forEach((key) => {
            this.form[key] = data[key];
          });
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
      let url = "";
      if (this.activeName === "first") {
        url = "/api/source/getBeforeFl";
      }
      if (this.activeName === "second") {
        url = "/api/source/getAfterFl";
      }
      if (this.activeName === "third") {
        url = "/api/source/commodity";
      }
      axios
        .post(url, {
          pageNum: page || this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
          ...backupFormData,
          sourceId: this.$route.query.id,
        })
        .then((value) => {
          /**
           * 具体代码需要参考接口返回字段
           * 任何情况下接口必须都返回当前页和数据总条数
           * 不需要再次判断code是否等于200
           */
          const { data } = value;
          this.tableData = data.records || [];
          this.tableData.forEach((res) => {
            if (res.type === "1") {
              res.hasChildren = true;
            }
          });
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

    handleClick() {
      (this.head = {
        name: "",
        proName: "",
      }),
        this.getTableData(1);
    },

    load(tree, treeNode, resolve) {
      axios
        .get(
          `/api/source/getBeforeFl/prd?id=${tree.id}&sourceId=${this.$route.query.id}`
        )
        .then(({ data }) => {
          data.records.forEach((res) => {
            res.hasChildren = false;
            res.type === "2";
          });
          resolve(data.records);
        });
    },

    handleSelectionChange(val) {
      this.selectList = val;
    },
    addStart() {
      if (this.selectList.length < 1) {
        this.$message.error("请先选择一个分类或商品");
        return;
      }
      this.$refs.addSoure.addStart(this.selectList);
    },
    handleSizeChange(val) {
      this.pagination.pageSize = val;
      this.getTableData(1);
    },
  },
};
</script>
