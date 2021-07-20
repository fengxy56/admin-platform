<style>
	.demo-upload-list {
		display: inline-block;
		width: 80px;
		height: 80px;
		text-align: center;
		line-height: 80px;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
		margin-right: 4px;
	}
	.active{
		display: inline-block;
		width: 80px;
		height: 80px;
		text-align: center;
		line-height: 80px;
		border: 3px solid red;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
		margin-right: 4px;
	}
	.demo-upload-list img {
		width: 100%;
		height: 100%;
	}

	.demo-upload-list-cover {
		display: none;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: rgba(0, 0, 0, .6);
	}

	.demo-upload-list:hover .demo-upload-list-cover {
		display: block;
	}

	.demo-upload-list-cover i {
		color: #fff;
		font-size: 30px;
		cursor: pointer;
		margin: 0 2px;
	}
</style>
<template>
	<div>
		<div v-if="showborder == 1" :class="{active:index===isShow}" class="demo-upload-list" v-for="(item,index) in uploadList" @click="switchData(item,index)" :key="index">
			<template v-if="item.status === 'finished'">
				<img :src="item.url">
				<div class="demo-upload-list-cover">
					<Icon type="ios-eye-outline" @click.native="handleView(item.url)"></Icon>
					<Icon v-if="disabled == false" type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
				</div>
			</template>
			<template v-else>
				<Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
			</template>
		</div>
		<div v-else class="demo-upload-list">
			<template v-if="item.status === 'finished'">
				<img :src="item.url">
				<div class="demo-upload-list-cover">
					<Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>
					<Icon v-if="disabled == false" type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
				</div>
			</template>
			<template v-else>
				<Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
			</template>
		</div>
		<Upload v-show="show" :disabled='disabled' ref="upload" :show-upload-list="false" :default-file-list="defaultList" :on-success="handleSuccess"
		 :format="['jpg','jpeg','png','bmp','beic','gif']" :max-size="maxsize" :on-format-error="handleFormatError" :on-exceeded-size="handleMaxSize" :on-error="hanleError"
		 :before-upload="handleBeforeUpload" type="drag" :action="`${this.$api.uploadTsq}`"
		 style="display: inline-block;width:58px;">
			<div style="width: 58px;height:58px;line-height: 58px;">
				<Icon type="ios-add" size="20"></Icon>
			</div>
		</Upload>
		<Modal title="图片预览" v-model="visible">
			<img :src="`${imgUrl}`" v-if="visible" style="width: 100%">
			<div slot="footer">
				<i-button type="error" size="large" @click="visible = false">关闭</i-button>
			</div>
		</Modal>
	</div>
</template>
<script>
	export default {
		name: "HomeUploadImgComponent",
		props: {
			defaultList: Array,
			projectId: String,
			typeId: String,
			maxLengthProp: Number,
			showborder: String,
			disabled:Boolean,
			maxsize:Number,
			activeshow:Number,
		},
		data() {
			return {
				imgUrl: '',
				visible: false,
				uploadList: [],
				maxLength: 10,
				show: true,
				isShow:0,
			}
		},
		methods: {
			handleView(imgUrl) {
				this.imgUrl = imgUrl;
				this.visible = true;
			},
			handleRemove(file) {
				if(this.showborder == 1){
					this.$Modal.confirm({
						title: '确认删除',
						content: "您确认删除，删除后不可恢复",
						onOk: async () => {
							const fileList = this.$refs.upload.fileList;
							this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
							this.$emit('remove-value',file)
							this.$emit("return-value", this.uploadList)
						}
					})
				}else{
					const fileList = this.$refs.upload.fileList;
					this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
					this.$emit('remove-value',file)
					this.$emit("return-value", this.uploadList)
				}
				/**
				 * 调用删除接口
				 */
			},
			handleSuccess(res, file ,fileList) {
				console.log(res.data);
				file.url =
					`${res.data.original}`
				file.name = res.fileId;
				this.$emit("return-value", fileList)
			},
			hanleError(res, file,fileList){
				console.log(res)
				console.log(file)
				console.log(fileList)
			},
			handleFormatError(file) {
				this.$Notice.warning({
					title: '图片格式错误',
					desc: '图片格式只能为jpg或者png.'
				});
			},
			handleMaxSize(file) {
				this.$Notice.warning({
					title: '图片大小错误',
					desc: '图片大小不能超过2M.'
				});
			},
			handleBeforeUpload() {
				let check;
				if (this.maxLengthProp) {
					check = this.uploadList.length < this.maxLengthProp;
				} else {
					check = this.uploadList.length < this.maxLength;
				}
				if (!check) {
					this.$Notice.warning({
						title: `最多只能上传${this.maxLengthProp||this.maxLength}张.`
					});
				}
				return check;
			},
			switchData(data,index){
				this.isShow = index;
				this.$emit("showdata", index)
			},
		},
		mounted() {
			this.uploadList = this.$refs.upload.fileList;
		},
		watch: {
			uploadList() {
				//上传到指定数量，去掉上传按钮
				if (this.maxLengthProp) {
					if (this.uploadList.length === this.maxLengthProp) {
						this.show = false;
					}else{
						this.show = true;
					}
				} else if (this.uploadList.length === this.maxLength) {
					this.show = false
				} else{
					this.show = true;
				}
				
			},
			activeshow(){
				this.isShow = this.activeshow
			}
		}
	}
</script>
