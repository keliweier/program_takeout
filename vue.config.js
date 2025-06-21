const IS_PROD = ['production', 'development'].includes(process.env.NODE_ENV)
const { defineConfig } = require('@vue/cli-service')
const path = require('path')
const name = 'Vue Typescript Admin'

module.exports = defineConfig({
  transpileDependencies: true,
  'publicPath': process.env.NODE_ENV === 'production' ? './' : '/', // TODO: Remember to change this to fit your need
  'lintOnSave': process.env.NODE_ENV === 'development',
  'pwa': {
    'name': name
  },
  'pluginOptions': {
    'style-resources-loader': {
      'preProcessor': 'scss',
      'patterns': [
        // path.resolve(__dirname, 'src/styles/_variables.scss'),
        path.resolve(__dirname, 'src/styles/_mixins.scss')
      ]
    }
  },
  //开启代理
  devServer:{
    port:8888,
    proxy:{
      '/api':{
        target:'http://localhost:8080',
        pathRewrite:{
          '^/api':''
        }
      } 
    }
  },
  css: {
    // 是否使用css分离插件 ExtractTextPlugin
    extract: IS_PROD,
    // 开启 CSS source maps?
    sourceMap: false,
    // css预设器配置项
    loaderOptions: {
      css: {
        // 启用 CSS modules for all css / pre-processor files.
        modules: {
          auto: false, // 可以根据需求设置为 true 或 false
          localIdentName: '[name]__[local]--[hash:base64:5]'
        }
      },
      sass: {
        additionalData: `
          @import "@/styles/_mixins.scss";
        `
      }
    },
},
})
