<style scoped>
.search {
  width: 200px;
  float: right;
}
.commons {
  line-height: 1;
}
.powerInput {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 5px;
}
.powerInput > div {
  margin-left: 10px;
}

	.userinput>Form{
		width: 100%;
		padding: 0;
	}

</style>



<template>
  <div class="commons">


    <div class="powerInput">
      <div> 
          是否待办:
              <Select v-model="queryParams.undoStatus" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="0">待办</Option>
                <Option :value="1">已办</Option>
                <Option :value="2">通知</Option>
              </Select>

      </div>
      <div> 
          是否删除:
              <Select v-model="queryParams.deleteBoolean" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="0">未删除</Option>
                <Option :value="1">已删除</Option>
              </Select>

      </div>
            <div>
        开始时间：
         <DatePicker v-model="queryParams.startDate" @on-change="changeStartTime"  format="yyyy-MM-dd" type="date"  placeholder="请选择开始时间" style="width: 100px"></DatePicker>
      </div>
      <div>
        结束时间：
        <DatePicker v-model="queryParams.endDate" @on-change="changeEndTime"  format="yyyy-MM-dd" type="date"  placeholder="请选择结束时间" style="width: 100px"></DatePicker>
      </div>
    </div>
    <div class="powerInput" style="margin-top:20px;">
      <div>
        账户：
        <Input v-model="queryParams.account" placeholder="账户" style="width: 1.5rem" />
      </div>
      <div>
        轻应用appid：
        <Input v-model="queryParams.appId" placeholder="轻应用appid" style="width: 1.5rem" />
      </div>
      <div>
        标题：
        <Input v-model="queryParams.content" placeholder="标题" style="width: 1.5rem" />
      </div>
      <div>
        消息id：
        <Input v-model="queryParams.msgId" placeholder="消息id" style="width: 1.5rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div> 
      <div>
        <Button type="error" @click="bathEdit">批量编辑</Button>
      </div>             
    </div>

    <Table
      stripe
      border
      :height="this.windowHeight - 200"
      :columns="columns"
      :data="data"
      ref="tables"
      size="small"
      @on-selection-change="getSelectionList"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>
    <Page
      :current="this.currentPage"
      :total="totals"
      show-sizer
      :style="{padding:'15px'}"
      :page-size="sizes"
      :page-size-opts="[10, 20, 50, 100]"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />
    <Modal v-model="modal" title="鸿信待办" width="800" footer-hide :mask-closable="false">
      <HxUpdateUndoComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData"
      />
    </Modal>

    <Modal
        v-model="modal1"
        title="批量编辑"
        @on-ok="bathSure"
        @on-cancel="cancel">

     <Form ref="formData"  :label-width="80" class="dict_form">

			<Row type="flex" justify="space-around" class="code-row-bg">
							
				<Col span="12">
					<FormItem prop="undoStatus" label="审批类型">
						<RadioGroup v-model="batchUpdateParams.batchUndoStatus">
							<Radio :label="0">待办</Radio>
							<Radio :label="1">已办</Radio>
              <Radio :label="2">通知</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="deleteStatus" label="是否删除">
						<RadioGroup v-model="batchUpdateParams.batchDeleteStatus">
							<Radio :label="0">否</Radio>
							<Radio :label="1">是</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
			</Row>

		</Form>
    </Modal>

  </div>
</template>
<script>
import HxUpdateUndoComponent from "../../../components/hx/undo/updateUndo";


export default {
  name: "HxUndoList",
  components: {
    HxUpdateUndoComponent,
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      modal1:false,
      loading: false,
      queryParams: {
        startDate:"2017-01-01 00:00:00",
        endDate:new Date(),
        pageNo: "",
        pageSize: "",
        appId: "",
        account: "",
        content: "",
        msgId: "",
        todoStatus:"",
        deleteStatus:"",

        deleteBoolean:0,
				undoStatus:0,
			},
      columns: [ 
         {
           type: 'selection',
           width: 60,
           align: 'center' 
          },
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "主键id",
          width: 100,
          key: "id",
          tooltip: true,
        },
        {
          title: "消息id",
          width: 200,
          key: "msgId",
          tooltip: true,
        },
        {
          title: "账户",
          width: 100,
          key: "account",
          tooltip: true,
         
        },
        {
          title: "标题",
          width: 200,
          key: "content",
          tooltip: true,
        },
        {
          title: "轻应用id",
          width: 120,
          key: "appId",
          tooltip: true,
        },
        {
          title: "是否删除",
          width: 120,
          key: "delete",
          tooltip: true,
                    render: (h, params) => {
            if (params.row.delete === false) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "未删除"
              );
            } else if (params.row.delete === true) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "已删除"
              );
            }
          }
        },
        {
          title: "待办已办状态",
          width: 120,
          key: "todoStatus",
          tooltip: true,
           render: (h, params) => {
            if (params.row.todoStatus === "undo") {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "待办"
              );
            } else if (params.row.todoStatus === "done") {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "已办"
              );
            }else if (params.row.todoStatus === "notify") {
              return h(
                "Tag",
                {
                  props: {
                    color: "warning",
                    type: "border"
                  }
                },
                "通知"
              );
            }
          }
        },
        {
          title: "创建时间",
          width: 100,
          key: "createTime",
          tooltip: true,
        },
        {
          title: "更新时间",
          width: 100,
          key: "updateTime",
          tooltip: true,
        },
        {
          title: "操作",
          width: 100,
          align: "center",
          fixed: "right",
          render: (h, params) => {

            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "info",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.update(params);
                    }
                  }
                },
                "编辑"
              )
            ]);
            
    
          }
        }
      ],
      rowData:{},
      appId: "",
      appName: "",  
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: [],
      batchUpdateParams:{
        batchList:[],
        delete:false,
        todoStatus:"",
        batchDeleteStatus:0,
        batchUndoStatus: 0,
      },

    };
  },
  methods: {
    getHxUserUndoStatusList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;

      if(this.queryParams.deleteBoolean === 0){//是否删除，0否，1是
        this.queryParams.deleteStatus = "false";
      }else if(this.queryParams.deleteBoolean === 1){//是否删除，0否，1是
        this.queryParams.deleteStatus = "true";
      }else if(this.queryParams.deleteBoolean === -1){//是否删除，0否，1是
        this.queryParams.deleteStatus = "";
      }

      if(this.queryParams.undoStatus === 0){//是否待办已办，0待办，1已办
        this.queryParams.todoStatus = "undo";
      }else if(this.queryParams.undoStatus === 1){//是否待办已办，0待办，1已办
        this.queryParams.todoStatus = "done";
      }else if(this.queryParams.undoStatus === 2){//通知
        this.queryParams.todoStatus = "notify";
      }else if(this.queryParams.undoStatus === -1){//全部
        this.queryParams.todoStatus = "";
      }
            
      this.loading = true;
      const result = await this.$api.getHxUserUndoStatusList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHxUserUndoStatusList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxUserUndoStatusList(this.currentPage);
    },
    query() {
      if(this.queryParams.status === -1){
				this.queryParams.status = "";
      }
      if(this.queryParams.isTop === -1){
				this.queryParams.isTop = "";
      }
      if(this.queryParams.appType === -1){
				this.queryParams.appType = "";
      }
      this.currentPage=1;
      this.getHxUserUndoStatusList(this.currentPage);
    },
    openPowerComponent(e) {
      this.roleId = e.row.bRoleId;
      this.modal = true;
    },
    //修改-编辑
    update(node) {
      this.rowData = node.row;
      this.rowData.actionType = "update";
      this.modal = true;
    },
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getHxUserUndoStatusList(this.currentPage);
    },
			changeStartTime(date) {
				console.log("changeStartTime:"+date);
				if(typeof(date) != "undefined"){
					this.queryParams.startDate=date+" 00:00:00";
				}else{
					this.queryParams.startDate="2017-01-01 00:00:00";
				}
				
			},
			changeEndTime(date) {
				console.log("changeEndTime:"+date);
				if(typeof(date) != "undefined"){
					this.queryParams.endDate = date+" 23:59:59";
				}else{
					this.queryParams.endDate = new Date();
				}
				
      },
      getSelectionList(arr){
        this.batchUpdateParams.batchList = arr;
  
      },
      bathEdit(){
        if(this.batchUpdateParams.batchList.length==0){
          this.$Message.info('请先选择数据');
        }else if(this.queryParams.undoStatus == -1){
          this.$Message.info('请先选择待办已办通知类型');
        }else{
          this.batchUpdateParams.batchUndoStatus = this.queryParams.undoStatus;
          this.modal1=true;
        }

      },        
      bathSure : async function(pages) {

          if(this.batchUpdateParams.batchDeleteStatus == 0){//是否删除，0否，1是
            this.batchUpdateParams.delete = false;
          }else if(this.batchUpdateParams.batchDeleteStatus == 1){//是否删除，0否，1是
            this.batchUpdateParams.delete = true;
          }

          if(this.batchUpdateParams.batchUndoStatus == 0){//是否待办已办，0待办，1已办
            this.batchUpdateParams.todoStatus = "undo";
          }else if(this.batchUpdateParams.batchUndoStatus == 1){//是否待办已办，0待办，1已办
            this.batchUpdateParams.todoStatus = "done";
          }else if(this.batchUpdateParams.batchUndoStatus == 2){//是否待办已办，0待办，1已办
            this.batchUpdateParams.todoStatus = "notify";
          }


          this.loading = true;
          const result = await this.$api.batchUpdateHxUserUndoStatus(this.batchUpdateParams);
          if (result.code === 1) {
            this.currentPage=1;
            this.getHxUserUndoStatusList(this.currentPage);
            this.$Message.success("修改成功!");
            this.loading = false;
          }else{
            console.log(result.errMsg);
            this.$Message.error(result.errMsg);
            this.loading = false;
          }


      },
      cancel () {
        //this.$Message.info('取消');
      }
      
  },
  mounted() {
    //this.getHxUserUndoStatusList(this.currentPage);
  }
};
</script>
