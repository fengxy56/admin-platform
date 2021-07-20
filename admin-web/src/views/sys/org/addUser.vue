<style>

.userInfo{
	max-height: 80%;
	height:400px;
	overflow:auto;
}

.dict_form {
  padding: 8px 20px 0 0;
}
/* .el-form-item__label {
	display: inline-block;
	min-width: 78px;
	text-align: left;
} */

.el-form-item__label:before {
	content: "*";
	color: red;
}

</style>
<template>
	<div class="userInfo" >
		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="80" height="300" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="8">
					<FormItem label="账号" prop="account">
						<Input v-model="formData.account" :disabled="isUpate" placeholder="请输入账号" />
					</FormItem>
				</Col>				
				<Col span="8">
					<FormItem prop="password" label="密码">
						<Input type="text" v-model="formData.password" placeholder="请输入密码" />
					</FormItem>
				</Col>				
				<Col span="8">
					<FormItem prop="surePassword" label="确认密码">
						<Input type="text" v-model="formData.surePassword" placeholder="请确认密码" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
								
				<Col span="8">
					<FormItem prop="name" label="姓名">
						<Input v-model="formData.name" placeholder="请输入姓名" />
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="phone" label="手机号">
						<Input type="text" v-model="formData.phone" placeholder="请输入手机号,如：13888888888" />
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem label="生日" prop="birthday" >
						<DatePicker type="date" v-model="formData.birthday" format="yyyy-MM-dd" placeholder="请输入生日" />
						<!-- <DatePicker type="date" v-model="formData.birthday" style="width: 1.55rem" @on-change="handleChange" ></DatePicker> -->
						 <!-- <DatePicker type="date" :value="formData.birthday" @on-change="handleChange" format="yyyy-MM-dd" placeholder="请选择发布日期" style="width: 295px;margin-left: 5px"/> -->

					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
							
				<Col span="8">
					<FormItem prop="gender" label="性别">
						<RadioGroup v-model="formData.gender">
							<Radio :label="0">未知</Radio>
							<Radio :label="1">男</Radio>
							<Radio :label="2">女</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="email" label="邮箱">
						<Input type="text" v-model="formData.email" placeholder="请输入邮箱,feng@126.com" />
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="jobTitle" label="岗位">
						<Input type="text" v-model="formData.jobTitle" placeholder="请输入岗位" />
					</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
								
				<Col span="24">
					<FormItem prop="status" label="是否启用">
						<RadioGroup v-model="formData.status">
							<Radio :label="1">启用</Radio>
							<Radio :label="0">禁用</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
			</Row>			
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="orgId" label="组织id">
						<Input disabled type="text" v-model="formData.orgId"  />
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="orgName" label="组织">
						<Input disabled type="text" v-model="formData.orgName"  />
					</FormItem>
				</Col>
			</Row>			
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="24">
					<FormItem prop="orgLongName" label="组织场长路径">
						<Input disabled type="text" v-model="formData.orgLongName"  />
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

	//import moment from "moment";

	export default {
		name: "AddUserComponent",
		props: {
			rowUserData: Object
		},
		data() {
			return {
				isUpate: false,
				formData: {},
				ruleInline: {
					account: [{
						required: true,
						message: "账户不能空",
						trigger: "blur"
					}],
					name: [{
						required: true,
						message: "姓名不能为空",
						trigger: "blur"
					}],
					birthday:  [
						{   required: true, 
							type: 'date', 
							message: '出生年月不能为空', 
							trigger: 'blur' 
						}
                    ],


				}
			};
		},
		methods: {
			async appSubmit(form) {
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

						if(this.rowUserData.actionType === "add"){						
							const result = await this.$api.saveSysUser(this.formData);
							this.loading = false;
							if (result.code === 1) {
								this.$Message.success("保存成功!");
								this.$emit("func"); //关闭父级modal
							}else{
								console.log(result.errMsg);
								this.$Message.error(result.errMsg);
							}
						}else if(this.rowUserData.actionType === "update"){	
							const result = await this.$api.updateSysUser(this.formData);
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
			},


			handleChange(data){
				console.log("时间改变");
				this.formData.birthday = data
			},

		},
		mounted() {

			console.log(this.rowUserData);
	
			if (this.rowUserData.actionType === "add") {
				this.formData.orgId = this.rowUserData.orgId;
				this.formData.orgName = this.rowUserData.orgName;

				if(this.rowUserData.orgLongName == null || this.rowUserData.orgLongName == ''){
					this.formData.orgLongName = this.rowUserData.orgName;
				}else{
					this.formData.orgLongName = this.rowUserData.orgLongName;
				}

				this.formData.gender = 0;
				this.formData.status = 1;//默认启用
				
			}else if (this.rowUserData.actionType === "update"){

				// console.log(moment(this.rowUserData.birthday).format("yyyy-MM-DD"));
				// this.formData.birthday = moment(this.rowUserData.birthday).format("yyyy-MM-DD")+"";
				// this.formData.birthday = "2020-01-11";
				this.formData = this.rowUserData;
				this.formData.password = '';
				this.isUpate = true;
			}


		}
	};
</script>
