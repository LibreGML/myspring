<template>
  <div class="page">
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
        <el-table-column prop="type" label="类型">
          <template slot-scope="{ row }">
            <DictParse :value="row.type" dict-type="price-type" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="grade" label="等级"></el-table-column>
        <el-table-column prop="limitb" label="赠送额度"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="info" label="描述信息"></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '编辑',
                  onClick: () => $refs.addOrEdit.editStart(row),
                  color: '#f56c6c',
                },
                {
                  text: '删除',
                  onClick: () => delRow(row.id),
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
import headBoxHeightMixin from "@/mixin/head-box-height";
import AddOrEdit from "./components/AddOrEdit";
import MyButtonGroup from "@/components/MyButtonGroup";
import DictParse from "@/components/DictParse";
export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "",
  components: {
    AddOrEdit,
    MyButtonGroup,
    DictParse,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
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
    this.getTableData(1);
  },
  methods: {
    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      axios
        .post("/api/member/list", {
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

    // 单个删除，一定要二次确认
    delRow(id) {
      this.$confirm("确定要删除该条数据吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios.post(`/api/member/del?key=${id}`).then(() => {
          this.getTableData(1);
        });
      });
    },
  },
};
</script>
