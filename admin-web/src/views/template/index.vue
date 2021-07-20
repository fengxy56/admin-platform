<style>
</style>
<template>
	<div>
		<UploadComponent projectId="chaixuhong" typeId="test" :defaultList="this.defaultList" @return-value="getVal" />
	</div>
</template>
<script>
	import UploadComponent from '../../components/uploadComponent/index'
	export default {
		name: "Tempalate",
		components: {
			UploadComponent
		},
		data() {
			return {
				defaultList: [{
					name: "c9fc4253797441e3be7370f953ac445f",
					url: `${this.$api.getFileUrl}?fileType=img&imgType=1&fileId=c9fc4253797441e3be7370f953ac445f`
				}]
			}
		},
		methods: {
			getVal(result) {
				console.log(result)
			}
		}
	};
</script>
