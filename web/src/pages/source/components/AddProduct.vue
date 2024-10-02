<template>
  <el-dialog :visible.sync="visible" width="800px" @closed="resetForm">
    <el-form ref="form" :model="form" label-width="120px">
      <el-row>
        <el-form-item label="商品关键词" prop="nameKey">
          <el-input v-model="form.nameKey" placeholder="请输入商品关键词">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getTableData(1)"
            >
              搜索
            </el-button>
          </el-input>
        </el-form-item>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="分类" prop="parentId">
            <el-cascader
              v-model="form.parentId"
              :props="{ checkStrictly: true, value: 'id', label: 'name' }"
              placeholder="请选择分类"
              :options="options"
              filterable
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col
          v-if="!((upType && form.pic) || (!upType && form.uppic.length > 0))"
          :span="12"
        >
          <el-form-item label="存储方式" prop="cunType">
            <el-select v-model="form.cunType" placeholder="请选择">
              <el-option
                v-for="item in cunTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="统一商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入统一商品名称" />
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="统一商品图片" prop="pic">
          <el-switch
            v-model="upType"
            active-text="已有地址"
            inactive-text="上传"
          >
          </el-switch>
          <el-input
            v-if="upType"
            v-model="form.pic"
            placeholder="请输入图片地址"
          />
          <MyUpload v-else v-model="form.uppic" :limit="1" type="img" />
        </el-form-item>
      </el-row>
      <el-row>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width: 100%"
          :height="400"
          row-key="id"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="35"> </el-table-column>
          <el-table-column prop="id" label="商品id" width="150">
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
      </el-row>
    </el-form>
    <span slot="footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
// 表单默认项
import MyUpload from "@/components/my-upload";
import axios from "@/utils/axios";
const defaultForm = {
  nameKey: "",
  name: "",
  image: [],
  parentId: [],
  cunType: "",
  pic: "",
  uppic: [],
};
export default {
  components: {
    MyUpload,
  },
  data() {
    return {
      upType: true,
      id: "", // 编辑时存在，为空时为新增
      visible: false,
      options: [],
      form: { ...defaultForm },
      selectSource: [],
      cunTypeList: [
        { value: "", label: "原有" },
        { value: "1", label: "本地" },
        { value: "2", label: "123云盘" },
      ],
      loading: false,
      tableData: [],
      pagination: {
        pageNum: 1,
        pageSize: 100,
        total: 0,
      },
      selectList: [],
    };
  },
  created() {},
  methods: {
    getSourceList() {
      axios.get("/api/category/all?name=").then((res) => {
        this.options = res.data;
      });
    },
    // 新增时启动函数
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    addStart(val) {
      this.selectSource = val;
      this.getSourceList();
      this.visible = true;
      // this.getTableData(1)
    },

    // 提交表单
    submit() {
      if (this.selectList.length < 1) {
        this.$message.error("请先至少选择一个商品");
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const parentId = this.form.parentId.length
            ? this.form.parentId[this.form.parentId.length - 1]
            : "";
          axios
            .post(`/api/source/getBeforeFl/intAll`, {
              ...this.form,
              selectList: this.selectList,
              flId: parentId,
              upType: this.upType,
            })
            .then(() => {
              this.$message.success("提交成功，后台处理中");
              this.visible = false;
            });
        }
      });
    },

    // 清空表单
    resetForm() {
      this.id = "";
      this.$refs["form"].resetFields();
      // // 一定要手动清空表单
      this.form = { ...defaultForm };
    },

    // 获取表格（list）数据
    getTableData(page) {
      this.loading = true;
      axios
        .post("/api/source/getAllProduct", {
          pageNum: page || this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
          name: this.form.nameKey,
          selectSource: this.selectSource,
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

    handleSizeChange(val) {
      this.pagination.pageSize = val;
      this.getTableData(1);
    },

    handleSelectionChange(val) {
      this.selectList = val;
    },
  },
};
</script>
