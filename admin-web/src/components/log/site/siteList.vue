<style>
.dict_form {
  padding: 8px 20px 0 0;
  /* width: 100%; */
}

.search {
  width: 100px;
  float: right;
}
.commons {
  line-height: 1;
  max-height: 80%;
}
.powerInput {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 5px;
}
.powerInput > div {
  margin-left: 10px;
}
</style>
<template>
	<div class="commons">
      <div class="powerInput">
		<div>
			开始时间：
			<DatePicker v-model="queryParams.startTime" type="date" format="yyyy-MM-dd" 
			placeholder="请选择开始时间" style="width: 150px"></DatePicker>
		</div>
		<div>
		  结束时间：
			<DatePicker v-model="queryParams.endTime" type="date" format="yyyy-MM-dd" 
			placeholder="请选择结束日期" style="height: 32px;"></DatePicker>

		</div>
							
		<div>
			<Button type="info" @click="query">查询</Button>
		</div>
		<div>
			<Button type="success" @click="siteXxExcel">详细数据导出</Button>
		</div> 
		<div>
			<Button type="success" @click="siteHzExcel">汇总数据导出</Button>
		</div>    

    </div>

    <Table
      stripe
      border
      :height="300"
      :columns="columns"
      :data="data"
      ref="tables"
      size="small"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>

		<Modal v-model="qrModal" title="访问次数汇总下载" width="80%" footer-hide >
			<qrcodeComponent v-on:funclose="closeQrModal" v-if="qrModal" :pageType="2" 
			:startTime="this.startTime" 
			:pages="this.qrUrl" 
			:endTime="this.endTime"  qrLocation="项目详情页"/>
		</Modal>
	
	</div>
</template>
<script>
	export default {
		name: "SiteComponent",
		props: {
			defaultList: Array,
			rowData: Object,   
			menuTheme: String,
			windowHeight: Number
		},
		data() {
			return {
				qrModal:false,
				loading: false,
				queryParams: {
					beginTime: "",
					endTime: "",
				},
				columns:[
					{
						title: "序号",
						type: "index",
						width: 170,
						align: "center"
					},
					{
						title: "账户",
						width: 200,
						key: "userid",
						tooltip: true,
					},
					{
						title: "时间",
						width: 200,
						key: "visitDate",
						tooltip: true,
					},
					{
						title: "次数",
						width: 200,
						key: "visitCount",
						tooltip: true,
					}

				],
				startTime:"",
				endTime:"",
				data: []
			};
		},
		methods: {

			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			},
			getUserVisitSiteCountList: async function() {

				console.log("this.startTime:"+this.queryParams.startTime);

				const data = {
					startTime: this.queryParams.startTime,
					endTime: this.queryParams.endTime
				};
				this.loading = true;
				const result = await this.$api.getUserVisitSiteCountList(data);
				this.loading = false;
				
				if (result.code === 1) {
					this.data = result.data;
				}
				
			},    
			query() {
				this.getUserVisitSiteCountList();
			},
			siteXxExcel(){
				window.location.href=(window.location.origin+`/api/hkdc-opex-api/logHxSite/siteXxExcel?startTime=`
				+this.queryParams.startTime+'&endTime='+this.queryParams.endTime);
			},
			siteHzExcel(){
				window.location.href=(window.location.origin+`/api/hkdc-opex-api/logHxSite/siteHzExcel?startTime=`
				+this.queryParams.startTime+'&endTime='+this.queryParams.endTime);
			},
			closeQrModal(){
				this.qrModal = false;
			}, 
			handleFormatError(file){
				this.$Notice.warning({
					title: '文件格式不正确',
					desc: '文件 ' + file.name + ' 格式不正确，请上传.xls,.xlsx文件。'
				})
			},
			handleSuccess(res,file){
				console.log(res);
				if(res.code === 1){
					this.dialoLead = false
					this.data=res.data;
					this.$Message.success("数据导入成功！")
					// this._getBookList()
					this.$refs.upload.clearFiles()
				}
			},
			handleError(error,file){
				this.$Message.error("数据导入失败！")
			},



		},
		mounted() {

		}
	};
</script>
