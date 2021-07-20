<template>
	<div>
		<div class="btn">
			<Button type="warning" icon="md-create" size="small" v-if="projectShow" @click="projectShow =false">修改</Button>
			<Button type="primary" icon="ios-clipboard-outline" v-if="projectShow == false" @click="add" size="small">保存</Button>
			<Button type="primary" icon="md-close" size="small" v-if="projectShow == false" @click="projectShow =true">取消</Button>
		</div>
		<div>
			<HomeUploadImgComponent 
				projectId="lunbotu" 
				v-if="detailRequestFlag"
				:showborder="borderstyle"
				:disabled="projectShow"
				:defaultList="posterList"
				:activeshow ="activeshow"
				@remove-value="removedata"
				@showdata ="showdata" 
				@return-value="getVal"/>
		
			<div v-if="hxPollImageList !=''">
				<Row :gutter="16" style="margin-top:20px;">
					<Col span="6">
						<span class="el-form-item__label">显示名称</span>
						<Input v-model="showhouse.showDescribe"  :disabled='projectShow' style="width: 2.2rem"></Input>
					</Col>
					<Col span="6">
						<span class="el-form-item__label">排序</span>
						<Input v-model="showhouse.showOrder"  :disabled="projectShow" style="width: 2.2rem"></Input>
					</Col>
					<Col span="12">
						<span class="el-form-item__label">点击跳转页面</span>
						<Input v-model="showhouse.goToUrl"  :disabled="projectShow" style="width: 4.6rem"></Input>
					</Col>
				</Row>
			</div>
		</div>
	</div>
</template>
<script>
import HomeUploadImgComponent from '../../../components/hx/homePage/uploadImg'
	export default {
		name: "HxHomePage",
		components: {
			HomeUploadImgComponent
		},
		data() {
			return {
				activeshow:null,
				borderstyle:'1',
				projectShow:true,
				detailRequestFlag:0,
				posterList:[],
				deletedHxPollImageList:[],
				hxPollImageList:[],
				showhouse:{
					showDescribe:"",
					showOrder:"",
					goToUrl:"",
				}
			}
		},
		methods: {
			//获取轮播图数据
			async getHxPollImageList(){
				const projectList = await this.$api.getHxPollImageList();
				projectList.data.forEach((val,index) =>{
					let obj = {};
					obj.url = val.imageUrlPath;
					obj.id = val.id;
					this.posterList.push(obj)
				})
				if(projectList.data != ''){
					this.showhouse = projectList.data[0]
					this.hxPollImageList = projectList.data;
				}else{
					this.showhouse = {}
				}
				
				this.detailRequestFlag = 1;
				this.loading = false;
			},
			//保存轮播图
			async saveOrUpdateHxPollImage(){

				const data = {
					deletedHxPollImageList:this.deletedHxPollImageList,
					hxPollImageList:this.hxPollImageList
				};
				console.log(data);

				const saveList = await this.$api.saveOrUpdateHxPollImage(data)

				if(saveList.errMsg == 'success'){
					this.$Message['success']({
						background: true,
						content: '保存成功'
					});
					this.detailRequestFlag = 0;
					this.posterList = [];
					this.getHxPollImageList()
				}else{
					this.$Message['error']({
						background: true,
						content: saveList.errMsg
					});
				}
			},
			removedata(data){
				this.hxPollImageList.map((item,index) =>{
					if(this.hxPollImageList.length !=0 && this.hxPollImageList.length-1 == index){
						this.showhouse = this.hxPollImageList[0]
					}
					if(item.id == data.id){
						console.log(this.hxPollImageList)
						this.hxPollImageList.splice(index,1)
					}
				})
				this.activeshow = 0;
				this.deletedHxPollImageList.push(data.id)
			},
			showdata(data){
				console.log("length:"+this.hxPollImageList.length+":"+data);
				if(this.hxPollImageList.length == data && this.hxPollImageList !=''){

					this.hxPollImageList.map((item,index) =>{
						if(index == this.hxPollImageList.length-2){
							console.log(index);
							console.log(this.showhouse);
							item = Object.assign(item,this.showhouse)
						}
					})
		
					this.showhouse = {};
					this.formatLoading = false;
					let _that = this;
					setTimeout(function(){
						_that.formatLoading = false;
					},500)
				}else if(this.hxPollImageList == ''){
					this.showhouse = {};
					this.formatLoading = false;
				}else{
					this.showhouse = this.hxPollImageList[data];
					let _that = this;
					setTimeout(function(){
						_that.formatLoading = false;
					},500)
				}
			},
			getVal(result) {
				if(result.length != this.hxPollImageList.length){
					let arr = {};
					result.forEach((item,index) =>{
						arr.type=1;
						arr.imageUrlPath = item.url;
					})
					this.hxPollImageList.push(arr)
				
					this.showdata(result.length);
					this.activeshow = result.length-1;
					if(this.hxPollImageList.length == 1){
						this.showdata(0)
					}
				}
			},
			add(){
				this.projectShow = true;
				this.saveOrUpdateHxPollImage();
				this.deletedHxPollImageList = [];
			}
		},
		mounted() {
			
			this.getHxPollImageList()
		},
	};
</script>
<style>
.btn{
  width:100%;
  text-align: right;
}
.btn>button{
  margin:0 10px;
}
.el-form-item__label{
  display: inline-block;
  padding:0 5px;
  text-align: left;
}
.el-form-item__label:before {
    content: "*";
    color: red;
}
</style>