
const webpack = require('webpack');
module.exports = {
	publicPath: "",
	// webpack-dev-server 相关配置
	devServer: {
		port: 8002,
    	proxy: 'http://localhost:9002', 
	},
	lintOnSave: false,
	configureWebpack: {
		plugins: [
			new webpack.ProvidePlugin({
				'window.Quill': 'quill/dist/quill.js',
				'Quill': 'quill/dist/quill.js'
			})
		]
	},
}
