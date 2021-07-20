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
        权限类型:
              <Select v-model="queryParams.authType" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="1">账户</Option>
                <Option :value="2">角色</Option>
              </Select>
      </div>

      <div>
        轻应用id：
        <Input v-model="queryParams.appId" placeholder="轻应用id" style="width: 1rem" />
      </div>
      <div>
        轻应用名称：
        <Input v-model="queryParams.appName" placeholder="轻应用名称：" style="width: 1rem" />
      </div>
      <div>
        权限id：
        <Input v-model="queryParams.authId" placeholder="权限id" style="width: 1rem" />
      </div>
      <div>
        权限名称：
        <Input v-model="queryParams.authName" placeholder="权限名称：" style="width: 1rem" />
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
      height="280"
      :columns="columns"
      :data="data"
      ref="tables"
      size="small"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>
    <Page
	  :current="currentPage"
      :total="totals"
      show-sizer
      :style="{padding:'15px'}"
      :page-size="sizes"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />
	
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
				columns:[
					{
						title: "序号",
						type: "index",
						width: 70,
						align: "center"
					},
					{
					title: "权限类型",
					width: 100,
					key: "isTop",
					tooltip: true,
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
							"账户"
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
						title: "轻应用Id",
						width: 100,
						key: "appId",
						tooltip: true,
					},
					{
						title: "轻应用名称",
						width: 150,
						key: "appName",
						tooltip: true,
					},
					{
						title: "权限id",
						width: 150,
						key: "authId",
						tooltip: true,
					},
					{
						title: "权限名称",
						width: 150,
						key: "authName",
						tooltip: true,
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
				this.queryParams.pageSize = this.sizes;

				console.log(this.queryParams);
				const result = await this.$api.getAppUserRoleAuthList(this.queryParams);

				if (result.code === 1) {
					this.data = result.data.records;
					this.totals = result.data.total;
				}

			},
			//切换页码
			swichPage(page) {
				this.currentPage = page;
				this.getAppUserRoleAuthList(this.currentPage);
			},
			//切换每页容量
			swichSize(size) {
				this.sizes = size;
				this.getAppUserRoleAuthList(this.currentPage);
			},
			query() {
				if(this.queryParams.authType === -1){
					this.queryParams.authType = "";
				}
				this.currentPage = 1;
				this.getAppUserRoleAuthList(this.currentPage);
			},

			exportExcel(){
				window.location.href=(window.location.origin+`/api/hkdc-opex-api/hxAppAuth/exportExcel?authType=`
				+this.queryParams.authType+'&appId='+this.queryParams.appId+"&appName="+this.queryParams.appName
				+"&authId="+this.queryParams.authId+"&authName="+this.queryParams.authName);
			}



		},
		mounted() {
			this.getAppUserRoleAuthList();
		}
	};
</script>
