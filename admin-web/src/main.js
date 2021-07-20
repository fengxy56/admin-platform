import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './init/js/rem'
import './init/css/reset.css'
import Api from './api/index.js'
import tool from './common/tool'
import dictCode from './common/dictCode'
import VueQuillEditor from "vue-quill-editor"; 
import 'quill/dist/quill.snow.css';
import moment from 'moment'
import 'babel-polyfill'
import VueAMap from "vue-amap";
Vue.use(VueAMap);

Vue.config.productionTip = false
Vue.prototype.$api = Api
Vue.prototype.$moment = moment;
Vue.prototype.$tool = tool;
Vue.prototype.$dictCode = dictCode;  
Vue.use(VueQuillEditor)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

VueAMap.initAMapApiLoader({
  key: "34933c35ef4cc86a937f428eb3933657",
  plugin: [
    "AMap.Autocomplete", //输入提示插件
    "AMap.PlaceSearch", //POI搜索插件
    "AMap.Scale", //右下角缩略图插件 比例尺
    "AMap.OverView", //地图鹰眼插件
    "AMap.ToolBar", //地图工具条
    "AMap.MapType", //类别切换控件，实现默认图层与卫星图、实施交通图层之间切换的控制
    "AMap.PolyEditor", //编辑 折线多，边形
    "AMap.CircleEditor", //圆形编辑器插件
    "AMap.Geolocation" //定位控件，用来获取和展示用户主机所在的经纬度位置
  ],
  uiVersion: "1.0"
});
