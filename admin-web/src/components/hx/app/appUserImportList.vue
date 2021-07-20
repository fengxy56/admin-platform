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
        	账号
        	<Input v-model="queryParams.positionName" placeholder="签到名称" style="width: 2rem" />
      	</div>
		<div>
			<Button type="primary" @click="query">查询</Button>
		</div>    
		<div>
			<Button type="success" @click="add">新增</Button>
		</div>    
	  	<div>
		  <Upload 
                ref="upload"
                    action="/api/hkdc-opex-api/hxAppAuth/readExcel"
                    name="file"
                    :show-upload-list="false" 
					:default-file-list="defaultList" 
                    :format ="['xlsx','xls']"
					:on-format-error="handleImportFormatError"
        			:on-success="handleImportSuccess"
        			:on-error="handleImportError"
					 multiple type="drag" >
					
                    <Button type="primary" icon="ios-cloud-upload-outline">用户导入</Button>
          </Upload>
	 	</div>

      <div>
        <Button type="success" @click="saveBathHxUserRoleAppAuth">确定导入</Button>
      </div>     
    </div>

    <Table stripe border height="300"  :columns="columns"  :data="data" ref="tables" size="small" :tooltip-theme="menuTheme" :loading="loading">

			<template slot-scope="{ row , index}" slot="orderNumber">
				<strong>{{ index+1}}</strong>
			</template>
			<template slot-scope="{ row , index}" slot="authId">
				<Input type="text" v-model="authId" v-if="editIndex === index" />
      			<span v-else>{{ row.authId }}</span>
			</template>
			<template slot-scope="{ row ,index}" slot="authName">
				<Input type="text" v-model="authName" v-if="editIndex === index" />
				<span v-else>{{ row.authName }}</span>
			</template>

			<template slot-scope="{ row ,index}" slot="authType">
				<Input type="text" v-model="authType" v-if="editIndex === index" />
				<span v-else>{{ row.authType }}</span>
			</template>

			<template slot-scope="{ row, index }" slot="action">
				<div v-if="editIndex === index">
					<Button @click="handleTableSave(index)">保存</Button>
					<Button @click="editIndex = -1">取消</Button>
				</div>
				<div v-else>
					<Button @click="handleTableEdit(row, index)" style="padding-rigth:15px;">操作</Button>
					<Button @click="removeTable(index)" type="error" >删除</Button>
				</div>
			</template>
	</Table>

	
	</div>
</template>
<script>
	export default {
		name: "HxAppUserImportListComponent",
		props: {
			defaultList: Array,
			rowData: Object,   
			menuTheme: String,
			windowHeight: Number
		},
		data() {
			return {
				loading: false,
				editIndex: -1,  // 当前聚焦的输入框的行数
				authId: '',  // 第一列输入框，当然聚焦的输入框的输入内容，与 data 分离避免重构的闪烁
				authName: '',  // 第二列输入框
				authType: 1,
				columns:[
					{
						title: "序号",
						type: "index",
						width: 120,
						align: "center"
					},
					{
						title: "账户",
						width: 120,
						key: "authId",
						tooltip: true,
						slot: 'authId',
					},
					{
						title: "姓名",
						width: 120,
						key: "authName",
						tooltip: true,
						slot: 'authName', 			
					},
					{
						title: "权限类型",
						width: 120,
						key: "authType",
						tooltip: true,
						slot: 'authType',
						render: (h, params) => {
							if (params.row.authType === 1) {
								return h(
									"Tag",
									{
									props: {
										color: "error",
										type: "border"
									}
									},
									"账号"
								);
							} else if (params.row.authType === 2) {
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
						title: '操作',
						width: 220,
						slot: 'action'
					}

				],
				data: [],
				sizes: 20,
				currentPage: 1,
				totals: 0,
				queryParams: {
					pageNo: 1,
					pageSize: 20,
					appId: "",
					appName: "",
					authId: "",
					authName: "",
					authType: "",
				},
			};
		},
		methods: {

			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			},
			async getAppUserRoleAuthList(pages){
				
				this.queryParams.pageNo = pages;
				this.queryParams.pageSize = 10000000;

				console.log(this.queryParams);
				const result = await this.$api.getAppUserRoleAuthList(this.queryParams);

				if (result.code === 1) {
					this.data = result.data.records;
					this.totals = result.data.total;
				}

			},
			async saveBathHxUserRoleAppAuth(){
				
				const param = { appId:this.rowData.appId, list: this.data};
				const result = await this.$api.saveBathHxUserRoleAppAuth(param);

				console.log(result);

				if (result.code === 1) {
					this.$Message.success(result.data);
					this.loading = false;
					//this.$emit("func"); //关闭父级modal
				}

			},
			handleImportFormatError(file){
				this.$Notice.warning({
					title: '文件格式不正确',
					desc: '文件 ' + file.name + ' 格式不正确，请上传.xls,.xlsx文件。'
				})
			},
			handleImportSuccess(res,file){
				if(res.code === 1){
					this.dialoLead = false
					this.data=res.data;
					this.$Message.success("数据导入成功！")
					// this._getBookList()
					this.$refs.upload.clearFiles()
				}
			},
			handleImportError(error,file){
				this.$Message.error("数据导入失败！")
			}, 
			handleTableEdit (row, index) {
				this.ssid = row.ssid;
				this.bssid = row.bssid;
				this.infoAddress = row.infoAddress;
				this.editIndex = index;
			},
			handleTableSave (index) {
				this.data[index].authId = this.authId;
				this.data[index].authName = this.authName;
				this.editIndex = -1;
			}, 
			removeTable (index) {
                this.data.splice(index, 1);
            },
			query() {
				// if(this.queryParams.status === -1){
				// 			this.queryParams.status = "";
				// }
				// if(this.queryParams.isTop === -1){
				// 			this.queryParams.isTop = "";
				// }
				// if(this.queryParams.appType === -1){
				// 			this.queryParams.appType = "";
				// }
				this.currentPage=1;
				this.getAppUserRoleAuthList(this.currentPage);
			},
			add() {
				const maxIndex = this.data.length;
	        	this.data.splice(this.data.length, 0,{
                        authId: '',
						authName: '',
						authType:1
                });
			}, 



		},
		mounted() {
			this.queryParams.appId = this.rowData.appId;
		}
	};
</script>
