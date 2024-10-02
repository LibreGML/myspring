<script>
//用于路由刷新，将会重定向到指定路由
export default {
  beforeCreate() {
    const { params, query } = this.$route;
    const { path } = params;
    this.$store.commit("setRedirectName", "");
    this.$router.replace({ path: `/${path}`, query });
  },
  render(h) {
    return h(); // 避免警告信息
  },
};
</script>
