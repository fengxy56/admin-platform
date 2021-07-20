<style scoped>
.search {
  width: 200px;
  float: right;
}
.commons {
  line-height: 1;
}
.userinput {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 5px;
}
.userinput > div {
  margin-left: 10px;
}
</style>
<template>
  <div class="commons">
    <div class="userinput">
      <div>
        用户账号：
        <Input v-model="accountName" placeholder="请输入用户账号" style="width: 2rem" />
      </div>
      <div>
        用户名称：
        <Input v-model="chsName" placeholder="请输入用户名称" style="width: 2rem" />
      </div>
      <div>
        角色id：
        <Input v-model="broleId" placeholder="请输入角色id" style="width: 2rem" />
      </div>
      <div>
        角色名称：
        <Input v-model="broleName" placeholder="角色名称" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
    </div>
    <Table
      stripe
      border
      :height="this.windowHeight - 200"
      :columns="columns"
      :data="data"
      ref="tables"
      size="small"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>
    <Page
      :current="this.currentPage"
      :total="totals"
      show-sizer
      :style="{padding:'15px'}"
      :page-size="sizes"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />
    <Modal v-model="modal" title="轻应用授权" width="400" footer-hide :mask-closable="false">
      <AppAuthComponent         
      v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal" :authType="this.authType" :authId="authId" />
    </Modal>
    <Modal v-model="modal1" title="应用菜单授权" width="400" footer-hide :mask-closable="false">
      <AppMenuAuthComponent         
      v-on:funclose="closeModal1"
        v-on:func="saveAndCloseModal1"
        v-if="modal1" :authType="this.authType" :authId="authId"
        :queryParams="this.queryParams" />
    </Modal>

    <Modal
        v-model="modal2"
        title="轻应用选择"
        @on-ok="bathSure"
        @on-cancel="cancel">

     <Form ref="formData"  :label-width="100" class="dict_form">
			        应用appid：
        		<Select style="width:150px;" filterable v-model="queryParams.appId" :label-in-value="true"  @on-change="changeHxCustApp">
							<Option  v-for="item in hxCustAppList" :value="item.appId" :key="item.id">{{ item.appName }}</Option>
						</Select>

		</Form>
    </Modal>
  </div>
</template>
<script>
import AppAuthComponent from "../../../components/hx/app/appAuthTreeList";
import AppMenuAuthComponent from "./appMenuAuthTreeList";
export default {
  name: "AppAuthTreeList",
  components: {
    AppAuthComponent,
    AppMenuAuthComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      modal1:false,
      modal2:false,
      loading: false,
      roleId: "",
      queryParams: {
          appId:"",
          appName: "",
			},
      hxCustAppList:[],
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "姓名",
          key: "chsName"
        },
        {
          title: "账号",
          key: "accountName"
        },
        {
          title: "角色名称",
          key: "bRoleName"
        },
        {
          title: "角色id",
          key: "bRoleId"
        },
        {
          title: "轻应用授权",
          width: 200,
          align: "center",
          fixed: "right",
          render: (h, params) => {
              return h("div", [
                h(
                  "Button",
                  {
                    props: {
                      type: "info",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px"
                    },
                    on: {
                      click: () => {
                        this.openUserComponent(params);
                      }
                    }
                  },
                  "用户授权"
                ),h(
                  "Button",
                  {
                    props: {
                      type: "success",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px"
                    },
                    on: {
                      click: () => {
                        this.openRoleComponent(params);
                      }
                    }
                  },
                  "角色授权"
                )
              ]);

          }

        },
        {
          title: "菜单授权",
          width: 200,
          align: "center",
          fixed: "right",
          render: (h, params) => {
              return h("div", [
                h(
                  "Button",
                  {
                    props: {
                      type: "info",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px"
                    },
                    on: {
                      click: () => {
                        this.openAppMenuUserComponent(params);
                      }
                    }
                  },
                  "用户授权"
                ),h(
                  "Button",
                  {
                    props: {
                      type: "success",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px"
                    },
                    on: {
                      click: () => {
                        this.openAppMenuRoleComponent(params);
                      }
                    }
                  },
                  "角色授权"
                )
              ]);

          }

        }


      ],
      accountName: "",
      broleId: "",
      broleName: "",
      chsName: "",
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getUvERoleUserList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        accountName: this.accountName,
        broleId: this.broleId,
        broleName: this.broleName,
        chsName: this.chsName
      };
      this.loading = true;
      const result = await this.$api.getUvERoleUserList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    getHxCustAppInfoList: async function(pages) {
        this.queryParams.pageNo = 1;
        this.queryParams.pageSize = 1000;

        this.loading = true;
        const result = await this.$api.getHxCustAppInfoList(this.queryParams);
        this.loading = false;
        if (result.code === 1) {
          this.hxCustAppList = result.data.records;
  
        }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getUvERoleUserList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getUvERoleUserList(this.currentPage);
    },
    query() {
      this.currentPage=1;
      this.getUvERoleUserList(this.currentPage);
    },
    openUserComponent(e) {

      this.authId = e.row.accountName;
      this.authType = 1;
      this.modal = true;
    },
    openRoleComponent(e) {

      this.authId = e.row.bRoleId;
      this.authType = 2;
      this.modal = true;
    },
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getUvERoleUserList(this.currentPage);
    },
    openAppMenuUserComponent(e) {
      this.queryParams.appId = "";
      this.queryParams.appName = "";
      //获取轻应用列表
      this.getHxCustAppInfoList();
      this.authId = e.row.accountName;
      this.authType = 1;
      this.modal2 = true;
      
    },
    openAppMenuRoleComponent(e) {
      this.queryParams.appId = "";
      this.queryParams.appName = "";
      //获取轻应用列表
      this.getHxCustAppInfoList();
      this.authId = e.row.bRoleId;
      this.authType = 2;
      this.modal2 = true;
    },
    //关闭授权组件
    closeModal1() {
      this.modal1 = false;
    },
    //关闭授权组件
    saveAndCloseModal1() {
      this.modal1 = false;
      this.getUvERoleUserList(this.currentPage);
    },        
    bathSure : async function(pages) {
      if(this.queryParams.appId == null || this.queryParams.appId == ''){
          this.$Message.warning('appId不能为空');
          return;
      }
      this.modal1 = true;
    },
    cancel () {
      //this.$Message.info('取消');
    },
    changeHxCustApp(changeHxCustAppData){
				this.queryParams.appName = changeHxCustAppData.label;
    }


  },
  mounted() {
    this.getUvERoleUserList(this.currentPage1);
  }
};
</script>
