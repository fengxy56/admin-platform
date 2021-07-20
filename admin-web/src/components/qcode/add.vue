<style>
.dict_form {
  padding: 8px 20px 0 0;
  width: 100%;
}
</style>
<template>
	<div>
		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="100" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="id" label="系统id">
						<Input type="text" disabled v-model="formData.id" />
					</FormItem>
				</Col>	
				<Col span="12">
					<FormItem prop="name" label="二维码名称">
						<Input type="text" v-model="formData.name" placeholder="二维码名称" />
					</FormItem>
				</Col>	
			</Row>	
			<!-- <Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="goUrl" label="跳转地址">
						<Input type="text" v-model="formData.goUrl" placeholder="跳转地址" />
					</FormItem>
				</Col>					
				<Col span="12">
					<FormItem prop="type" label="类型">
						<Input type="text" v-model="formData.type" placeholder="类型" />
					</FormItem>
				</Col>	
			</Row>	 -->
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="24">
					<FormItem prop="goUrl" label="跳转地址">
						<Input type="text" v-model="formData.goUrl"  placeholder="请输入跳转地址" />
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
		name: "QcodeComponent",
		props: {
			rowData: Object
		},
		data() {
			return {
				groupFlag: false,
				formData: {
					id:"",
					name:"",
					goUrl: "",
					type: "",
				},
				ruleInline: {
					name: [{
						required: true,
						message: "二维码名称不能为空",
						trigger: "blur"
					}],
					goUrl: [{
						required: true,
						message: "跳转地址不能为空",
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
						const result = await this.$api.saveOrUpdateHxCodeInfo(this.formData);
						
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
				this.formData.name = this.rowData.name;
				this.formData.goUrl = this.rowData.goUrl;
				console.log(this.formData);
			} 

		}
	};
</script>
