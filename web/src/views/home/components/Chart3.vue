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
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          top: "bottom",
        },
        series: [
          {
            name: "销售数量top7",
            type: "pie",
            radius: [5, 80],
            center: ["50%", "50%"],
            data: [],
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
      axios.get("/api/current/category-7").then((value) => {
        const { data } = value;
        this.option.series[0].data = data;
      });
    },
  },
};
</script>
