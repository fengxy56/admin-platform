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
					<FormItem prop="id" label="系统id">
						<Input disabled type="text" v-model="formData.id" />
					</FormItem>
				</Col>	
				<Col span="12">
					<FormItem prop="status" label="系统状态">
						<RadioGroup v-model="formData.status">
							<Radio :label="1">启用</Radio>
							<Radio :label="0">禁用</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
			</Row>	
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="smsKey" label="系统编码">
						<Input type="text" v-model="formData.smsKey" placeholder="请输入系统编码" />
					</FormItem>
				</Col>				
				<Col span="12">
					<FormItem prop="sysName" label="系统名称">
						<Input type="text" v-model="formData.sysName"  placeholder="请输入系统名称" />
					</FormItem>
				</Col>
			</Row>	
			<Row type="flex" justify="space-around" class="code-row-bg">

				<Col span="12">
					<FormItem prop="smsPush" label="短信状态">
						<RadioGroup v-model="formData.smsPush">
							<Radio :label="1">启用</Radio>
							<Radio :label="0">禁用</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="smsDictId" label="短信系统">
						<Select style="width:150px;" v-model="formData.smsDictId" :label-in-value="true"  @on-change="changeSmsDictCode">
							<Option  v-for="item in smsSysDictList" :value="item.id" :key="item.id">{{ item.dictName }}</Option>
						</Select>
					</FormItem>
				</Col>	
			</Row>			
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="hxPush" label="鸿信发送状态">
						<RadioGroup v-model="formData.hxPush">
							<Radio :label="1">启用</Radio>
							<Radio :label="0">禁用</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="hxAppid" label="鸿信轻应用">
						<Select style="width:150px;" v-model="formData.hxNotifyAppDictId" :label-in-value="true"  @on-change="changeHxNotifyDictCode">
							<Option  v-for="item in hxNotifyAppList" :value="item.id" :key="item.id">{{ item.dictName }}</Option>
						</Select>
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
		name: "smsComponent",
		props: {
			rowData: Object,
			canEdit: Boolean
		},
		data() {
			return {
				groupFlag: false,
				smsSysDictList:[],
				hxNotifyAppList:[],
				formData: {
					id:"",
					smsKey: "",
					sysName: "",
					smsPush: 1,
					hxPush: 1,
					status: 1,
					smsDictId: "",
					hxNotifyAppDictId: "",
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
			changeSmsDictCode(dictCodeData) {  
				console.log(dictCodeData);
				this.formData.smsDictId = dictCodeData.value;
					console.log(dictCodeData);
			},
			changeHxNotifyDictCode(dictCodeData) {  
				console.log(dictCodeData);
				this.formData.hxNotifyAppDictId = dictCodeData.value;
			},
			async dictSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);
						await this.$api.saveOrUpdateSmsSysInfo(this.formData);
						this.$emit("func"); //关闭父级modal
					}
				});
			},
			closeModal() {
				this.$emit("funclose"); //关闭父级modal
			},			
			//获取鸿信轻应用类型
			async getSmsSysDictList() {
				const param = {
					parentId: this.$dictCode.smsSysDict.id
				};
				const result = await this.$api.getDictListByParentId(param);
				
				if (result.code === 1) {
					this.smsSysDictList = result.data;
				}
			},//获取鸿信轻应用类型
			async getHxNotifyAppList() {
				const param = {
					parentId: this.$dictCode.hxNotifyApp.id
				};
				const result = await this.$api.getDictListByParentId(param);

				if (result.code === 1) {
					this.hxNotifyAppList = result.data;
				}

			},

		},
		mounted() {

			//获取短信平台
			this.getSmsSysDictList();
			//获取鸿信通知
			this.getHxNotifyAppList();


			console.log(this.rowData);

			if (this.rowData.actionType === "add") {

				//新增
				this.groupFlag = true;

			} else if (this.rowData.actionType === "update") {

				//修改
				this.formData.id = this.rowData.id;
				this.formData.smsKey = this.rowData.smsKey;
				this.formData.sysName = this.rowData.sysName;

				this.formData.smsPush = this.rowData.smsPush;
				this.formData.hxPush = this.rowData.hxPush;
				this.formData.smsDictId = this.rowData.smsDictId;
				this.formData.hxNotifyAppDictId = this.rowData.hxNotifyAppDictId;
				this.formData.status = this.rowData.status;

			} 
			
		}
	};
</script>
