<style>

.userInfo{
	max-height: 80%;
	height:400px;
	overflow:auto;
}

.dict_form {
  padding: 8px 20px 0 0;
}

</style>
<template>
	<div class="userInfo" >
		<Form ref="formData" :model="formData" :label-width="120" height="300" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="orgName" label="组织名称">
						<Input type="text" v-model="formData.orgName" placeholder="请输入组织" />
					</FormItem>
				</Col>				
			</Row>

			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="levelType" label="层级">
						<Input disabled type="text" v-model="formData.levelType"  />
					</FormItem>
				</Col>
			</Row>

			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="parentId" label="父级组织id">
						<Input type="text" levelType v-model="formData.parentId"  />
					</FormItem>
				</Col>				
			</Row>
		
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="parentName" label="父级组织名称">
						<Input disabled type="text" v-model="formData.parentName"  />
					</FormItem>
				</Col>
			</Row>
			
		</Form>
		<div slot="footer" class="clearfix">
			<Button type="success" @click="appSubmit('formData')" style="float:right;">保存</Button>
			<Button type="info" @click="closeModal" style="float:right;margin-right: 10px;">取消</Button>
		</div>
	</div>
</template>
<script>
	export default {
		name: "AddUserComponent",
		props: {
			rowOrgData: Object
		},
		data() {
			return {
				formData: {
					id:"",
					orgName:"",
					parentId:"",
					parentName:"",
					status:1,
					levelType:1,
				}
			};
		},
		methods: {
			async appSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

	
						this.loading = false;


						if(this.rowOrgData.actionType === "add"){
							const result = await this.$api.saveSysOrg(this.formData);
							if (result.code === 1) {
								this.$Message.success("修改成功!");
								this.$emit("func"); //关闭父级modal
							}else{
								console.log(result.errMsg);
								this.$Message.error(result.errMsg);
							}

						}else if(this.rowOrgData.actionType === "update"){
							const result = await this.$api.updateSysOrg(this.formData);
							if (result.code === 1) {
								this.$Message.success("修改成功!");
								this.$emit("func"); //关闭父级modal
							}else{
								console.log(result.errMsg);
								this.$Message.error(result.errMsg);
							}
						}
						
						
		

				
					}
				});
			},
			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			}
		},
		mounted() {
			 console.log(this.rowOrgData);
		
			if (this.rowOrgData.actionType === "add") {

				if(this.rowOrgData.parentId == null || this.rowOrgData.parentId == ''  || typeof this.rowOrgData.id == 'undefined'){
					this.formData.parentId = "-1";
					this.formData.parentName = "顶级";
				}else{
					this.formData.parentId = this.rowOrgData.id;
					this.formData.parentName = this.rowOrgData.orgName;
					this.formData.levelType = this.rowOrgData.levelType+1;
				}
				

			}else if (this.rowOrgData.actionType === "update"){
					this.formData.id = this.rowOrgData.id;
					this.formData.orgName = this.rowOrgData.orgName;
					this.formData.parentId = this.rowOrgData.parentId;
					this.formData.parentName = this.rowOrgData.parentName;
			}

		}
	};
</script>
