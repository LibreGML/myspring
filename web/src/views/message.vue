<template>
  <div>
    <div v-if="tableData.length > 0">
      <el-timeline>
        <div
          v-for="(item, index) in tableData"
          :key="index"
          @click="showDalog(item)"
        >
          <el-timeline-item :timestamp="item.createTime" placement="top">
            <el-badge
              v-if="item.isRead === '0'"
              value="未读"
              class="item"
              style="width: 80%;"
            >
              <el-card>
                <h4>{{ item.title }}</h4>
              </el-card>
            </el-badge>
            <el-card v-else style="width: 80%;">
              <h4>{{ item.title }}</h4>
            </el-card>
          </el-timeline-item>
        </div>
      </el-timeline>

      <div style="text-align: right;margin-top:20px;margin-right: 70px;">
        <el-pagination
          :current-page="pagination.pageNum"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          @current-change="getTableData"
        >
        </el-pagination>
      </div>
    </div>
    <el-card v-else>
      <h4>暂无消息</h4>
    </el-card>

    <el-dialog
      title="公告详情"
      :visible.sync="dialogVisible"
      width="60%"
      @close="colsedDalog"
    >
      <h3>{{ selectItem.title }}</h3>
      <div v-html="selectItem.content"></div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="colsedDalog">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "@/utils/axios";
export default {
  name: "Message",
  components: {},
  props: {},
  data() {
    return {
      dialogVisible: false,
      selectItem: {
        id: "",
        title: "",
        content: "",
      },
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 5,
        total: 0,
      },
    };
  },
  computed: {},
  created() {
    this.searchHead();
  },
  mounted() {},
  methods: {
    // 根据当前head表单数据查询第一页
    searchHead() {
      this.getTableData(1);
    },
    getTableData(page) {
      axios
        .post("/api/notice/list/my", {
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
        });
    },

    showDalog(e) {
      this.selectItem = e;
      this.dialogVisible = true;
      axios.get(`/api/notice/read?noticeId=${this.selectItem.id}`).then(() => {
        this.getunRead();
      });
    },

    colsedDalog() {
      this.dialogVisible = false;
      this.getTableData();
    },

    getunRead() {
      axios.get(`/api/notice/unread`).then((value) => {
        const { data } = value;
        this.$store.state.unRead = data;
      });
    },
  },
};
</script>
