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
        公司名称
        <Input v-model="queryParams.companyName" placeholder="公司名称" style="width: 100px" />
      </div>
      <div>
        项目名称
        <Input v-model="queryParams.projectName" placeholder="项目名称" style="width: 100px" />
      </div>
      <div>
        姓名
        <Input v-model="queryParams.userName" placeholder="姓名" style="width: 100px" />
      </div>   
      <div>
        账户
        <Input v-model="queryParams.account" placeholder="账户" style="width: 100px" />
      </div>        
       <div>
        开始时间：
        <DatePicker v-model="queryParams.startSignTime" @on-change="changeStartTime"  format="yyyy-MM-dd" type="date"  placeholder="请选择开始时间" style="width: 150px"></DatePicker>
      </div>
      <div>
        结束时间：
        <DatePicker v-model="queryParams.endSignTime" @on-change="changeEndTime"  format="yyyy-MM-dd" type="date"  placeholder="请选择结束时间" style="width: 150px"></DatePicker>
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>      
      <div>
        <Button type="success" @click="summary">数据汇总</Button>
      </div>          
      <div>
        <Button type="success" @click="exportExcel">导出</Button>
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

    <Modal v-model="modal3" title="数据汇总" width="800" max-height='300'  footer-hide :mask-closable="false">
      <HxsignTrackRecordSummaryComponent
        v-on:funclose="closeModa(2)"
        v-on:func="saveAndCloseModal(2)"
        v-if="modal3"
       :searchParams="this.searchParams"
      />
    </Modal>

  </div>
</template>
<script>
import HxsignTrackRecordSummaryComponent from "../sign/signTrackRecordSummary";
import moment from "moment";

export default {
  name: "HxAppInfoList",
  components: {
    HxsignTrackRecordSummaryComponent,
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
        companyName: "",
        projectName: "",
        userName:"",
        account:"",
        startSignTime: moment().format("yyyy-MM")+"-01",
        endSignTime: moment().format("yyyy-MM-DD"),
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
          title: "记录id",
          width: 100,
          key: "recordId",
          tooltip: true,
        },
        {
          title: "账户",
          width: 130,
          key: "account",
          tooltip: true,
         
        },
        {
          title: "姓名",
          width: 100,
          key: "userName",
          tooltip: true,
         
        },
        {
          title: "岗位",
          width: 130,
          key: "jobTitle",
          tooltip: true,
         
        },
        {
          title: "是否在职",
          width: 100,
          key: "status",
          tooltip: true,
         
        },
        {
          title: "最后修改时间",
          width: 150,
          key: "lastUpdateTime",
          tooltip: true,
         
        },{
          title: "公司",
          width: 200,
          key: "companyName",
          tooltip: true,
        },
        {
          title: "项目",
          width: 300,
          key: "projectName",
          tooltip: true,
        },
        {
          title: "签到点名称",
          width: 100,
          key: "positionName",
          tooltip: true,
        },
        {
          title: "签到时间",
          width: 150,
          key: "signTime",
          tooltip: true,
        },
      ],
      rowData:{},
      searchParams:{},
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getHxSignTrackRecordList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;


console.log(this.queryParams.startSignTime);

      if(this.queryParams.startSignTime !=null && this.queryParams.startSignTime !='' 
          && typeof(this.queryParams.startSignTime)=="object"){
        this.queryParams.startSignTime = moment(this.queryParams.startSignTime).format("yyyy-MM-DD")+" 00:00:00";
      }

      if(this.queryParams.endSignTime !=null &&   this.queryParams.startSignTime !=''
           && typeof(this.queryParams.endSignTime)=="object"){
        this.queryParams.endSignTime = moment(this.queryParams.endSignTime).format("yyyy-MM-DD")+" 23:59:59";
      }

      this.loading = true;
      const result = await this.$api.getHxSignTrackRecordList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }else{
            console.log(result.errMsg);
            this.$Message.error(result.errMsg);
            this.loading = false;
      }
    }, 
    handleSelectAll (status) {
      this.$refs.selection.selectAll(status);
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHxSignTrackRecordList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxSignTrackRecordList(this.currentPage);
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
      this.getHxSignTrackRecordList(this.currentPage);
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
      this.searchParams = node.row;
      this.modal3 = true;
    },
    summary() {
      this.searchParams = this.queryParams;
      this.modal3 = true;
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
        this.getHxSignTrackRecordList(this.currentPage);
      }else if(type==2){
        this.modal2 = false;
        this.getHxSignTrackRecordList(this.currentPage);
      }else if(type==3){
        this.modal3 = false;
      }


    },
    changeStartTime(date) {

        console.log("changeStartTime:"+date);
        
				if(date!=null && date!='' && typeof(date) != "undefined"){
					this.queryParams.startSignTime=date+" 00:00:00";
				}else{
					this.queryParams.startSignTime="";
				}
				
			},
			changeEndTime(date) {
        console.log("changeEndTime:"+date);
        
				if(date!=null && date!='' && typeof(date) != "undefined"){
					this.queryParams.endSignTime=date+" 23:59:59";
				}else{
					this.queryParams.endSignTime="";
				}
				
			},
    exportExcel(){
      window.location.href=(window.location.origin+`/api/hkdc-opex-api/hxSignTrackRecord/exportExcel?startTime=`
        +this.queryParams.startSignTime+'&endTime='+this.queryParams.endSignTime
        +"&companyName="+encodeURIComponent(encodeURIComponent(this.queryParams.companyName))
        +"&projectName="+encodeURIComponent(encodeURIComponent(this.queryParams.projectName))
      );
    }
  },
  mounted() {
    console.log("moment:"+moment().format("yyyy-MM-DD"));
    this.getHxSignTrackRecordList(this.currentPage);
  }
};
</script>
