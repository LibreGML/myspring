const px2rem = require('postcss-px2rem')
const postcssRem = px2rem({
	// 基准大小 baseSize，可以使12、14等需要和rem.js中相同
	remUnit: 16
})
module.exports = {
	devServer: {
		proxy: {
			'/api': {
				target: 'http://localhost:8085', // 设置你想要跨域的接口域名
				changeOrigin: true
			}
		}
	},
	publicPath: "./",
	lintOnSave: true,
	css: {
		loaderOptions: {
			postcss: {
				plugins: [
					postcssRem
				]
			}
		}
	}
}
