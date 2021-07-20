<style>
.dict_form {
  padding: 8px 20px 0 0;
  /* width: 100%; */
}
</style>
<template>
	<div>
		<Form ref="formData" :model="formData" :label-width="80" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="id" label="主键id">
						<Input disabled type="text" v-model="formData.id" placeholder="主键id" />
					</FormItem>
				</Col>				
				<Col span="12">
					<FormItem prop="msgId" label="消息id">
						<Input disabled type="text" v-model="formData.msgId" placeholder="消息id" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="content" label="标题">
						<Input disabled type="text" v-model="formData.content" placeholder="标题" />
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="account" label="账号">
						<Input disabled type="text" v-model="formData.account" placeholder="账号" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
							
				<Col span="12">
					<FormItem prop="undoStatus" label="审批类型">
						<RadioGroup v-model="formData.undoStatus">
							<Radio :label="0">待办</Radio>
							<Radio :label="1">已办</Radio>
							<Radio :label="2">通知</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="deleteStatus" label="是否删除">
						<RadioGroup v-model="formData.deleteStatus">
							<Radio :label="0">否</Radio>
							<Radio :label="1">是</Radio>
						</RadioGroup>
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
		name: "HxUpdateUndoComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				formData: {
					id:"",
					msgId:"",
					content:"",
					deleteStatus:0,
					undoStatus:0,
					account: "",
					delete:"",
					todoStatus: "",
				}
			};
		},
		methods: {
			async appSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

						if(this.formData.deleteStatus == 0){//是否删除，0否，1是
							this.formData.delete = false;
						}else if(this.formData.deleteStatus == 1){//是否删除，0否，1是
							this.formData.delete = true;
						}

						if(this.formData.undoStatus == 0){//是否待办已办，0待办，1已办
							this.formData.todoStatus = "undo";
						}else if(this.formData.undoStatus == 1){//是否待办已办，0待办，1已办
							this.formData.todoStatus = "done";
						}else if(this.formData.undoStatus == 2){//是否待办已办，0待办，1已办
							this.formData.todoStatus = "notify";
						}
						

						const result = await this.$api.updateHxUserUndoStatus(this.formData);
						this.loading = false;
						if (result.code === 1) {
							this.$Message.success("修改成功!");
							this.$emit("func"); //关闭父级modal
						}else{
							console.log(result.errMsg);
							this.$Message.error(result.errMsg);
						}

				
					}
				});
			},
			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			}
		},
		mounted() {
			 console.log(this.rowData);
			if (this.rowData.actionType === "update") {
				//修改
				this.formData.id = this.rowData.id;
				this.formData.msgId = this.rowData.msgId;
				this.formData.content = this.rowData.content;
				
				if(this.rowData.delete==false){
					this.formData.deleteStatus = 0;//是否删除，0否，1是
				}else if(this.rowData.delete==true){
					this.formData.deleteStatus = 1;//是否删除，0否，1是
				}


				if(this.rowData.todoStatus=="undo"){
					this.formData.undoStatus = 0;//是否待办已办，0待办，1已办
				}else if(this.rowData.todoStatus=="done"){
					this.formData.undoStatus = 1;//是否待办已办，0待办，1已办
				}else if(this.rowData.todoStatus=="notify"){
					this.formData.undoStatus = 2;//是否待办已办，0待办，1已办
				}
				this.formData.account = this.rowData.account;

			} 



		}
	};
</script>
