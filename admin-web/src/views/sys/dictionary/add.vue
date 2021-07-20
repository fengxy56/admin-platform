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
				<FormItem prop="dictName" label="名称">
					<Input type="text" v-model="formData.dictName" placeholder="请输入名称" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="parentId" label="父级">
					<Input disabled type="text" v-model="formData.parentId" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="dictGroup" label="组">
					<Input type="text" :disabled="groupFlag" v-model="formData.dictGroup" placeholder="请输入组名" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem v-if="this.formData.parentId!=='-1'" prop="dictCode" label="编码">
					<Input type="text" v-model="formData.dictCode" placeholder="请输入编码">
					<span slot="prepend">{{formData.dictGroup}}_</span>
					</Input>
				</FormItem>
				<FormItem v-else prop="dictCode" label="编码">
					<Input type="text" disabled v-model="formData.dictGroup" placeholder="请输入编码" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="ext1" label="扩展1">
					<Input type="text" v-model="formData.ext1" placeholder="请输入扩展1" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="ext2" label="扩展2">
					<Input type="text" v-model="formData.ext2" placeholder="请输入扩展2" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="ext3" label="扩展3">
					<Input type="text" v-model="formData.ext3" placeholder="请输入扩展3" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="ext4" label="扩展4">
					<Input type="text" v-model="formData.ext4" placeholder="请输入扩展4" />
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="ext5" label="扩展5">
					<Input type="text" v-model="formData.ext5" placeholder="请输入扩展5" />
				</FormItem>
				</Col>
				<Col span="12">
				<FormItem prop="status" label="状态">
					<RadioGroup v-model="formData.status">
						<Radio :label="1">启用</Radio>
						<Radio :label="0">禁用</Radio>
					</RadioGroup>
				</FormItem>
				</Col>
			</Row>
			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
				<FormItem prop="remark" label="备注">
					<Input type="textarea" v-model="formData.remark" placeholder="请输入备注" />
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
			<Button type="success" @click="dictSubmit('formData')" style="float:right;">保存</Button>
			<Button type="info" @click="closeModal" style="float:right;margin-right: 10px;">取消</Button>
		</div>
	</div>
</template>
<script>
	export default {
		name: "dictComponet",
		props: {
			rowData: Object
		},
		data() {
			return {
				groupFlag: false,
				formData: {
					dictCode: "",
					dictGroup: "",
					dictName: "",
					parentId: "",
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
					status: 1
				},
				ruleInline: {
					dictName: [{
						required: true,
						message: "名称不能为空",
						trigger: "blur"
					}],
					dictGroup: [{
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
						if (this.formData.parentId === "-1") {
							console.log("父级");
							//父级---组名与编码强制一致
							this.formData.dictCode = this.formData.dictGroup;
						} else {
							this.formData.dictCode = `${this.formData.dictGroup}_${this.formData.dictCode}`;
						}
						const result = await this.$api.saveOrUpdateDictInfo(this.formData);

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
			if (this.rowData.actionType === "add") {
				//新增
				this.formData.dictGroup = this.rowData.dictGroup;
				this.formData.parentId = this.rowData.id;
				this.formData.level = this.rowData.level + 1;
				this.groupFlag = true;
			} else if (this.rowData.actionType === "update") {
				//修改
				if (this.rowData.parentId !== "-1") {
					this.formData.dictCode = this.rowData.dictCode.split(
						this.rowData.dictGroup + "_"
					)[1];
				} else {
					this.formData.dictCode = this.rowData.dictCode;
				}
				this.formData.dictGroup = this.rowData.dictGroup;
				this.formData.dictName = this.rowData.dictName;
				this.formData.parentId = this.rowData.parentId;
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
			} else {
				//新增父级节点
				this.formData.parentId = "-1";
				this.formData.level = 1;
			}
		}
	};
</script>
