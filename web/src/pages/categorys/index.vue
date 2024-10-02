<template>
  <div>
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

    <el-table
      :key="key"
      v-loading="loading"
      :data="tableData"
      style="width: 100%"
      :height="`calc(100vh - ${m_boxHeightAll}px - 240px)`"
      row-key="id"
      border
      lazy
      :load="load"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="分类名称"> </el-table-column>
      <el-table-column prop="image" label="分类图片">
        <template slot-scope="{ row }">
          <el-image
            style="width: 50px; height: 50px"
            :src="row.image"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序"> </el-table-column>
      <el-table-column prop="num" label="分类直属商品数量"> </el-table-column>
      <el-table-column prop="createTime" label="创建时间"> </el-table-column>
      <!-- 表格项太多时 设置操作栏固定在最右侧 -->
      <el-table-column label="操作" fixed="right" width="150">
        <template slot-scope="{ row }">
          <MyButtonGroup
            :but-list="[
              {
                text: '商品详情',
                onClick: () => $router.push(`/product?id=${row.id}`),
              },
              {
                text: '修改',
                onClick: () => $refs['addOrEdit'].editStart(row),
              },
              {
                text: '删除',
                onClick: () => del(row),
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
    <AddOrEdit ref="addOrEdit" :callback="getTableData" />
  </div>
</template>

<script>
import MyButtonGroup from "@/components/MyButtonGroup";
import headBoxHeightMixin from "@/mixin/head-box-height";
import AddOrEdit from "./components/AddOrEdit";
import axios from "@/utils/axios";
export default {
  components: {
    MyButtonGroup,
    AddOrEdit,
  },
  mixins: [headBoxHeightMixin],
  data() {
    return {
      key: 1,
      tableData: [],
      loading: false,
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
    del(row) {
      axios.post(`/api/category/del?key=${row.id}`).then(() => {
        this.$message.success("删除成功");
        this.getTableData(1);
      });
    },
    load(tree, treeNode, resolve) {
      axios
        .post("/api/category/list", {
          parentId: tree.id,
        })
        .then(({ data }) => {
          data.records.forEach((res) => {
            res.hasChildren = true;
          });
          resolve(data.records);
        });
    },
    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      axios
        .post("/api/category/list", {
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
          this.tableData.forEach((res) => {
            res.hasChildren = true;
          });
          this.pagination.pageNum = data.current;
          this.pagination.total = data.total;
          this.loading = false;
          this.key = this.key + 1;
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
