<template>
  <el-dialog title="会员/额度包购买" :visible.sync="visible" width="800px">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="会员购买" name="member">
        <div style="display: flex; justify-content:center; gap: 10px;">
          <el-card
            v-for="(item, index) in memberList"
            :key="index"
            class="my-card"
            :class="{ selectId: selectId === item.id }"
            shadow="hover"
            style="width: 200px;text-align: center;cursor: pointer;"
            @click.native="change(item.id)"
          >
            <div style="color: #98b2b2;font-size: 15px;line-height: 30px;">
              {{ item.name }}
            </div>
            <div style="color: #98b2b2;font-size: 25px;line-height: 50px;">
              {{ item.price }}元/<span style="font-size: 15px;">
                {{ item.duration }}天
              </span>
            </div>
            <div style="color: #697878;font-size: 12px;line-height: 15px;">
              {{ item.info }}
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="额度包购买" name="quota">
        <div style="display: flex; justify-content:center; gap: 10px;">
          <el-card
            v-for="(item, index) in memberList"
            :key="index"
            class="my-card"
            :class="{ selectId: selectId === item.id }"
            shadow="hover"
            style="width: 200px;text-align: center;cursor: pointer;"
            @click.native="change(item.id)"
          >
            <div style="color: #98b2b2;font-size: 25px;line-height: 50px;">
              {{ item.limitb
              }}<span style="font-size: 15px;">额度包</span>/<span
                style="font-size: 15px;"
              >
                {{ item.price }}元
              </span>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="余额购买" name="yue">
        <div style="display: flex; justify-content:center; gap: 10px;">
          <el-input-number
            v-model="yue"
            :min="0.01"
            :precision="2"
            :step="0.01"
          ></el-input-number>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-row style="text-align: center;margin-top: 30px;">
      <el-radio v-model="type" label="alipay">支付宝</el-radio>
      <el-radio v-model="type" label="qqpay">QQ钱包</el-radio>
      <el-radio v-model="type" label="wxpay">微信支付</el-radio>
    </el-row>
    <el-row style="width: 100%;">
      <el-button
        style="float: right;margin-top: 20px;margin-right: 70px;"
        @click="buy"
      >
        购 买
      </el-button>
    </el-row>
    <span slot="footer">
      <el-button type="primary" @click="visible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
// 表单默认项
import axios from "@/utils/axios";
export default {
  data() {
    return {
      activeName: "member",
      selectId: "1",
      yue: 10,
      visible: false,
      memberList: [],
      type: "wxpay", //qqpay:QQ钱包, alipay:支付宝 ,wxpay:微信支付
    };
  },
  methods: {
    // 新增时启动函数
    // 父组件会通过 ref 调用
    // eslint-disable-next-line vue/no-unused-properties
    start(val) {
      this.activeName = val;
      this.visible = true;
      this.getTable();
    },
    getTable() {
      const url =
        this.activeName === "member"
          ? "/api/member/member/list"
          : "/api/member/pic/list";
      axios
        .post(url, {
          pageNum: 1,
          pageSize: 3,
        })
        .then((value) => {
          const { data } = value;
          this.memberList = data.records || [];
        });
    },
    buy() {
      if (this.activeName === "yue") {
        axios
          .post(`/api/business/buy/yue?Price=${this.yue}&type=${this.type}`)
          .then((data) => {
            window.open(data.data, "_self");
          });
      } else {
        if (this.selectId) {
          axios
            .post(`/api/business/buy?id=${this.selectId}&type=${this.type}`)
            .then((data) => {
              window.open(data.data, "_self");
            });
        }
      }
    },
    change(val) {
      this.selectId = val;
    },
    handleClick() {
      this.getTable();
    },
  },
};
</script>

<style scoped>
.my-card:hover {
  background: rgba(44, 163, 170, 0.141);
}

.selectId {
  background: rgba(44, 163, 170, 0.241);
}
</style>
