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
  height: 100%;
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
				<Button type="primary" @click="query">查询</Button>
			</div>      
			<div>
				<Button type="success" @click="add">新增</Button>
			</div>       
			<div>
				<Button type="success" @click="save">保存</Button>
			</div>           
		</div>


		<Table :height="this.windowHeight - 200" border :columns="columns" :data="data"  size="small">
			<template slot-scope="{ row , index}" slot="orderNumber">
				<strong>{{ index+1}}</strong>
			</template>
			<template slot-scope="{ row , index}" slot="ssid">
				<Input type="text" v-model="ssid" v-if="editIndex === index" />
      			<span v-else>{{ row.ssid }}</span>
			</template>
			<template slot-scope="{ row ,index}" slot="bssid">
				<Input type="text" v-model="bssid" v-if="editIndex === index" />
				<span v-else>{{ row.bssid }}</span>
			</template>
			<template slot-scope="{ row, index }" slot="infoAddress">
				<Input type="text" v-model="infoAddress" v-if="editIndex === index" />
      			<span v-else>{{ row.infoAddress }}</span>
			</template>

			<template slot-scope="{ row, index }" slot="action">
				<div v-if="editIndex === index">
					<Button @click="handleSave(index)">保存</Button>
					<Button @click="editIndex = -1">取消</Button>
				</div>
				<div v-else>
					<Button @click="handleEdit(row, index)" style="padding-rigth:15px;">操作</Button>
					<Button @click="remove(index)" type="error" >删除</Button>
				</div>

			</template>
		</Table>

		<!-- <Table
			stripe
			border
			height="280"
			:columns="columns"
			:data="data"
			ref="tables"
			size="small"
			:tooltip-theme="menuTheme"
			:loading="loading"
		></Table> -->

	
	</div>
</template>
<script>
	export default {
		name: "HxAttendanceWifiInfoListComponent",
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
						title: "wifi名称",
						width: 160,
						slot: 'ssid',
					},
					{
						title: "ip范围",
						width: 220,
						slot: 'bssid',

					},
					{
						title: "备注信息",
						width: 260,
						slot: 'infoAddress',
	
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
				editIndex: -1,  // 当前聚焦的输入框的行数
				ssid: '',  // 第一列输入框，当然聚焦的输入框的输入内容，与 data 分离避免重构的闪烁
				bssid: '',  // 第二列输入框
				infoAddress: '',  // 第三列输入框
			};
		},
		methods: {

			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			},
			async getHxAttendanceSetById(){
				
				const data = {
					id:this.rowData.id,
				};
				const result = await this.$api.getHxAttendanceSetById(data);

				console.log(result);
				if (result.code === 1) {
					this.data = result.data.wifiInoList;
					// this.totals = result.data.total;
				}else{
                    this.$Message.error(result.errMsg);
                    this.loading = false;
				}


			},
			query() {
				this.getHxAttendanceSetById();
			},
			add() {
				const maxIndex = this.data.length;
	        	this.data.splice(this.data.length, 0,{
                        ssid: '',
                        bssid: '',
                        infoAddress: ''
                });
			}, 
			remove (index) {
                this.data.splice(index, 1);
            },    
			handleEdit (row, index) {
				this.ssid = row.ssid;
				this.bssid = row.bssid;
				this.infoAddress = row.infoAddress;
				this.editIndex = index;
			},
			handleSave (index) {
				this.data[index].ssid = this.ssid;
				this.data[index].bssid = this.bssid;
				this.data[index].infoAddress = this.infoAddress;
				this.editIndex = -1;
			},
			async save(){

				this.loading = true;

				const data = {
					id:this.rowData.id,
					wifiInoList:this.data,
				};


				console.log(data);
				const result = await this.$api.updateHxAttendanceSetById(data);

				if (result.code === 1) {
					this.$Message.success("保存成功!");
                    this.loading = false;
                    this.$emit("func"); //关闭父级modal
					this.getHxAttendanceSetById(this.rowData.id);
					// this.data = result.data.records;
					// this.totals = result.data.total;
				}else{
                     this.$Message.error(result.errMsg);
                    this.loading = false;
				}

			}

		},
		mounted() {
			//this.data = this.rowData.wifiInoList;
			this.getHxAttendanceSetById(this.rowData.id);
		}
	};
</script>
