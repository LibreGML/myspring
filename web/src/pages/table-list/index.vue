<template>
  <div class="page">
    <DropdownHead @onSubmit="searchHead" @onReset="resetHead">
      <el-form ref="headForm" :model="head" inline>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="head.name" placeholder="请输入姓名" />
        </el-form-item>
        <PhoneFormItem v-model="head.phone" prop="phone" />
        <IdentityFormItem v-model="head.identity" prop="identity" />
        <EmailFormItem v-model="head.email" prop="email" />
        <el-form-item label="字典select" prop="dictValue">
          <DictSelect v-model="head.dictValue" dict-type="axios-type" />
        </el-form-item>
        <el-form-item label="查询条件" prop="value1">
          <el-input v-model="head.value1" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="查询条件" prop="value2">
          <el-input v-model="head.value2" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="查询条件" prop="value5">
          <el-input v-model="head.value5" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="截至日期">
          <el-date-picker
            v-model="head.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="查询条件" prop="value3">
          <el-input v-model="head.value3" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="查询条件" prop="value4">
          <el-input v-model="head.value4" placeholder="请输入" />
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
        },
        {
          text: '批量操作',
          icon: 'el-icon-finished',
          authButKey: '',
          disabled: true,
        },
        {
          text: '导入',
          icon: 'el-icon-upload2',
          authButKey: '',
        },
        {
          text: '导出',
          icon: 'el-icon-download',
          authButKey: '',
        },
        {
          text: '上传',
          icon: 'el-icon-upload2',
          authButKey: '',
        },
        {
          text: '下载',
          icon: 'el-icon-download',
          authButKey: '',
        },
        {
          text: '批量删除',
          icon: 'el-icon-delete',
          color: '#f56c6c',
          authButKey: '555',
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
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column
          prop="phone"
          label="手机号"
          min-width="110px"
        ></el-table-column>
        <el-table-column
          prop="identity"
          label="身份证"
          min-width="165px"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <el-table-column prop="email" label="图片" min-width="190px">
          <template slot-scope="{}">
            <el-button
              type="text"
              @click="
                () => {
                  $filePreview({
                    urlList: [
                      'https://caihai123.com/Dribbble/lists/preview_teaser.png',
                      'https://caihai123.com/Dribbble/lists/news_teaser.png',
                    ],
                  });
                }
              "
            >
              点击预览
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          min-width="190px"
        ></el-table-column>
        <!-- 表格项太多时 设置操作栏固定在最右侧 -->
        <el-table-column
          v-if="$checkPermission(['release', '555'])"
          label="操作"
          fixed="right"
          width="150"
        >
          <template slot-scope="{ row }">
            <MyButtonGroup
              :but-list="[
                {
                  text: '编辑',
                  onClick: () => $refs.addOrEdit.addStart(row),
                  color: '#f56c6c',
                  disabled: false,
                  authButKey: 'release', //当前页面拥有此按钮标识，所以按钮会显示
                },
                {
                  text: '详情',
                  onClick: () => $router.push('/template/details'),
                  disabled: false,
                  authButKey: '666', //当前页面没有此按钮标识，所以按钮不显示
                },
                {
                  text: '导出',
                  onClick: () => $refs.addOrEdit.addStart(row),
                  disabled: false, //没有配置权限标识，按钮会显示
                },
                {
                  text: '下载',
                  onClick: () => $refs.addOrEdit.addStart(row),
                  disabled: false,
                },
                {
                  text: '删除',
                  onClick: () => delRow(row.id),
                  disabled: true,
                },
                {
                  text: '批量删除',
                  onClick: () => delRow(row.id),
                  disabled: false,
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

    <AddOrEdit ref="addOrEdit" />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import DropdownHead from "@/components/DropdownHead";
import headBoxHeightMixin from "@/mixin/head-box-height";
import DictSelect from "@/components/select/DictSelect";
import AddOrEdit from "./components/AddOrEdit";
import EmailFormItem from "@/components/form/email-form-item";
import IdentityFormItem from "@/components/form/identity-form-item";
import PhoneFormItem from "@/components/form/phone-form-item";
import MyButtonGroup from "@/components/MyButtonGroup";

// 备份的表单数据，只在点击查询或重置时更新
let backupFormData = null;

export default {
  // 在一级页面中name为必填项，且必须和router中的name相对应，否则不会有页面缓存
  name: "TableList",
  components: {
    DropdownHead,
    DictSelect,
    AddOrEdit,
    EmailFormItem,
    IdentityFormItem,
    PhoneFormItem,
    MyButtonGroup,
  },
  // 此混入会占用data中的‘m_boxHeightAll’属性和methods中的‘m_boxHandle’函数，不要再次定义
  mixins: [headBoxHeightMixin],
  data() {
    return {
      head: {
        name: "",
        phone: "15519060282",
        identity: "",
        email: "",
        dictValue: "",
        value1: "",
        value2: "",
        value3: "",
        value4: "",
        value5: "",
        date: [],
      },
      loading: false,
      tableData: [
        {
          id: "1",
          name: "王小虎",
          phone: "15519060282",
          identity: "520122199405083016",
          email: "ch15519060282@163.com",
        },
      ],
      pagination: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  created() {
    // this.searchHead()
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
        .post("", {
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
          this.tableData = data.list || [];
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

    // 单个删除，一定要二次确认
    delRow(id) {
      this.$confirm("确定要删除该条数据吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        /**
         * 打印语句只能用于开发调试
         * 它会导致生产环境打包失败
         */
        id;
      });
    },
  },
};
</script>
