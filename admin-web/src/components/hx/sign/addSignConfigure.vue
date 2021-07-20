<style>

.commons{
    height: 100%;
}

.dict_form {
  padding: 8px 20px 0 0;
  width: 100%;
}

.amap-page-container {
    position: relative;
    margin-top: 16px;
    height: 320px;
    background-color: #f2f2f2;
}

.search-input{
    border: 1px solid red;
    position: absolute;
    z-index: 5;
    width: 80%;
    left: 10%;
    padding: 5px;
}

.toolbar{
    margin-top: 15px;
}

.popup-body-left .addr-name {
    margin-top: 4px;
}

.popup-body-left,
.popup-body-right {
  width: 50%;
  float: left;
  height: 400px;
  position: relative;
}
.popup-body-right {
  border-left: 1px solid #D8D8D8;
  overflow: hidden;
  padding-left: 20px;
}


.addr-search-bar .search-input-contnet input {
    height: 20px;
    border-radius: 10px;
    padding: 0 30px 0 10px;
    width: 100%;
}
.addr-search-bar {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    padding: 5px 0;
    background: rgba(234,239,243,.8);
    z-index: 200;
}

.addr-search-bar .search-input-contnet {
    margin: 0 34px 0 12px;
}



.map-btn a {
    cursor: pointer;
    position: relative;
    z-index: 10;
    display: block;
    margin: 3px auto;
    width: 200px;
    height: 30px;
    background-color: #44bbfc;
    font-size: 14px;
    line-height: 30px;
    border-radius: 30px;
    text-align: center;
    color: #fff;
    text-decoration: none;
}

</style>
<template>
<div class="commons clearfix">

    <div class="popup-body-left">

            <label data-v-eaeb723c="">当前签到点地址：</label>
            <p><small>{{formData.address}}</small></p>

            <div class="amap-page-container" >

            <div class="addr-search-bar" style="">
                <div class="search-input-contnet" v-if="searchVisible" >
                    <input id="search" placeholder="输入地址搜索"> 
                    <i class="delete-addr" style=""></i>
                </div> 
                <a class="search-addr"></a>
            </div>

            <el-amap class="amap-demo"  vid="amap"  :zoom="zoom" :center="center" :amapManager="amapManager" :events="events"  >

            <el-amap-marker :center="center" :position="center" :draggable="draggable"  :events="markerEvents"></el-amap-marker>

            <!-- <el-amap-marker v-for="(marker, index) in markers" :center="center" :position="center" :draggable="true" :events="marker.events" :key="index"></el-amap-marker> -->

                <el-amap-circle
                    vid="circle"
                    :position="center"
                    :center="center"
                    :radius="radius"
                    :draggable="true"
                    :showMarker:="true"
                    fill-opacity="0.2"
                    strokeColor="#38f"
                    strokeOpacity="0.8"
                    strokeWeight="1"
                    fillColor="#38f"
                >
                
                </el-amap-circle>
            </el-amap>

        <div class="map-btn">
            <a class="btn btn-primary" v-if="resetAttPlaceVisible" @click="resetAttPlace">点击重新选择签到点地址</a> 
            <a name="save-att-place" class="btn btn-primary" v-if="saveAttPlaceVisible"  @click="saveAttPlace">保存签到点地址</a></div>
        </div>
    </div>


    <div class="popup-body-right">
  
 		<Form ref="formData" :model="formData"  label-position="right" :rules="ruleInline" :label-width="100" class="dict_form">
 
             <FormItem prop="companyName" label="公司名称：" style="font-size: 10px;">
                    <Input type="text" v-model="formData.companyName" placeholder="公司名称"/>
                </FormItem> 
            <FormItem prop="projectName" label="项目名称：">
                <Input type="text" v-model="formData.projectName" placeholder="项目名称" />
            </FormItem>
            <FormItem prop="alias" label="办公地点：">
                <Input type="text" v-model="formData.alias" placeholder="办公地点别名" />
            </FormItem>
            <FormItem prop="positionName" label="签到名称：">
                <Input type="text" v-model="formData.positionName" placeholder="签到名称" />
            </FormItem>
            <FormItem prop="radius" label="签到点范围：">
                <Input type="text" v-model="radius" placeholder="签到名称" />
            </FormItem>
        </Form>


      <div>
        <Button type="primary" @click="save('formData')" style="float:right;">保存签到点</Button>
      </div>
    </div>

</div>
</template>

<script>
import {AMapManager} from "vue-amap"
let amapManager=new AMapManager();
export default {
    props: {
        rowData: Object
    },
    data() {
            let vm = this;
            return {
                formData: {
                    id:"",
                    alias: "",
                    positionName: "",
                    companyName: "",
                    projectName: "",
                    address:"",
                    longitude:"",
                    latitude:"",
                    offset:"",
                },
                zoom:16,
                // center: [116.339801,39.790082],118.087303,latitude:  39.55946
                center: [118.071736,39.569861],
                result:[],   
                radius:200,
                toSearch:false,
                searchVisible:false,
                resetAttPlaceVisible:true,
                saveAttPlaceVisible:false,
                amapManager,
                draggable:false,
                map:null,
                actionType:"",
                markerEvents: {
                    click: () => {
                            alert('click marker');
                    },
                    dragend: (e) => {
                        console.log(e);
                        console.log('---event---: dragend');
                        this.center = [e.lnglat.lng, e.lnglat.lat];
                        //this.startDrag();// 调用逆解码函数
                        this.getAddress(this.center);
                    }
                },
                events:{
                    init(o){
                        vm.map=o;
                    }
                },
                ruleInline: {
 
                    companyName: [
                    {
                        required: true,
                        message: "公司名称不能为空",
                        trigger: "blur"
                    }
                    ],
                    projectName: [
                    {
                        required: true,
                        message: "项目名称不能为空",
                        trigger: "blur"
                    }
                    ],
                    positionName: [
                    {
                        required: true,
                        message: "签到点名称不能为空",
                        trigger: "blur"
                    }
                    ],
                    alias: [
                    {
                        required: true,
                        message: "别名不能为空",
                        trigger: "blur"
                    }
                    ]
                },

            };
        },
        watch:{
            map:function(){
                if(this.map!=null && this.actionType == "add"){
                    this.startDrag();
                }
            }
        },
        methods:{

            startDrag(){//方法二
                let vm=this;
                let map=this.amapManager.getMap();
                AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
                    var positionPicker = new PositionPicker({
                        mode: 'dragMap',
                        map: map
                    });
                    //定位
                    let geolocation;
                    map.plugin('AMap.Geolocation', function () {
                        geolocation=new AMap.Geolocation({
                            showButton: true, //显示定位按钮，默认：true
                            showMarker: true, //定位成功后在定位到的位置显示点标记，默认：true
                            extensions:'all',
                            // 设置是否可以拖拽
                            draggable: true,
                            // cursor: 'move',
                            // // 设置拖拽效果
                            // raiseOnDrag: true,
                        })
                        map.addControl(geolocation);
                        geolocation.getCurrentPosition();
                        AMap.event.addListener(geolocation, 'complete', function(data){
                            positionPicker.start();
                        });//返回定位信息
                    })
                    positionPicker.on('success', function(positionResult){
                        console.log("this.actionType:"+vm.actionType );
                        vm.result=positionResult.regeocode.pois;
                        vm.formData.address=positionResult.regeocode.formattedAddress;
                        vm.center=[positionResult.position.lng,positionResult.position.lat];

                    })
                    positionPicker.on('fail', function(failResult){
                        console.log(failResult)
                    })
                    // positionPicker.start();
                })
            },
            //逆解码函数
			getAddress: function(lnglat) {
				let self=this
				AMap.plugin('AMap.Geocoder', function() {
					var geocoder = new AMap.Geocoder({
						// city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
						city: '010'
					})
					geocoder.getAddress(lnglat, function(status, result) {
                        console.log(result);
						if (status === 'complete' && result.info === 'OK') {
							self.formData.address = result.regeocode.formattedAddress
							// result为对应的地理位置详细信息
						}
					})
				})
			},
            closeModal() {
                this.$emit("funclose"); //关闭父级modal
            },
            startSearch() {
                let vm=this;
                let map=this.amapManager.getMap();
                this.toSearch=true;
                AMapUI.loadUI(['misc/PoiPicker'], function(PoiPicker) {
                    var poiPicker = new PoiPicker({
                    input: 'search', //输入框id
                });
                //监听poi选中信息
                poiPicker.on('poiPicked', function(poiResult) {
                    console.log(poiResult);
                    //用户选中的poi点信息
                    vm.center=[poiResult.item.location.lng,poiResult.item.location.lat];
                    vm.getAddress(vm.center);
                });
                });
            },
            resetAttPlace(){
                this.draggable = true;
                this.searchVisible = true;
                this.resetAttPlaceVisible = false;
                this.saveAttPlaceVisible = true;
                this.startSearch();
            },
            saveAttPlace(){
                this.draggable = false;
                this.searchVisible = false;
                this.resetAttPlaceVisible = true;
                this.saveAttPlaceVisible = false;
            },
            async save(form) {
                console.log(this.formData);
                                    
                this.$refs[form].validate(async valid => {
                    if (valid) {

                        this.formData.longitude=this.center[0] ;
                        this.formData.latitude=this.center[1];
                        this.formData.offset=this.radius;


                        console.log(this.formData);

                        const result = await this.$api.saveOrUpdateHxSignTrackConfigure(this.formData);

                        console.log(result);

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
        },  
        mounted() {

            
            console.log(this.rowData);
            if(this.rowData.actionType === "update"){
                //经纬度
                this.formData.id = this.rowData.id;
        
                this.formData.address = this.rowData.address;
                this.formData.alias = this.rowData.alias;
                this.formData.companyName = this.rowData.companyName;
                this.formData.projectName = this.rowData.projectName;
                this.formData.positionName = this.rowData.positionName;

                this.radius=this.rowData.offset;

                this.actionType=this.rowData.actionType;
                this.center = [this.rowData.longitude,this.rowData.latitude];
                
                console.log(this.center);
            }else if(this.rowData.actionType === "add"){
                this.resetAttPlace();
                this.getAddress(this.center);
            }


            
        }
};
</script>