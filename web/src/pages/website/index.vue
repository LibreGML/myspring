<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="首页" name="home">
        <el-tabs v-model="activeName2" @tab-click="handleClick">
          <el-tab-pane label="主体1" name="mianBody1">
            <el-button class="addPrimary" type="primary" @click="addData">
              新增
            </el-button>
            <el-table :data="tableData" style="width: 100%" :height="350">
              <el-table-column
                prop="title1"
                label="标题1"
                width="180"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.title1"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="title2"
                label="标题2"
                width="180"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.title2"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="content1"
                label="内容1"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.content1"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="content2"
                label="内容2"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.content2"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="picture"
                label="图片"
                width="200"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <MyUpload
                    v-model="row.picture"
                    class="teup"
                    :limit="1"
                    :min="true"
                    type="img"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="主体2" name="mianBody2">
            <el-button class="addPrimary" type="primary" @click="addData">
              新增
            </el-button>
            <el-table :data="tableData" style="width: 100%" :height="350">
              <el-table-column
                prop="title1"
                label="标题1"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.title1"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="picture"
                label="图片"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <MyUpload
                    v-model="row.picture"
                    class="teup"
                    :limit="1"
                    :min="true"
                    type="img"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="主体3" name="mianBody3">
            <div style="display: flex;">
              <div style="width: 50%;">
                <el-button class="addPrimary" type="primary" @click="addData">
                  新增
                </el-button>
                <el-table :data="tableData" style="width: 100%" :height="350">
                  <el-table-column
                    prop="title1"
                    label="标题1"
                    :show-overflow-tooltip="false"
                  >
                    <template slot-scope="{ row }">
                      <el-input
                        v-model="row.title1"
                        type="textarea"
                        :rows="3"
                        resize="none"
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="picture"
                    label="图片"
                    :show-overflow-tooltip="false"
                  >
                    <template slot-scope="{ row }">
                      <MyUpload
                        v-model="row.picture"
                        class="teup"
                        :limit="1"
                        :min="true"
                        type="img"
                      />
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div style="width: 50%;border-left: 2px solid #000;">
                <el-form label-width="100px" class="demo-ruleForm">
                  <el-form-item label="标题1">
                    <el-input v-model="other.title1"></el-input>
                  </el-form-item>
                  <el-form-item label="内容1">
                    <el-input
                      v-model="other.content1"
                      type="textarea"
                      :rows="10"
                      resize="none"
                    ></el-input>
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="主体4" name="mianBody4">
            <div style="display: flex;">
              <div style="width: 50%;">
                <el-button class="addPrimary" type="primary" @click="addData">
                  新增
                </el-button>
                <el-table :data="tableData" style="width: 100%" :height="350">
                  <el-table-column
                    prop="title1"
                    label="标题1"
                    :show-overflow-tooltip="false"
                  >
                    <template slot-scope="{ row }">
                      <el-input
                        v-model="row.title1"
                        type="textarea"
                        :rows="3"
                        resize="none"
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="picture"
                    label="图片"
                    :show-overflow-tooltip="false"
                  >
                    <template slot-scope="{ row }">
                      <MyUpload
                        v-model="row.picture"
                        class="teup"
                        :limit="1"
                        :min="true"
                        type="img"
                      />
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div style="width: 50%;border-left: 2px solid #000;">
                <el-form label-width="100px" class="demo-ruleForm">
                  <el-form-item label="图片">
                    <MyUpload v-model="other.picture" :limit="1" type="img" />
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="主体5" name="mianBody5">
            <el-button class="addPrimary" type="primary" @click="addData">
              新增
            </el-button>
            <el-table :data="tableData" style="width: 100%" :height="350">
              <el-table-column
                prop="title1"
                label="标题1"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.title1"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="title2"
                label="标题2"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <el-input
                    v-model="row.title2"
                    type="textarea"
                    :rows="3"
                    resize="none"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="picture"
                label="图片"
                :show-overflow-tooltip="false"
              >
                <template slot-scope="{ row }">
                  <MyUpload
                    v-model="row.picture"
                    class="teup"
                    :limit="1"
                    :min="true"
                    type="img"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="合作伙伴" name="partners">
            <MyUpload v-model="partnersfileList" type="img" />
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane label="货源商品" name="source">
        <el-tabs v-model="activeName3" @tab-click="handleClick">
          <el-tab-pane label="轮播" name="carousel">
            <MyUpload v-model="carouselfileList" type="img" />
          </el-tab-pane>
          <el-tab-pane label="轮播下两张" name="twe">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>左图</span>
              </div>
              <MyUpload v-model="twel" :limit="1" type="img" />
            </el-card>
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>右图</span>
              </div>
              <MyUpload v-model="twer" :limit="1" type="img" />
            </el-card>
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>尊享权益</span>
              </div>
              <MyUpload v-model="equity" :limit="1" type="img" />
            </el-card>
          </el-tab-pane>
          <!-- <el-tab-pane label="第一个分类模块" name="class1">
            <el-form label-width="100px" class="demo-ruleForm">
              <el-form-item label="分类名称">
                <el-input v-model="class1.name"></el-input>
              </el-form-item>
              <el-form-item label="商品分类" prop="category">
                <el-cascader
                  v-model="class1.category"
                  style="width: 100%;"
                  :props="{
                    multiple: true,
                    checkStrictly: true,
                    value: 'id',
                    label: 'name',
                  }"
                  :options="options"
                  filterable
                ></el-cascader>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="第二个分类模块" name="class2">
            <el-form label-width="100px" class="demo-ruleForm">
              <el-form-item label="分类名称">
                <el-input v-model="class2.name"></el-input>
              </el-form-item>
              <el-form-item label="商品分类" prop="category">
                <el-cascader
                  v-model="class2.category"
                  style="width: 100%;"
                  :props="{
                    multiple: true,
                    checkStrictly: true,
                    value: 'id',
                    label: 'name',
                  }"
                  :options="options"
                  filterable
                ></el-cascader>
              </el-form-item>
            </el-form>
          </el-tab-pane> -->
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane label="加入我们" name="jion">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>背景图片</span>
          </div>
          <MyUpload v-model="jionUrl" :limit="1" type="img" />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="底部" name="bottom">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>文本1</span>
          </div>
          <el-form :inline="true" :model="formInline1" class="demo-form-inline">
            <el-form-item label="主标题">
              <el-input
                v-model="formInline1.title"
                placeholder="主标题"
              ></el-input>
              <el-input
                v-model="formInline1.titleUrl"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题1">
              <el-input
                v-model="formInline1.title1"
                placeholder="副标题1"
              ></el-input>
              <el-input
                v-model="formInline1.title1Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题2">
              <el-input
                v-model="formInline1.title2"
                placeholder="副标题2"
              ></el-input>
              <el-input
                v-model="formInline1.title2Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题3">
              <el-input
                v-model="formInline1.title3"
                placeholder="副标题3"
              ></el-input>
              <el-input
                v-model="formInline1.title3Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题4">
              <el-input
                v-model="formInline1.title4"
                placeholder="副标题4"
              ></el-input>
              <el-input
                v-model="formInline1.title4Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>文本2</span>
          </div>
          <el-form :inline="true" :model="formInline2" class="demo-form-inline">
            <el-form-item label="主标题">
              <el-input
                v-model="formInline2.title"
                placeholder="主标题"
              ></el-input>
              <el-input
                v-model="formInline2.titleUrl"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题1">
              <el-input
                v-model="formInline2.title1"
                placeholder="副标题1"
              ></el-input>
              <el-input
                v-model="formInline2.title1Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题2">
              <el-input
                v-model="formInline2.title2"
                placeholder="副标题2"
              ></el-input>
              <el-input
                v-model="formInline2.title2Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题3">
              <el-input
                v-model="formInline2.title3"
                placeholder="副标题3"
              ></el-input>
              <el-input
                v-model="formInline2.title3Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题4">
              <el-input
                v-model="formInline2.title4"
                placeholder="副标题4"
              ></el-input>
              <el-input
                v-model="formInline2.title4Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>文本3</span>
          </div>
          <el-form :inline="true" :model="formInline3" class="demo-form-inline">
            <el-form-item label="主标题">
              <el-input
                v-model="formInline3.title"
                placeholder="主标题"
              ></el-input>
              <el-input
                v-model="formInline3.titleUrl"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题1">
              <el-input
                v-model="formInline3.title1"
                placeholder="副标题1"
              ></el-input>
              <el-input
                v-model="formInline3.title1Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题2">
              <el-input
                v-model="formInline3.title2"
                placeholder="副标题2"
              ></el-input>
              <el-input
                v-model="formInline3.title2Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题3">
              <el-input
                v-model="formInline3.title3"
                placeholder="副标题3"
              ></el-input>
              <el-input
                v-model="formInline3.title3Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="副标题4">
              <el-input
                v-model="formInline3.title4"
                placeholder="副标题4"
              ></el-input>
              <el-input
                v-model="formInline3.title4Url"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>最底文本</span>
          </div>
          <el-form :inline="true" :model="formInline4" class="demo-form-inline">
            <el-form-item label="左部文本">
              <el-input
                v-model="formInline4.left"
                style="width: 350px;"
                placeholder="左部文本"
              ></el-input>
              <el-input
                v-model="formInline4.leftUrl"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
            <el-form-item label="右部文本">
              <el-input
                v-model="formInline4.right"
                style="width: 350px;"
                placeholder="右部文本"
              ></el-input>
              <el-input
                v-model="formInline4.rightUrl"
                placeholder="跳转地址"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>联系二维码</span>
          </div>
          <MyUpload v-model="QRCode" :limit="1" type="img" />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="logo" name="mian-logo">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>logo</span>
          </div>
          <MyUpload v-model="logoUrl" :limit="1" type="img" />
        </el-card>
      </el-tab-pane>
    </el-tabs>
    <el-button class="buttonPrimary" type="primary" @click="saveData">
      保存
    </el-button>
  </div>
</template>

<script>
import MyUpload from "@/components/my-upload";
import axios from "@/utils/axios";
export default {
  components: {
    MyUpload,
  },
  data() {
    return {
      activeName: "home",
      activeName2: "mianBody1",
      activeName3: "carousel",
      partnersfileList: [],
      carouselfileList: [],
      twel: [],
      twer: [],
      equity: [],
      options: [],
      class1: {
        name: "",
        category: [],
      },
      class2: {
        name: "",
        category: [],
      },
      QRCode: [],
      formInline1: {
        title: "",
        title1: "",
        title2: "",
        title3: "",
        title4: "",
        titleUrl: "",
        title1Url: "",
        title2Url: "",
        title3Url: "",
        title4Url: "",
      },
      formInline2: {
        title: "",
        title1: "",
        title2: "",
        title3: "",
        title4: "",
        titleUrl: "",
        title1Url: "",
        title2Url: "",
        title3Url: "",
        title4Url: "",
      },
      formInline3: {
        title: "",
        title1: "",
        title2: "",
        title3: "",
        title4: "",
        titleUrl: "",
        title1Url: "",
        title2Url: "",
        title3Url: "",
        title4Url: "",
      },
      formInline4: {
        right: "",
        left: "",
        rightUrl: "",
        leftUrl: "",
      },
      jionUrl: [],
      logoUrl: [],
      tableData: [],
      other: {
        title1: "",
        content1: "",
        picture: [],
      },
    };
  },
  created() {
    this.getBase();
    this.getSourceList();
  },
  methods: {
    getSourceList() {
      axios.get("/api/category/all?name=").then((res) => {
        this.options = res.data;
      });
    },
    handleClick() {
      this.getBase();
    },
    getBase() {
      let fieldName = "";
      if (this.activeName === "home") {
        fieldName = this.activeName2;
        this.tableData = [];
        this.other = {
          title1: "",
          content1: "",
          picture: [],
        };
      }
      if (this.activeName === "source") {
        fieldName = this.activeName3;
      }
      if (this.activeName === "bottom") {
        fieldName = "bottom";
      }
      if (this.activeName === "jion") {
        fieldName = "jion";
      }
      if (this.activeName === "mian-logo") {
        fieldName = "mian-logo";
      }
      axios.get(`/api/website/get-base?key=${fieldName}`).then((val) => {
        if (val.data && val.data.fieldValue) {
          this.dealData(JSON.parse(val.data.fieldValue));
        }
      });
    },
    dealData(val) {
      if (this.activeName === "home") {
        if (this.activeName2 === "partners") {
          const fileList = [];
          for (const item of val) {
            fileList.push({ response: { url: item }, url: item });
          }
          this.partnersfileList = fileList;
        } else {
          if (val.other) {
            this.other = val.other;
          }
          if (val.tableData) {
            this.tableData = val.tableData;
          }
          if (val instanceof Array) {
            this.tableData = val;
          }
        }
      }
      if (this.activeName === "source") {
        if (this.activeName3 === "carousel") {
          const fileList = [];
          for (const item of val) {
            fileList.push({ response: { url: item.url }, url: item.url });
          }
          this.carouselfileList = fileList;
        }
        if (this.activeName3 === "twe") {
          if (val.twel) {
            this.twel = [{ response: { url: val.twel }, url: val.twel }];
          }
          if (val.twer) {
            this.twer = [{ response: { url: val.twer }, url: val.twer }];
          }
          if (val.equity) {
            this.equity = [{ response: { url: val.equity }, url: val.equity }];
          }
        }
        if (this.activeName3 === "class1") {
          this.class1 = val;
        }
        if (this.activeName3 === "class2") {
          this.class2 = val;
        }
      }
      if (this.activeName === "bottom") {
        this.QRCode = [
          { response: { url: val.QRCode[0] }, url: val.QRCode[0] },
        ];
        this.formInline1 = val.formInline1 || {};
        this.formInline2 = val.formInline2 || {};
        this.formInline3 = val.formInline3 || {};
        this.formInline4 = val.formInline4 || {};
      }
      if (this.activeName === "jion") {
        this.jionUrl = [{ response: { url: val[0] }, url: val[0] }];
      }
      if (this.activeName === "mian-logo") {
        this.logoUrl = [{ response: { url: val[0] }, url: val[0] }];
      }
    },
    saveData() {
      let fieldName = "";
      let fieldValue = "";
      if (this.activeName === "home") {
        if (this.activeName2 === "partners") {
          fieldName = "partners";
          const fileList = [];
          for (const item of this.partnersfileList) {
            fileList.push(item.response.url);
          }
          fieldValue = fileList;
        } else {
          fieldName = this.activeName2;
          if (
            this.activeName2 === "mianBody3" ||
            this.activeName2 === "mianBody4"
          ) {
            fieldValue = { tableData: this.tableData, other: this.other };
          } else {
            fieldValue = this.tableData;
          }
        }
      }
      if (this.activeName === "source") {
        if (this.activeName3 === "carousel") {
          fieldName = "carousel";
          const fileList = [];
          for (const item of this.carouselfileList) {
            fileList.push({ url: item.response.url, tip: "" });
          }
          fieldValue = fileList;
        }
        if (this.activeName3 === "twe") {
          fieldName = "twe";
          fieldValue = {
            twel:
              this.twel[0] && this.twel[0].response
                ? this.twel[0].response.url
                : "",
            twer:
              this.twer[0] && this.twer[0].response
                ? this.twer[0].response.url
                : "",
            equity:
              this.equity[0] && this.equity[0].response
                ? this.equity[0].response.url
                : "",
          };
        }
        if (this.activeName3 === "class1") {
          fieldName = "class1";
          fieldValue = this.class1;
        }
        if (this.activeName3 === "class2") {
          fieldName = "class2";
          fieldValue = this.class2;
        }
      }
      if (this.activeName === "bottom") {
        fieldName = "bottom";
        fieldValue = {
          QRCode:
            this.QRCode[0] && this.QRCode[0].response
              ? [this.QRCode[0].response.url]
              : [],
          formInline1: this.formInline1,
          formInline2: this.formInline2,
          formInline3: this.formInline3,
          formInline4: this.formInline4,
        };
      }
      if (this.activeName === "jion") {
        fieldName = "jion";
        fieldValue =
          this.jionUrl[0] && this.jionUrl[0].response
            ? [this.jionUrl[0].response.url]
            : [];
      }
      if (this.activeName === "mian-logo") {
        fieldName = "mian-logo";
        fieldValue =
          this.logoUrl[0] && this.logoUrl[0].response
            ? [this.logoUrl[0].response.url]
            : [];
      }
      axios
        .post("/api/website/base", {
          fieldName,
          fieldValue: JSON.stringify(fieldValue),
        })
        .then(() => {
          this.$message.success(`保存成功`);
        });
    },
    addData() {
      let data = {};
      if (this.activeName2 === "mianBody1") {
        data = {
          title1: "",
          title2: "",
          content1: "",
          content2: "",
          picture: [],
        };
      }
      if (this.activeName2 === "mianBody2") {
        data = {
          title1: "",
          picture: [],
        };
      }
      this.tableData.push(data);
    },
  },
};
</script>

<style scoped lang="scss">
.buttonPrimary {
  position: fixed !important;
  right: 50px;
  bottom: 50px;
}

.addPrimary {
  float: right;
  margin: 10px 30px;
}

.teup {
  .el-upload {
    width: 50px !important;
    height: 50px !important;
  }
}
</style>
