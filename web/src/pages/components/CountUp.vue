<template>
  <div>
    <div>
      普通用法：
      <CountUp style="font-size:24px;color:#2b7bfb" />
    </div>
    <div>
      反着跑
      <CountUp
        :end-val="0"
        :options="{ startVal: 2022 }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>
    <div>
      设置小数点，不设置时会始终显示整数，不会管真实数字是多少
      <CountUp
        :end-val="2022"
        :options="{ decimalPlaces: 2 }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>
    <div>
      设置动画时间，默认2s
      <CountUp
        :end-val="2022"
        :options="{ duration: 10 }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>
    <div>
      不显示千分位分组
      <CountUp
        :end-val="2022"
        :options="{ useGrouping: false }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>
    <div>
      显示前缀
      <CountUp
        :end-val="2022"
        :options="{ prefix: '现在是：' }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>
    <div>
      显示后缀
      <CountUp
        :end-val="2022"
        :options="{ suffix: '年' }"
        style="font-size:24px;color:#2b7bfb"
      />
    </div>

    <el-divider></el-divider>

    更多用法请参考：<el-link
      href="http://inorganik.github.io/countUp.js/"
      type="primary"
      target="_blank"
    >
      countup.js
    </el-link>
  </div>
</template>

<script>
import CountUp from "@/components/CountUp";
export default {
  name: "CountUpDemo",
  components: { CountUp },
};
</script>

<!-- <style scoped></style> -->
