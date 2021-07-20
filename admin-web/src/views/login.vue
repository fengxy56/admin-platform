<template>
  <div >
 		<Form ref="formData" :model="formData" :rules="ruleInline" :label-width="100" class="dict_form">
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="8">
				<FormItem prop="account" label="账户">
					<Input type="text" v-model="formData.account" placeholder="请输入账户"  />
				</FormItem>
				</Col>				

			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="8">
				<FormItem prop="password" label="密码">
					<Input type="text"  v-model="formData.password" placeholder="请输入密码" />
				</FormItem>
				</Col>
			</Row>

      <Row type="flex" justify="space-around" class="code-row-bg" style="width: 1000px;margin: 0 auto;">
				<Col span="8">
          <Button type="success" @click="dictSubmit('formData')" style="float:left;">登录</Button>
				</Col>
			</Row>

		</Form>

  </div>
</template>
<script>
export default {
  name: "Login",
		data() {
			return {
				groupFlag: false,
				formData: {
					account: "fengxiaoyang",
					password: "123123",
				},
				ruleInline: {
					account: [{
						required: true,
						message: "账户不能为空",
						trigger: "blur"
					}],
					password: [{
						required: true,
						message: "密码不能为空",
						trigger: "blur"
					}]
				}
			};
		},
		methods: {
			async dictSubmit(form) {
        
        		console.log(form);
                    
				this.$refs[form].validate(async valid => {
					if (valid) {
						console.log(this.formData);

						const result = await this.$api.login(this.formData);

						console.log(result);
						if (result.code === 1) {
							this.$Message.success("保存成功!");
							this.loading = false;
							this.$emit("func"); //关闭父级modal
							 
							window.location.href = "/";
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
    console.log("222");
  }
}
</script>
