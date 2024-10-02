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
        tooltip: {
          trigger: "axis",
        },
        legend: {
          right: "30px",
        },
        grid: {
          left: "30px",
          right: "5%",
          bottom: "20px",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
          },
        },
        series: [
          {
            type: "line",
            data: [1, 2, 3, 4, 5, 6, 3, 78, 21],
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
      axios.get("/api/current/order-7").then((value) => {
        const { data } = value;
        this.option.xAxis.data = data.data;
        this.option.series[0].data = data.series;
      });
    },
  },
};
</script>
