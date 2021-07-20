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
					<FormItem prop="hxMsgId" label="鸿信消息id">
						<Input disabled type="text" v-model="formData.hxMsgId" placeholder="鸿信消息id" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="msgTitle" label="标题">
						<Input disabled type="text" v-model="formData.msgTitle" placeholder="标题" />
					</FormItem>
				</Col>				
				<Col span="12">
					<FormItem prop="requestId" label="标题">
						<Input disabled type="text" v-model="formData.requestId" placeholder="标题" />
					</FormItem>
				</Col>	

			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="userId" label="账号">
						<Input disabled type="text" v-model="formData.userId" placeholder="账号" />
					</FormItem>
				</Col>				
				<Col span="12">
					<FormItem prop="processCode" label="其它编码">
						<Input disabled type="text" v-model="formData.processCode" placeholder="其它编码" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">

				<Col span="12">
					<FormItem prop="undoStatus" label="审批状态">
						<RadioGroup v-model="formData.ifTodo">
							<Radio :label="0">已办</Radio>
							<Radio :label="1">待办</Radio>
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
		name: "HxUpdateHxPushInfoComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				formData: {
					id:"",
					hxMsgId:"",
					msgTitle:"",
					userId:"",
					requestId:"",
					ifTodo:"",
					processCode:"",
					deleteStatus:0,
				}
			};
		},
		methods: {
			async appSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {

						//console.log(this.formData);

						const result = await this.$api.updateHongkunPushinfo(this.formData);
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
				console.log(11);
				this.$emit("funclose"); //关闭父级modal
			}
		},
		mounted() {
			//console.log(this.rowData);
			
			if (this.rowData.actionType === "update") {
				//修改
				this.formData.id = this.rowData.id;
				this.formData.hxMsgId = this.rowData.hxMsgid;
				this.formData.msgTitle = this.rowData.msgTitle;
				this.formData.requestId = this.rowData.requestId;
				this.formData.userId = this.rowData.userId;
				this.formData.processCode = this.rowData.processCode;
				this.formData.ifTodo = this.rowData.ifTodo;//是否待办，0否，1是

			} 



		}
	};
</script>
