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
        权限类型：
        <Select v-model="authType" clearable style="width: 1rem">
          <Option :value="1">用户</Option>
          <Option :value="2">角色</Option>
        </Select>
      </div>
      <div>
        权限id：
        <Input v-model="authId" placeholder="请输入权限id" style="width: 2rem" />
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
        v-if="modal" :menuTheme="this.menuTheme" :authId="authId" :authType="authType" :businessSystemId="businessSystemId" />
    </Modal>


  </div>
</template>
<script>
// import AddAuthMemuComponent from "./addAuthMemu";

export default {
  name: "UserRoleMenuListComponent",
  components: {
    // AddAuthMemuComponent,

  },
  props: {
    menuTheme: String,
    windowHeight: Number,
    menuId: String
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
      authType:"",
      businessSystemId:"",
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
          title: "系统名称",
          key: "businessSystemName"
        },
        {
          title: "权限类型",
          key: "authType",
          render: (h, params) => {
            if (params.row.authType === '1') {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "用户"
              );
            } else if (params.row.authType === '2') {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "角色"
              );
            }
          }

        },
        {
          title: "权限id",
          key: "authId"
        },
        {
          title: "修改人",
          key: "updateAccount"
        }

      ],

    };
  },
  methods: {
    getList: async function(pages) {
      const data = {
        menuId: this.menuId,
        authId: this.authId,
        authType: this.authType
      };
      this.loading = true;

      const result = await this.$api.getSysAuthMenuList(data);
      this.loading = false;

      console.log(result);

      if (result.code === 1) {
        this.data = result.data;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getList(this.currentPage);
    },
    query() {
      this.currentPage=1;
      this.getList(this.currentPage);
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
      this.getList(this.currentPage);
    },
    
    //关闭授权组件
    closeModal1() {
      this.modal1 = false;
    },
    //关闭授权组件
    saveAndCloseModal1() {
      this.modal1 = false;
      this.getList(this.currentPage);
    }

  },
  mounted() {
    //businessSystemId;
    console.log("this.menuId:"+this.menuId);

    this.businessSystemId =  sessionStorage.getItem("businessSystemId");
    console.log("businessSystemId:"+this.businessSystemId );

    this.getList(this.currentPage);
  }

  
};
</script>
