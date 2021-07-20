<style scoped>
.layout {
  background: #f5f7f9;
  position: relative;
  overflow: hidden;
}
.layout-logo {
  width: 100px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
}
.layout-nav {
  width: 420px;
  margin: 0 auto;
  margin-right: 20px;
}
.layout-logo {
  width: 130px;
  height: 40px;
  background: #ffffff;
  border-radius: 3px;
  top: 6px;
  left: -21px;
  margin-left: 50px;
}
</style>
<template>
  <div class="layout">
    <Layout :style="this.heightStyle">
      <Header :style="{ height: '55px', padding: '0' }">
        <Menu
          mode="horizontal"
          :theme="this.menuTheme"
          :style="{height:'55px',lineHeight:'55px'}"
          @on-select="clickMenu"
        >
          <div class="layout-logo">
            <img
              src="../assets/hk_logo.png"
              alt="logo"
              style="display:block;width:130px;height:40px;"
            />
          </div>
          <div class="layout-nav">
            <Submenu name="other" style="float:right;">
              <template slot="title">
                <Avatar
                  src="https://i.loli.net/2017/08/21/599a521472424.jpg"
                  :style="{marginRight:'5px'}"
                />
                {{this.loginUser.chsName || "个人信息"}}
              </template>
              <MenuItem name="loginout" style="text-align:center">退出登录</MenuItem>
            </Submenu>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger :style="siderClass">
          <Menu
            :active-name="this.viewComponent"
            :theme="this.menuTheme"
            width="auto"
            @on-select="swichMain"
            :open-names="this.openNameArr"
            ref="side_menu"
			accordion
          >
            <MenuComponet v-for="item in data" :key="item.index" :menuData="item" />
          </Menu>
          <i-Switch
            :style="{position: 'absolute',bottom: '20px',left: '70px'}"
            true-color="#515a6e"
            false-color="#2d8cf0"
            @on-change="swichTheme"
            v-model="swichModel"
          >
            <span slot="open">暗</span>
            <span slot="close">亮</span>
          </i-Switch>
        </Sider>
        <Layout :style="{padding: '10px'}">
          <Content
            ref="contents"
            :style="{padding: '10px', minHeight: '280px', background: '#fff'}"
          >
           <div v-if="authMenuCount>0">
              <div v-for="item in linkData" :key="item.index">
                <component
                  :is="item.menuUrl"
                  v-if="viewComponent===item.menuCode"
                  :menuTheme="menuTheme"
                  :windowHeight="windowHeight"
                  :components="components"
                  v-on:func="callback"
                ></component>
              </div>
            </div>
            <div v-else style="text-align:center;vertical-align:middle;">    
              <img
              src="../assets/auth_error.png"
              alt="logo"
              style="width:30%;"
            /></div>
          </Content>
        </Layout>
      </Layout>
    </Layout>
    <Spin fix v-show="linkLoading||menuLoading" style="z-index:999"></Spin>
  </div>
</template>
<script>
import storage from "../common/storage";
import MenuComponet from "../components/menuComponent/index";
import Template from "./template/index";
import MenuIndex from "./sys/menu/index";
import SysAuthMenuList from "./sys/authMenu/userRoleList";
import Dictionary from "./sys/dictionary/list";

import SysOrg from "./sys/org/index";

import RoleList from "./role/list";

import InterfaceList from './interface/list';
import SmsSysInfoList from './sms/list';
import HxPersonInfoList from './hx/person/list';
import HxAppInfoList from './hx/app/appList';
import HxAppAuthList from './hx/app/appAuthList';
import HxAppMenuList from './hx/app/appMenuList';


import LogHxSiteList from './log/logHxSiteList';
import HxWorkReportList from './hx/workReport/workReportList';
import WorkoList from './hx/worko/workoList';
import WorkoUsersList from './hx/worko/workoUsersList';

import HxHomePageList from './hx/homepage/homepageList';
import HxSignTrackList from './hx/sign/signTrackList';
import HxSignTrackRecordList from './hx/sign/signTrackRecordList';


import HxUndoList from './hx/undo/undoList';
import HxPushInfoList from './hx/mapproval/hxPushInfoList';

import HxAttendanceSetList from './hx/attendance/hxAttendanceSetList';

import QCodeList from './qcode/list';


export default {
  name: "Index",
  components: {
    Template,
    MenuComponet,
    MenuIndex,
    // PowerList,
    // UserList,
    SysAuthMenuList,
    SysOrg,
    
    RoleList,
    WorkoList,
    WorkoUsersList,
    Dictionary,
    InterfaceList,
    SmsSysInfoList,
    HxPersonInfoList,
    HxAppInfoList,
    HxAppAuthList,
    HxAppMenuList,
    LogHxSiteList,
    HxWorkReportList,
    HxHomePageList,
    HxSignTrackList,
    HxSignTrackRecordList,
    HxUndoList,
    HxPushInfoList,
    HxAttendanceSetList,
    QCodeList,
  },
  data() {
    return {
      components: [
        { value: "Template", name: "模板组件" },
        { value: "MenuIndex", name: "菜单管理组件" },
        { value: "SysOrg", name: "组织组件" },
        
        { value: "SysAuthMenuList", name: "权限管理组件" },
        // { value: "UserList", name: "用户列表组件" },
        { value: "RoleList", name: "角色管理组件" },

        { value: "Dictionary", name: "字典管理组件" },
        { value: "InterfaceList", name: "接口管理组件" },
        { value: 'SmsSysInfoList', name: "短信管理组件"},
        { value: 'HxPersonInfoList', name: "鸿信人员组件"},
        { value: 'HxAppInfoList', name: "轻应用列表"},
        { value: 'HxAppAuthList', name: "轻应用授权"},
        { value: 'HxAppMenuList', name: "轻应用菜单"},

        { value: 'LogHxSiteList', name: "智慧工地"},
        { value: 'HxWorkReportList', name: "工作更新"},
        { value: 'HxHomePageList', name:'首页轮播图组件'},
        { value: 'WorkoList', name:'同事圈-内容列表'},
        { value: 'WorkoUsersList', name:'同事圈-用户列表'},


        { value: 'HxSignTrackList', name:'签到轨迹信息'},
        { value: 'HxSignTrackRecordList', name:'签到轨迹记录信息'},
        
        { value: 'HxUndoList', name:'鸿信待办'},
        { value: 'HxPushInfoList', name:'移动审批'},

        { value: 'HxAttendanceSetList', name:'鸿信签到'},
        { value: 'QCodeList', name:'二维码'},

      ],
      siderClass: { zIndex: 9, backgroundColor: "#515a6e" },
      viewComponent: "",
      cname: "MenuIndex",
      menuTheme: "dark",
      swichModel: false,
      heightStyle: "",
      windowHeight: window.innerHeight,
      data: [],
      linkData: [],
      menuLoading: false,
      linkLoading: false,
      loginUser: {},
      openNameArr: [],
      activeMenuName: "",
      authMenuCount :1,
    };
  },
  methods: {
    //获取权限菜单数据
    async getUserInfo() {
      const userResult = await this.$api.getUserInfo();
      if (userResult.code === 1) {
        this.loginUser = userResult.data;
        this.getUserLoginTreeMenu();
        this.getXMenuLink();
      }
    },
    //获取左侧权限菜单
    async getUserLoginTreeMenu() {
      this.menuLoading = true;
      const result = await this.$api.getUserLoginTreeMenu();
      if (result.code === 1) {
        this.data = [];
        result.data.forEach(item => {
          const menuObj = {};
          const children = [];
          children.push(item);
          menuObj.children = children;
          this.data.push(menuObj);
        });
        //默认展开第一个主菜单第一项
        const arr=[]

        this.authMenuCount = result.data.length;

        if (result.data.length > 0) {
          arr.push(result.data[0].menuCode);
          this.openNameArr = arr;
          if (result.data[0].children.length > 0) {
            this.viewComponent = result.data[0].children[0].menuCode;
          }
        }
        this.menuLoading = false;
      }
    },
    //获取菜单组件Content数据
    getXMenuLink: async function() {
      this.linkLoading = true;
      const result = await this.$api.getXMenuLink();
      if (result.code === 1) {
        let arr = this.components.map(item => {
          return item.value;
        });
        this.linkData = result.data.filter(item => {
          return arr.includes(item.menuUrl);
        });
        this.linkLoading = false;
      }
    },
    //切换菜单
    swichMain(name) {
      this.viewComponent = name;
    },
    //切换主题
    swichTheme(status) {
      if (status) {
        this.menuTheme = "light";
        this.siderClass.backgroundColor = "#ffffff";
      } else {
        this.menuTheme = "dark";
        this.siderClass.backgroundColor = "#515a6e";
      }
      storage.set("themes", status);
    },
    //菜单组件修改菜单后的回调
    async callback() {
      this.menuLoading = true;
      location.reload();
    },
    //退出登录
    async clickMenu(menuName) {
      if (menuName === "loginout") {
        console.log("退出登录");
        const result = await this.$api.loginOut();
        console.log(result);
        if (result.code === 1) {
            window.location.href = "/login";
        }
    
      }
    }


  },
  mounted() {
    this.getUserInfo();
    this.swichModel = storage.get("themes");
    this.swichTheme(storage.get("themes")); //根据用户选择更改主题
    //自适应窗体大小调整
    this.windowHeight = window.innerHeight < 600 ? 600 : window.innerHeight;
    this.heightStyle = { height: this.windowHeight - 2 + "px" };
    window.onresize = () => {
      if (window.innerHeight > 600) {
        this.heightStyle = { height: window.innerHeight - 2 + "px" };
        this.windowHeight = window.innerHeight;
      }
    };
  },
  watch: {
    openNameArr() {
      this.$nextTick(() => {
        this.$refs.side_menu.updateOpened();
        this.$refs.side_menu.updateActiveName();
      });
    }
  }
};
</script>
