<style>
.dict_form {
  padding: 8px 20px 0 0;
  /* width: 100%; */
}
</style>
<template>
	<div>
		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="100" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="businessSystemName" label="公司名称">
					<Input type="text" v-model="formData.businessSystemName" placeholder="请输入公司名称" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="businessSystemCode" label="公司编码">
					<Input type="text" v-model="formData.businessSystemCode" placeholder="请输入公司编码" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				
				<Col span="8">
				<FormItem prop="adminAccount" label="管理员账户">
					<Input type="text" v-model="formData.adminAccount" placeholder="请输入公司管理员账户" />
				</FormItem>
				</Col>
				<Col span="8">
				<FormItem prop="status" label="状态">
					<RadioGroup v-model="formData.status">
						<Radio :label="1">启用</Radio>
						<Radio :label="0">禁用</Radio>
					</RadioGroup>
				</FormItem>
				</Col>
				<Col span="8">
				<FormItem prop="showOrder" label="排序">
					<InputNumber :max="10000" :min="1" v-model="formData.showOrder"></InputNumber>
				</FormItem>
				</Col>

			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="24">
				<FormItem prop="remark" label="备注">
					<Input type="textarea" v-model="formData.remark" placeholder="请输入备注" />
				</FormItem>
				</Col>
			</Row>
		</Form>
		<div slot="footer" class="clearfix">
			<Button type="success" @click="dictSubmit('formData')" style="float:right;">保存</Button>
			<Button type="info" @click="closeModal" style="float:right;margin-right: 10px;">取消</Button>
		</div>
	</div>
</template>
<script>
	export default {
		name: "AddBusinessSystemComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				groupFlag: false,
				formData: {
					id: "",
					businessSystemName: "",
					businessSystemCode: "",
					adminAccount: "", 
					status: 1,
					showOrder: 1,
					remark: "",
				
				},
				ruleInline: {
					businessSystemName: [{
						required: true,
						message: "公司名称不能为空",
						trigger: "blur"
					}],
					businessSystemCode: [{
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

						const result = await this.$api.saveOrUpdateBusinessSystem(this.formData);

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
			}
		},
		mounted() {

			console.log(this.rowData);

			if (this.rowData.actionType === "add") {
				//新增
				this.groupFlag = true;
			} else if (this.rowData.actionType === "update") {

				this.formData.id = this.rowData.id;
				this.formData.businessSystemName = this.rowData.businessSystemName;
				this.formData.businessSystemCode = this.rowData.businessSystemCode;
				this.formData.adminAccount = this.rowData.adminAccount;

				this.formData.status = this.rowData.status;
				this.formData.showOrder = this.rowData.showOrder;
				this.formData.remark = this.rowData.remark;

			} 

		}
	};
</script>
