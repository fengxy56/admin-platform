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
        轻应用id：
        <Input v-model="queryParams.appId" placeholder="轻应用id" style="width: 1rem" />
      </div>
      <div>
        轻应用名称：
        <Input v-model="queryParams.appName" placeholder="轻应用名称：" style="width: 1rem" />
      </div>

       <div>
        权限状态
              <Select v-model="queryParams.status" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="0">禁用</Option>
                <Option :value="1">全员</Option>
                <Option :value="2">部分</Option>
              </Select>
         
      </div>
      <div> 
          是否置顶:
              <Select v-model="queryParams.isTop" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="0">否</Option>
                <Option :value="1">是</Option>
              </Select>

      </div>
      <div>
        应用类型:
              <Select v-model="queryParams.appType" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="1">轻应用</Option>
                <Option :value="2">固定</Option>
              </Select>
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>      
      <div>
        <Button type="success" @click="add">新增</Button>
      </div>      
      <div>
        <Button type="warning" @click="getAppUserRoleList">权限明细</Button>
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
    <Modal v-model="modal" title="轻应用信息" width="600" footer-hide :mask-closable="false">
      <HxAppInfoComponent
        v-on:funclose="closeModal(1)"
        v-on:func="saveAndCloseModal(1)"
        v-if="modal"
       :rowData="this.rowData"
      />
    </Modal>

    <Modal v-model="modal2" title="可见权限配置" width="1000" max-height='600'  footer-hide :mask-closable="false">
      <HxAppUserImportListComponent
        v-on:funclose="closeModa(2)"
        v-on:func="saveAndCloseModal(2)"
        v-if="modal2"
       :rowData="this.rowData"
      />
    </Modal>


    <Modal v-model="modal3" title="应用权限明细" width="1000" height='350'  footer-hide :mask-closable="false">
      <HxAppUserRoleListAuthComponent
        v-on:funclose="closeModal(3)"
        v-on:func="saveAndCloseModal(3)"
        v-if="modal3"
       :rowData="this.rowData"
      />
    </Modal>

  </div>
</template>
<script>
import HxAppInfoComponent from "../../../components/hx/app/addApp";
import HxAppUserImportListComponent from "../../../components/hx/app/appUserImportList";
import HxAppUserRoleListAuthComponent from "../../../components/hx/app/appUserRoleAuthList";

export default {
  name: "HxAppInfoList",
  components: {
    HxAppInfoComponent,
    HxAppUserImportListComponent,
    HxAppUserRoleListAuthComponent
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
        appId: "",
        appName: "",
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
          title: "轻应用id",
          width: 100,
          key: "appId",
          tooltip: true,
        },
        {
          title: "轻应用名称",
          width: 100,
          key: "appName",
          tooltip: true,
        },
        {
          title: "用户权限",
          width: 100,
          key: "status",
          tooltip: true,
          render: (h, params) => {
            if (params.row.status === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "禁用"
              );
            } else if (params.row.status === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "全员"
              );
            } else if (params.row.status === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "primary",
                    type: "border"
                  }
                },
                "内部"
              );
            }else if (params.row.status === 3) {
              return h(
                "Tag",
                {
                  props: {
                    color: "geekblue",
                    type: "border"
                  }
                },
                "商务"
              );
            }else if (params.row.status === 4) {
              return h(
                "Tag",
                {
                  props: {
                    color: "warning",
                    type: "border"
                  }
                },
                "部分"
              );
            }

          }
        },
        {
          title: "是否置顶",
          width: 100,
          key: "isTop",
          tooltip: true,
          render: (h, params) => {
            if (params.row.isTop === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "否"
              );
            } else if (params.row.isTop === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "是"
              );
            }

          }
        },
        {
          title: "排序",
          key: "showOrder",
          align: "center",
          width: 90,
          render: (h, params) => {
            return h(
              "Tag",
              {
                props: this.$tool.getNumberColor(params.row.showOrder),
                style: {
                  borderRadius: "50%",
                  textAlign: "center"
                }
              },
              params.row.showOrder
            );
          }
        },
        {
          title: "应用类型",
          width: 120,
          key: "appType",
          tooltip: true,
          render: (h, params) => {
            if (params.row.appType === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "轻应用"
              );
            } else if (params.row.appType === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "固定应用"
              );
            }else if (params.row.appType === 8) {
              return h(
                "Tag",
                {
                  props: {
                    color: "primary",
                    type: "border"
                  }
                },
                "原生应用"
              );
            }

          }
        },
        {
          title: "修改人",
          width: 100,
          key: "updateAccout",
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
         
          title: "备注",
          key: "remark",
          align: "center",
          width: 110,
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
      appId: "",
      appName: "",  
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getHxCustAppInfoList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getHxCustAppInfoList(this.queryParams);
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
      this.getHxCustAppInfoList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxCustAppInfoList(this.currentPage);
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
      this.getHxCustAppInfoList(this.currentPage);
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
      this.rowData.actionType = "update";
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
        this.getHxCustAppInfoList(this.currentPage);
      }else if(type==2){
        this.modal2 = false;
        this.getHxCustAppInfoList(this.currentPage);
      }else if(type==3){
        this.modal3 = false;
      }


    },
  },
  mounted() {
    this.getHxCustAppInfoList(this.currentPage);
  }
};
</script>
