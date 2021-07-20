<style>
.dict_form {
  padding: 8px 20px 0 0;
  width: 100%;
}
</style>
<template>
	<div>
		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="80" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<FormItem prop="id" label="系统id">
					<Input type="text" disabled v-model="formData.id" />
				</FormItem>
			</Row>	
			<Row type="flex" justify="space-around" class="code-row-bg">
				<FormItem prop="sysCode" label="系统编码">
					<Input type="text" v-model="formData.sysCode" placeholder="请输入系统编码" />
				</FormItem>
			</Row>	
			<Row type="flex" justify="space-around" class="code-row-bg">
				<FormItem prop="sysName" label="系统名称">
					<Input type="text" v-model="formData.sysName"  placeholder="请输入系统名称" />
				</FormItem>
	
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
		name: "sysInfoComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				groupFlag: false,
				formData: {
					id:"",
					sysCode: "",
					sysName: "",
					status: 1
				},
				ruleInline: {
					sysCode: [{
						required: true,
						message: "系统code不能为空",
						trigger: "blur"
					}],
					sysName: [{
						required: true,
						message: "系统名称不能为空",
						trigger: "blur"
					}]
				}
			};
		},
		methods: {
			async dictSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						this.loading = true;
						const result = await this.$api.saveOrUpdateSysInfo(this.formData);
						
						if (result.code === 1) {
							this.$Message.success("保存成功!");
                            this.loading = false;
                            this.$emit("func"); //关闭父级modal
						}else{
                            this.$Message.error(result.errMsg);
                            this.loading = false;
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
				//修改
				this.formData.id = this.rowData.id;
				this.formData.sysCode = this.rowData.sysCode;
				this.formData.sysName = this.rowData.sysName;
				this.formData.status = this.rowData.status;
			} 

		}
	};
</script>
