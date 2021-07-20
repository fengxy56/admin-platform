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
        监理公司
        <Input v-model="queryParams.jlCompanyName" placeholder="监理公司" style="width: 100px" />
      </div>    
      </div> 
    <div class="powerInput">  
       <div>
        开始时间：
        <DatePicker v-model="queryParams.startSignTime" @on-change="changeStartTime"  format="yyyy-MM-dd" type="datetime"  placeholder="请选择开始时间" style="width: 150px"></DatePicker>
      </div>
      <div>
        结束时间：
        <DatePicker v-model="queryParams.endSignTime" @on-change="changeEndTime"  format="yyyy-MM-dd" type="datetime"  placeholder="请选择结束时间" style="width: 150px"></DatePicker>
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>      
      <div>
        <Button type="success" @click="exportExcel">导出</Button>
      </div>             
    </div>

    <Table
      stripe
      border
      :height="360"
      :columns="columns"
      :data="data"
      ref="tables"
      size="small"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>

  </div>
</template>
<script>
import HxAddSignConfigureComponent from "../../../components/hx/sign/addSignConfigure";
import HxSignTrackImportListComponent from "../../../components/hx/sign/signTrackImportList";
import moment from "moment";

export default {
  name: "HxAppInfoList",
  components: {
    HxAddSignConfigureComponent,
    HxSignTrackImportListComponent,
  },
  props: {
    searchParams: Object,
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal3:false,
      loading: false,
      queryParams: {
        companyName: "",
        projectName: "",
        startSignTime: "",
        endSignTime: "",
			},
      columns: [ 
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "公司",
          width: 100,
          key: "cityCompanyName",
          tooltip: true,
        },
        {
          title: "项目",
          width: 130,
          key: "projectName",
          tooltip: true,
         
        },
        {
          title: "监理公司",
          width: 100,
          key: "jlCompanyName",
          tooltip: true,
         
        },{
          title: "账户",
          width: 100,
          key: "account",
          tooltip: true,
        },
        {
          title: "姓名",
          width: 100,
          key: "name",
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
         
        },
        {
          title: "打卡天数",
          width: 100,
          key: "dkCount",
          tooltip: true,
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

    getHxSignTrackRecordSummary: async function(pages) {
      this.loading = true;

      
      if( typeof(this.queryParams.startSignTime)=="object"){
        this.queryParams.startSignTime = moment(this.queryParams.startSignTime).format("yyyy-MM-DD")+" 00:00:00";
      }

      if( typeof(this.queryParams.endSignTime)=="object"){
        this.queryParams.endSignTime = moment(this.queryParams.endSignTime).format("yyyy-MM-DD")+" 23:59:59";
      }

      const result = await this.$api.getHxSignTrackRecordSummary(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data;
        this.totals = result.data.total;
      }else{
            console.log(result.errMsg);
            this.$Message.error(result.errMsg);
            this.loading = false;
      }


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
      this.getHxSignTrackRecordSummary(this.currentPage);
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
        this.getHxSignTrackRecordSummary(this.currentPage);
      }else if(type==2){
        this.modal2 = false;
        this.getHxSignTrackRecordSummary(this.currentPage);
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
        +"&jlCompanyName="+encodeURIComponent(encodeURIComponent(this.queryParams.jlCompanyName))
        
      );
    }
  },
  mounted() {
    console.log( this.searchParams.companyName);
    this.queryParams.companyName = this.searchParams.companyName;
    this.queryParams.projectName = this.searchParams.projectName;
    this.queryParams.startSignTime = this.searchParams.startSignTime;
    this.queryParams.endSignTime = this.searchParams.endSignTime;
    this.getHxSignTrackRecordSummary(this.currentPage);
  }
};
</script>
