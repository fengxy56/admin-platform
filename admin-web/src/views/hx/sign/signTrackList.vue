<style scoped>
.search {
  width: 200px;
  float: right;
}
.commons {
  line-height: 1;
}
.powerInput {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 5px;
}
.powerInput > div {
  margin-left: 10px;
}

	.userinput>Form{
		width: 100%;
		padding: 0;
	}

</style>



<template>
  <div class="commons">


    <div class="powerInput">
      <div>
        签到名称
        <Input v-model="queryParams.positionName" placeholder="签到名称" style="width: 2rem" />
      </div>
      <div>
        签到地址
        <Input v-model="queryParams.address" placeholder="签到地址" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>      
      <div>
        <Button type="success" @click="add">新增</Button>
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

    <Modal v-model="modal" title="轻应用信息" width="1000" height="600" footer-hide :mask-closable="false">
      <HxAddSignConfigureComponent
        v-on:funclose="closeModal(1)"
        v-on:func="saveAndCloseModal(1)"
        v-if="modal"
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData"
      />
    </Modal>


    <Modal v-model="modal2" title="用户权限配置" width="1000" max-height='600'  footer-hide :mask-closable="false">
      <HxSignTrackImportListComponent
        v-on:funclose="closeModa(2)"
        v-on:func="saveAndCloseModal(2)"
        v-if="modal2"
       :rowData="this.rowData"
      />
    </Modal>


  </div>
</template>
<script>
import HxAddSignConfigureComponent from "../../../components/hx/sign/addSignConfigure";
import HxSignTrackImportListComponent from "../../../components/hx/sign/signTrackImportList";

export default {
  name: "HxAppInfoList",
  components: {
    HxAddSignConfigureComponent,
    HxSignTrackImportListComponent,
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      modal2:false,
      modal3:false,
      loading: false,
      queryParams: {
        pageNo: "",
        pageSize: "",
        appType: "",
        isTop: "",
        status: "",
			},
      columns: [ 
        // {
        //   type: 'selection',
        //   width: 60,
        //   align: 'center' 
        //  },
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "主键id",
          width: 100,
          key: "id",
          tooltip: true,
        },
        {
          title: "签到点名称",
          width: 100,
          key: "positionName",
          tooltip: true,
        },
        {
          title: "签到定位地址",
          width: 100,
          key: "address",
          tooltip: true,
         
        },
        {
          title: "经度",
          width: 100,
          key: "longitude",
          tooltip: true,
        },
        {
          title: "维度",
          width: 120,
          key: "latitude",
          tooltip: true,
        },
        {
          title: "签到范围",
          width: 100,
          key: "offset",
          tooltip: true,
        },
        {
          title: "公司",
          width: 100,
          key: "companyName",
          tooltip: true,
        },
        {
          title: "项目",
          width: 100,
          key: "projectName",
          tooltip: true,
        },
        {
          title: "别名",
          width: 100,
          key: "alias",
          tooltip: true,
        },
        {
          title: "创建人",
          width: 100,
          key: "createUserName",
          tooltip: true,
        },
        {
          title: "修改人",
          width: 100,
          key: "updateUserName",
          tooltip: true,
        },
        {
          title: "创建时间",
          width: 150,
          key: "createTime",
          tooltip: true,
        },
        {
          title: "修改时间",
          width: 150,
          key: "updateTime",
          tooltip: true,
        },
        {
          title: "操作",
          width: 300,
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
                      this.update(params);
                    }
                  }
                },
                "编辑"
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
                      this.updateAppUserRole(params);
                    }
                  }
                },
                "权限配置"
              )
            ]);
            
    
          }
        }
      ],
      rowData:{},
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getHxSignTrackConfigureList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getHxSignTrackConfigureList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    }, 
    handleSelectAll (status) {
      this.$refs.selection.selectAll(status);
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHxSignTrackConfigureList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxSignTrackConfigureList(this.currentPage);
    },
    query() {
      if(this.queryParams.status === -1){
				this.queryParams.status = "";
      }
      if(this.queryParams.isTop === -1){
				this.queryParams.isTop = "";
      }
      if(this.queryParams.appType === -1){
				this.queryParams.appType = "";
      }
      this.currentPage=1;
      this.getHxSignTrackConfigureList(this.currentPage);
    },
    openPowerComponent(e) {
      this.roleId = e.row.bRoleId;
      this.modal = true;
    },
    //修改-编辑
    update(node) {
      this.rowData = node.row;
      this.rowData.actionType = "update";
      this.modal = true;
    },
    //权限配置
    updateAppUserRole(node) {
      this.rowData = node.row;
      this.rowData.actionType = "info";
      this.modal2 = true;
    },
    //权限明细
    getAppUserRoleList(node) {
      this.rowData = node.row;
      this.modal3 = true;
    },
    add() {
      this.rowData.actionType = "add";
      this.modal = true;
    },
    //关闭授权组件
    closeModal(type) {
      if(type==1){
        this.modal = false;
      }else if(type==2){
        this.modal2 = false;
      }else if(type==3){
        this.modal3 = false;
      }

    },
    //关闭授权组件
    saveAndCloseModal(type) {

      if(type==1){
        this.modal = false;
        this.getHxSignTrackConfigureList(this.currentPage);
      }else if(type==2){
        this.modal2 = false;
        this.getHxSignTrackConfigureList(this.currentPage);
      }else if(type==3){
        this.modal3 = false;
      }


    },
  },
  mounted() {
    this.getHxSignTrackConfigureList(this.currentPage);
  }
};
</script>
