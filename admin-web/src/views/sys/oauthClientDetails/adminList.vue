<style>
.dict_form {
  padding: 8px 20px 0 0;
  /* width: 100%; */
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

</style>
<template>
	<div class="commons">
		<div class="powerInput">
			<div>
				管理员id
				<Input v-model="adminId" placeholder="请输入管理员id" style="width: 2rem" />
			</div>
			<div>
				管理员名称
				<Input v-model="adminName" placeholder="请输入管理员名称" style="width: 2rem" />
			</div>
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
			<template slot-scope="{ row , index}" slot="adminId">
				<Input type="text" v-model="adminId" v-if="editIndex === index" />
      			<span v-else>{{ row.adminId }}</span>
			</template>
			<template slot-scope="{ row ,index}" slot="adminName">
				<Input type="text" v-model="adminName" v-if="editIndex === index" />
				<span v-else>{{ row.adminName }}</span>
			</template>
			<template slot-scope="{ row, index }" slot="adminType">
				<!-- <Input type="text" v-model="adminType" v-if="editIndex === index" /> -->

				<RadioGroup v-model="adminType" v-if="editIndex === index">
					<Radio :label="1">用户</Radio>
					<Radio :label="2">角色</Radio>							
				</RadioGroup>

      			<span v-else>{{ row.adminType }}</span>
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

	</div>
</template>
<script>
	export default {
		name: "AdminListComponent",
		props: {
			rowData: Object, 
			menuTheme: String,
			windowHeight: Number
		},
		data() {
			return {
				groupFlag: false,
				editIndex: -1,  // 当前聚焦的输入框的行数
				adminId: '',  // 第一列输入框，当然聚焦的输入框的输入内容，与 data 分离避免重构的闪烁
				adminName: '',  // 第二列输入框
				adminType: '',  // 第三列输入框
				columns:[
					{
						title: "序号",
						type: "index",
						width: 70,
						align: "center"
					},
					{
						title: "管理员id",
						width: 160,
						slot: 'adminId',
					},
					{
						title: "管理员名称",
						width: 160,
						slot: 'adminName',

					},
					{
						title: "管理员类型",
						width: 160,
						key: "adminType",
						tooltip: true,
						slot: 'adminType',
						render: (h, params) => {
							if (params.row.adminType === 1) {
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
							} else if (params.row.adminType === 2) {
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
				data:[],
				formData: {
					id: "",
					companyName: "",
					companyShortName: "",
					companyCode: "",
					status: 1,
					showOrder: 1,
					remark: "",
				
				},
				ruleInline: {
					companyName: [{
						required: true,
						message: "公司名称不能为空",
						trigger: "blur"
					}],
					companyCode: [{
						required: true,
						message: "公司编码不能为空",
						trigger: "blur"
					}]
				}
			};
		},
		methods: {
			async dictSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {

						const result = await this.$api.saveOrUpdateSysCompany(this.formData);

						console.log(result);

						if (result.code === 1) {
							this.$Message.success("保存成功!");
							this.loading = false;
							this.$emit("func"); //关闭父级modal
						}else {
							this.$Message.warning(result.errMsg);
							// this.$emit("func"); //关闭父级modal
						}

					}
				});
			},
			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			},
			query(){

			},
			add() {
				console.log("add");
				const maxIndex = this.data.length;
	        	this.data.splice(this.data.length, 0,{
                        adminId: '',
                        adminName: '',
                        adminType: ''
                });
			}, 
			remove (index) {
                this.data.splice(index, 1);
            },    
			handleEdit (row, index) {
				this.adminId = row.adminId;
				this.adminName = row.adminName;
				this.adminType = row.adminType;
				this.editIndex = index;
			},
			handleSave (index) {
				this.data[index].adminId = this.adminId;
				this.data[index].adminName = this.adminName;
				this.data[index].adminType = this.adminType;
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

			console.log("3333");
			if (this.rowData.actionType === "add") {
				//新增
				this.groupFlag = true;
			} else if (this.rowData.actionType === "update") {

				this.formData.id = this.rowData.id;
				this.formData.companyName = this.rowData.companyName;
				this.formData.companyShortName = this.rowData.companyShortName;
				this.formData.companyCode = this.rowData.companyCode;

				this.formData.status = this.rowData.status;
				this.formData.showOrder = this.rowData.showOrder;
				this.formData.remark = this.rowData.remark;

			} 

		}
	};
</script>
