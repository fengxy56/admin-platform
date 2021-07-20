<style>
.dict_form {
  padding: 8px 20px 0 0;
  /* width: 100%; */
}
</style>
<template>
	<div>
		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="80" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="appId" label="轻应用id">
						<Input type="text" v-model="formData.appId" placeholder="请输入轻应用id" />
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="status" label="用户权限">
						<RadioGroup v-model="formData.status">
							<Radio :label="0">禁用</Radio>
							<Radio :label="1">全员</Radio>
							<Radio :label="2">内部</Radio>
							<Radio :label="3">商务</Radio>		
							<Radio :label="4">部分</Radio>									
						</RadioGroup>
					</FormItem>
				</Col>

			</Row>

			<Row type="flex" justify="space-around" class="code-row-bg">

				<Col span="12">
					<FormItem prop="appName" label="应用名称">
						<Input type="text" v-model="formData.appName" placeholder="请输入轻应用名称" />
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isTop" label="是否置顶">
						<RadioGroup v-model="formData.isTop">
							<Radio :label="1">是</Radio>
							<Radio :label="0">否</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="appType" label="应用类型">
						<RadioGroup v-model="formData.appType">
							<Radio :label="1">轻应用</Radio>
							<Radio :label="2">固定应用</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="showOrder" label="排序">
						<InputNumber :max="10000" :min="1" v-model="formData.showOrder"></InputNumber>
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
		name: "HxAppInfoComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				formData: {
					appId: "",
					appName: "",
					appType: 1,
					isTop: 0,
					parentAppId:-1,
					status: 2,
					remark: "",
					showOrder: 1,
				},
				ruleInline: {
					// appId: [{
					// 	required: true,
					// 	message: "轻应用id不能空",
					// 	trigger: "change",
					// 	type: "number",
					// }],
					appName: [{
						required: true,
						message: "名称不能为空",
						trigger: "blur"
					}]
				}
			};
		},
		methods: {
			async appSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

							if (this.rowData.actionType === "add") {
								const result = await this.$api.saveHxCustAppInfo(this.formData);
								this.loading = false;
								if (result.code === 1) {
									this.$Message.success("保存成功!");
									this.$emit("func"); //关闭父级modal
								}else{
									console.log(result.errMsg);
									this.$Message.error(result.errMsg);
								}
								this.groupFlag = true;
							} else if (this.rowData.actionType === "update") {
								const result = await this.$api.updateHxCustAppInfo(this.formData);
								this.loading = false;
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
			// console.log(this.rowData);
			if (this.rowData.actionType === "update") {
				//修改
				this.formData.appId = this.rowData.appId;
				this.formData.appName = this.rowData.appName;
				this.formData.appType = this.rowData.appType;
				this.formData.isTop = this.rowData.isTop;
				this.formData.status = this.rowData.status;
				this.formData.showOrder = this.rowData.showOrder;
				this.formData.remark = this.rowData.remark;
			} 



		}
	};
</script>
