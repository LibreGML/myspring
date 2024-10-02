<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="账号" prop="account">
          <el-input v-model="head.account" placeholder="请输入账号" />
        </el-form-item>
      </el-form>
    </DropdownHead>

    <!--这部分按钮必须是文字类型按钮加且必须加图标-->
    <MyButtonGroup
      :max-number="-1"
      :space-size="20"
      class="auto-box"
      style="padding-bottom:16px"
      :but-list="[]"
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
        <el-table-column prop="account" label="账号"></el-table-column>
        <el-table-column prop="roleType" label="角色类型" min-width="110px">
          <template slot-scope="{ row }">
            {{
              row.roleType === "1"
                ? "总站长"
                : row.roleType === "2"
                ? "商家"
                : row.roleType === "3"
                ? "供应商"
                : "未知"
            }}
          </template>
        </el-table-column>
        <el-table-column prop="headPic" label="头像" min-width="190px">
          <template slot-scope="{ row }">
            <el-button
              type="text"
              @click="
                () => {
                  $filePreview({
                    urlList: [row.headPic],
                  });
                }
              "
            >
              点击预览
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          min-width="165px"
        ></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '修改',
                  onClick: () => $refs.addOrEdit.editStart(row),
                },
                {
                  text: '详情',
                  onClick: () => $router.push(`/account/details?id=${row.id}`),
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
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import AddOrEdit from "./components/AddOrEdit";
import MyButtonGroup from "@/components/MyButtonGroup";

// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "Account",
  components: {
    DropdownHead,
    AddOrEdit,
    MyButtonGroup,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        account: "",
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
        .post("/api/account/list", {
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
  },
};
</script>
