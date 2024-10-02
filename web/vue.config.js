const commit = require("./commit");

// 打包时间（启动时间）
const nowDate = new Date();
const buildDate = `${`${nowDate.getFullYear()}-${nowDate.getMonth() +
  1}-${nowDate.getDate()} ${nowDate.getHours()}:${nowDate.getMinutes()}:${nowDate.getSeconds()}`}`;

module.exports = {
  chainWebpack: (config) => {
    config.plugin("define").tap((args) => {
      args[0]["process.env"].commitHash = JSON.stringify(commit.commitHash);
      args[0]["process.env"].buildDate = JSON.stringify(buildDate);
      return args;
    });
  },
  filenameHashing: true, //是否在文件名中包含了 hash 以便更好的控制缓存
  productionSourceMap: false, //如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  devServer: {
    https: false,
    port: 1234,
    proxy: {
      "/api": {
        target: process.env.VUE_APP_BASE_URL,
        ws: true,
        changeOrigin: true,
        // pathRewrite: {
        //   "^/api": "",
        // },
      },
    },
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
  },
  publicPath: "",
  pages: {
    index: {
      title: "基础开发框架",
      entry: "src/main.js",
      template: "public/index.html",
      filename: "index.html",
    },
    h5: {
      title: "文件上传",
      entry: "src/h5/main.js",
      template: "public/h5.html",
      filename: "h5.html",
    },
  },
  css: {
    loaderOptions: {
      scss: {
        prependData: `@import "~@/variables/style.scss";`,
      },
    },
  },
};
