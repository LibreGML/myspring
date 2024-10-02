<template>
  <VueECharts :option="option" autoresize style="width:100%;height:250px" />
</template>

<script>
import VueECharts from "vue-echarts";
import axios from "@/utils/axios";
export default {
  components: { VueECharts },
  data() {
    return {
      option: {
        legend: {
          right: "30px",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          left: "30px",
          right: "20px",
          bottom: "20px",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: [],
          },
        ],
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
          },
        },
        series: [
          {
            type: "bar",
            data: [],
            color: "#0062FF",
          },
        ],
      },
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      axios.get("/api/current/bus/transaction-5").then((value) => {
        const { data } = value;
        this.option.xAxis[0].data = data.data;
        this.option.series[0].data = data.series;
      });
    },
  },
};
</script>
