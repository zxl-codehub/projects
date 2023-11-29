const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, //关闭保存时严格检查

  devServer : {
    proxy : {
      '/api': {
        target: 'https://restapi.amap.com',
        pathRewrite: { '^/api': '' },
      },
    }
  }
})
