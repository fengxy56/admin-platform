import Vue from 'vue'
import Router from 'vue-router'
import Index from './views/index'
import Login from './views/login'
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
Vue.use(ViewUI);
Vue.use(Router)
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index
    },{
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
})
