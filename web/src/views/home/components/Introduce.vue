<template>
  <div class="introduce">
    <div class="card_body" style="background-color: #438fd2;">
      <div class="card_icon">
        <i class="card_el_icon el-icon-bangzhu"></i>
      </div>
      <div class="card_text">
        <div class="label">订单总数</div>
        <div class="content">
          <CountUp :end-val="data.allOder" />
        </div>
      </div>
    </div>
    <div class="card_body" style="background-color: #d24399;">
      <div class="card_icon">
        <i class="card_el_icon el-icon-cloudy-and-sunny"></i>
      </div>
      <div class="card_text">
        <div class="label">今日订单</div>
        <div class="content">
          <CountUp :end-val="data.todayOder" />
        </div>
      </div>
    </div>
    <div class="card_body" style="background-color: #437cd2;">
      <div class="card_icon"><i class="card_el_icon el-icon-time"></i></div>
      <div class="card_text">
        <div class="label">总交易额</div>
        <div class="content">
          <CountUp
            :end-val="data.allTransaction"
            :options="{ decimalPlaces: 2 }"
          />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #43d2a7;"
    >
      <div class="card_icon"><i class="card_el_icon el-icon-aim"></i></div>
      <div class="card_text">
        <div class="label">系统总余额</div>
        <div class="content">
          <CountUp :end-val="data.allBalance" :options="{ decimalPlaces: 2 }" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #e481bd;"
    >
      <div class="card_icon"><i class="card_el_icon el-icon-discover"></i></div>
      <div class="card_text">
        <div class="label">供应商</div>
        <div class="content">
          <CountUp :end-val="data.supplier" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #4381d2;"
    >
      <div class="card_icon"><i class="card_el_icon el-icon-football"></i></div>
      <div class="card_text">
        <div class="label">商家</div>
        <div class="content">
          <CountUp :end-val="data.business" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #43d2a2;"
    >
      <div class="card_icon"><i class="card_el_icon el-icon-open"></i></div>
      <div class="card_text">
        <div class="label">今日新增供应商</div>
        <div class="content">
          <CountUp :end-val="data.addSupplier" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #43d25d;"
    >
      <div class="card_icon">
        <i class="card_el_icon el-icon-wind-power"></i>
      </div>
      <div class="card_text">
        <div class="label">今日新增商家</div>
        <div class="content">
          <CountUp :end-val="data.addBusiness" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #435dd2;"
    >
      <div class="card_icon">
        <i class="card_el_icon el-icon-sunrise-1"></i>
      </div>
      <div class="card_text">
        <div class="label">累计会员用户数</div>
        <div class="content">
          <CountUp :end-val="data.numberNum" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #cb43d2;"
    >
      <div class="card_icon">
        <i class="card_el_icon el-icon-ice-drink"></i>
      </div>
      <div class="card_text">
        <div class="label">今日利润</div>
        <div class="content">
          <CountUp :end-val="data.profit" :options="{ decimalPlaces: 2 }" />
        </div>
      </div>
    </div>
    <div
      v-if="roleType === '1'"
      class="card_body"
      style="background-color: #4543d2;"
    >
      <div class="card_icon"><i class="card_el_icon el-icon-lollipop"></i></div>
      <div class="card_text">
        <div class="label">累计利润</div>
        <div class="content">
          <CountUp :end-val="data.allProfit" :options="{ decimalPlaces: 2 }" />
        </div>
      </div>
    </div>
    <div class="card_body" style="background-color: #d2439e;">
      <div class="card_icon">
        <i class="card_el_icon el-icon-watermelon"></i>
      </div>
      <div class="card_text">
        <div class="label">成功提现</div>
        <div class="content">
          <CountUp
            :end-val="data.allWithdrawal"
            :options="{ decimalPlaces: 2 }"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CountUp from "@/components/CountUp";
import axios from "@/utils/axios";
export default {
  components: { CountUp },
  data() {
    return {
      data: {
        allOder: 0,
        todayOder: 0,
        allTransaction: 0,
        allBalance: 0,
        supplier: 0,
        business: 0,
        addSupplier: 0,
        addBusiness: 0,
        numberNum: 0,
        profit: 0,
        allProfit: 0,
        allWithdrawal: 0,
      },
    };
  },
  computed: {
    roleType() {
      return this.$store.state.userInfo.account.roleType;
    },
  },
  created() {
    this.getAll();
  },
  methods: {
    getAll() {
      axios.get("/api/current/getAll").then((value) => {
        const { data } = value;
        this.data = data;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.introduce {
  display: flex;
  flex-wrap: wrap;
}

.card_body {
  width: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 20px;

  .card_icon {
    float: left;
    width: 50px;
    height: 50px;
    background: rgba(255, 255, 255, 0.5);
    border-radius: 50%;
  }

  .card_el_icon {
    font-size: 30px;
    line-height: 50px;
    text-align: center;
    width: 50px;
    color: #fff;
  }

  .card_text {
    padding-top: 5px;
    float: right;
    width: 100px;

    .label {
      font-size: 12px;
      color: #fff;
    }

    .content {
      line-height: 30px;
      font-size: 18px;
      color: #fff;
    }
  }
}
</style>
