<template>
  <div id="app">
    <heard />
    <router-view></router-view>
    <Bottom />
  </div>
</template>
<script>
import Heard from '@/components/Heard.vue'
import Bottom from '@/components/Bottom.vue'
export default {
  name: "app",
  components: { Heard, Bottom },
  mounted() {
    this.getlogo()
  },
  methods: {
    getlogo() {
      this.$axios.get(`/api/website/get-base?key=mian-logo`).then(({ data }) => {
        if (data && data.data.fieldValue) {
          localStorage.setItem("jbj_logo", JSON.parse(data.data.fieldValue)[0])
        }
      });
    },
  }
};
</script>