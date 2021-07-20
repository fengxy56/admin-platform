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

				<Col span="8">
				<FormItem prop="parentAppId" label="父级appid">
					<Input type="text" v-model="formData.parentAppId" />
				</FormItem>
				</Col>				
				<Col span="12">
				<FormItem prop="parentAppName" label="父级名称">
					<Input type="text" :disabled="groupFlag" v-model="formData.parentAppName" placeholder="请输入父级名称" />
				</FormItem>
				</Col>

			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">

				<Col span="8">
				<FormItem prop="appId" label="应用appid">
					<Input :disabled="groupFlag" type="text" v-model="formData.appId" placeholder="请输入应用appid" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="appName" label="名称">
					<Input type="text" v-model="formData.appName" placeholder="请输入名称" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="8">
				<FormItem prop="ext1" label="跳转地址类型">
					<RadioGroup v-model="formData.appAddressType">
						<Radio :label="1">外部</Radio>
						<Radio :label="2">内部</Radio>							
					</RadioGroup>
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="ext2" label="跳转地址">
					<Input type="text" v-model="formData.appAddress" placeholder="请输入跳转地址" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="8">
				<FormItem prop="appPhotoUrlType" label="应用图片类型">
					<RadioGroup v-model="formData.appPhotoUrlType">
						<Radio :label="1">外部</Radio>
						<Radio :label="2">内部</Radio>							
					</RadioGroup>
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="appPhotoUrl" label="应用图片">
					<Input type="text" v-model="formData.appPhotoUrl" placeholder="请输入应用图片" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="remark" label="状态">
					<RadioGroup v-model="formData.status">
						<Radio :label="0">禁用</Radio>
						<Radio :label="1">有效</Radio>							
					</RadioGroup>
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="showOrder" label="排序">
					<InputNumber :max="10000" :min="1" v-model="formData.showOrder"></InputNumber>
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
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
		name: "addComponet",
		props: {
			rowData: Object,
			queryParams: Object
		},
		groupFlag : false,
		data() {
			return {
				groupFlag: false,
				formData: {
					appId: "",
					appName: "",
					parentAppId: "",
					parentAppName: "",
					appAddress: "",
					appAddressType: "",
					appPhotoUrl: "",
					appPhotoUrlType: "",
					level: 1,
					ext1: "",
					ext2: "",
					ext3: "",
					ext4: "",
					ext5: "",
					ext6: "",
					ext7: "",
					ext8: "",
					remark: "",
					showOrder: 1,
					status: 1,
					actionType:"",
				},
				ruleInline: {
					dictName: [{
						required: true,
						message: "名称不能为空",
						trigger: "blur"
					}],
					parentName: [{
						required: true,
						message: "外链路径不能为空",
						trigger: "blur"
					}]
				}
			};
		},
		methods: {
			async dictSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

						const result = await this.$api.saveOrUpdateHxCustAppMenu(this.formData);

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

			this.formData.actionType = this.rowData.actionType;

			if (this.rowData.actionType === "add") {

				//新增
				this.formData.parentAppId = this.queryParams.appId;
				this.formData.parentAppName = this.queryParams.appName;

				this.formData.level = 1;		
				
			} else if (this.rowData.actionType === "update") {
				//修改
				this.formData.parentAppName = this.rowData.parentAppName;
				this.formData.parentAppId = this.rowData.parentAppId;
				this.formData.appName = this.rowData.appName;
				this.formData.appId = this.rowData.appId;
			    this.formData.appAddressType = this.rowData.appAddressType;
				this.formData.appAddress = this.rowData.appAddress;
			    this.formData.appPhotoUrlType = this.rowData.appPhotoUrlType;
				this.formData.appPhotoUrl = this.rowData.appPhotoUrl;

				this.formData.showOrder = this.rowData.showOrder;
				this.formData.status = this.rowData.status;
				this.formData.id = this.rowData.id;
				this.formData.ext1 = this.rowData.ext1;
				this.formData.ext2 = this.rowData.ext2;
				this.formData.ext3 = this.rowData.ext3;
				this.formData.ext4 = this.rowData.ext4;
				this.formData.ext5 = this.rowData.ext5;
				this.formData.ext6 = this.rowData.ext6;
				this.formData.ext7 = this.rowData.ext7;
				this.formData.ext8 = this.rowData.ext8;
				this.formData.remark = this.rowData.remark;


				this.groupFlag = true;
			}if (this.rowData.actionType === "parent") {

			    this.formData.actionType ="add";
				//新增
				this.formData.parentAppId = this.rowData.appId;
				this.formData.parentAppName = this.rowData.appName;		
				this.formData.level = this.rowData.level+1;		
			}


		}
	};
</script>
