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
        签到点名称：
        <Input v-model="queryParams.positionName" placeholder="签到点名称" style="width: 1.5rem" />
      </div>
      <div>
        签到点地址：
        <Input v-model="queryParams.address" placeholder="签到点地址" style="width: 1.5rem" />
      </div>
      <div>
        主键id：
        <Input v-model="queryParams.id" placeholder="主键id" style="width: 1.5rem" />
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
    <Modal v-model="modal" title="wifi信息配置" width="1000" footer-hide :mask-closable="false">
      <HxAttendanceWifiInfoListComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData"
      />
    </Modal>

  </div>
</template>
<script>
import HxAttendanceWifiInfoListComponent from "../../../components/hx/attendance/attendanceWifiInfoList";


export default {
  name: "HxAttendanceSetList",
  components: {
    HxAttendanceWifiInfoListComponent,
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      loading: false,
      queryParams: {
        pageNo: "",
        pageSize: "",
        positionName: "",
        address: "",
        id: "",
        // status: "",
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
          width: 80,
          key: "id",
          tooltip: true,
        },
        {
          title: "签到点名称",
          width: 150,
          key: "positionName",
          tooltip: true,
        },
        {
          title: "签到点地址",
          width: 260,
          key: "address",
          tooltip: true,
         
        },
        {
          title: "签到点范围",
          width: 100,
          key: "offset",
          tooltip: true,
        },
        {
          title: "经度",
          width: 100,
          key: "lng",
          tooltip: true,
        },
        {
          title: "维度",
          width: 100,
          key: "lat",
          tooltip: true,
        },
        {
          title: "更新时间",
          width: 100,
          key: "updateTime",
          tooltip: true,
        },
        {
          title: "操作",
          width: 150,
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
                "wifi信息配置"
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
    getHxAttendanceSetList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getHxAttendanceSetList(this.queryParams);
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
      this.getHxAttendanceSetList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxAttendanceSetList(this.currentPage);
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
      this.getHxAttendanceSetList(this.currentPage);
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
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getHxAttendanceSetList(this.currentPage);
    },
		
      
  },
  mounted() {
    this.getHxAttendanceSetList(this.currentPage);
  }
};
</script>
