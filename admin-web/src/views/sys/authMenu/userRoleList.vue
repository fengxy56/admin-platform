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
        <Input v-model="queryParams.accountName" placeholder="请输入用户账号" style="width: 2rem" />
      </div>
      <div>
        用户名称：
        <Input v-model="queryParams.chsName" placeholder="请输入用户名称" style="width: 2rem" />
      </div>
      <div>
        角色id：
        <Input v-model="queryParams.broleId" placeholder="请输入角色id" style="width: 2rem" />
      </div>
      <div>
        角色名称：
        <Input v-model="queryParams.broleName" placeholder="角色名称" style="width: 2rem" />
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
      :page-size-opts="[10, 20, 50, 100]"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />
    <Modal v-model="modal" title="用户授权" width="400" footer-hide :mask-closable="false">
      <AddAuthMemuComponent         
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal" :menuTheme="this.menuTheme" :authId="authId" :authType="authType" />
    </Modal>

    <Modal v-model="modal1" title="组织授权" width="400" footer-hide :mask-closable="false">
      <HxOrgComponent         
        v-on:funclose="closeModal1"
        v-on:func="saveAndCloseModal1"
        v-if="modal1" :menuTheme="this.menuTheme" :authId="authId" :authType="authType" />
    </Modal>

  </div>
</template>
<script>
import AddAuthMemuComponent from "./addAuthMemu";
import HxOrgComponent from "./hxOrg";

export default {
  name: "UserRoleList",
  components: {
    AddAuthMemuComponent,
    HxOrgComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      modal1:false,
      loading: false,
      queryParams: {
        accountName: "",
        broleId: "",
        broleName: "",
        chsName: "",
      },
  
      authId:"",
      authType:1,
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: [],

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
          title: "操作",
          width: 260,
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
                  "授权"
                ),
                h(
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
                ),
                h(
                  "Button",
                  {
                    props: {
                      type: "warning",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px"
                    },
                    on: {
                      click: () => {
                        this.openOrgComponent(params);
                      }
                    }
                  },
                  "组织授权"
                )

              ]);

          }

        }
      ],

    };
  },
  methods: {
    getUvERoleUserList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        accountName: this.queryParams.accountName,
        broleId: this.queryParams.broleId,
        broleName: this.queryParams.broleName,
        chsName: this.queryParams.chsName
      };
      this.loading = true;
      console.log(data);
      const result = await this.$api.getUvERoleUserList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
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
    openOrgComponent(e) {
      console.log(111);
      this.authId = e.row.accountName;
      this.modal1 = true;
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
    
    //关闭授权组件
    closeModal1() {
      this.modal1 = false;
    },
    //关闭授权组件
    saveAndCloseModal1() {
      this.modal1 = false;
      this.getUvERoleUserList(this.currentPage);
    }

  },
  mounted() {
    this.getUvERoleUserList(this.currentPage1);
  }
};
</script>
