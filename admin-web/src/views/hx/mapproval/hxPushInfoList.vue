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
              <Select v-model="queryParams.ifTodo" style="width: 1rem">
                <Option :value="-1">全部</Option>
                <Option :value="1">待办</Option>
                <Option :value="0">已办</Option>
              </Select>
      </div>
      <div>
        应用appid：
        		<Select style="width:150px;" filterable v-model="queryParams.appId" :label-in-value="true"  @on-change="changeHxCustApp">
							<Option  v-for="item in approvalAppData" :value="item.joinAppId" :key="item.id"  >{{ item.joinAppName }}</Option>
						</Select>
      </div>
      <div>
        账户：
        <Input v-model="queryParams.userId" placeholder="账户" style="width: 1.5rem" />
      </div>
      <div>
        标题：
        <Input v-model="queryParams.msgTitle" placeholder="标题" style="width: 1.5rem" />
      </div>
      <div>
        鸿信消息id：
        <Input v-model="queryParams.hxMsgId" placeholder="消息id" style="width: 1.5rem" />
      </div>
      <div>
        业务id：
        <Input v-model="queryParams.requestId" placeholder="消息id" style="width: 1.5rem" />
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
      @on-selection-change="getSelectionList"
      ref="tables"
      size="small"
      :tooltip-theme="menuTheme"
      :loading="loading"
    ></Table>
    <Page
      :current="this.currentPage"
      :total="totals"
      show-sizer
      :style="{padding:'15px'}"
      :page-size="sizes"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />

    <!-- <Modal v-model="modal" title="移动审批" width="800" footer-hide :mask-closable="false">
      <HxUpdateHxPushInfoComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData"
      />
    </Modal> -->

    <Modal v-model="modal" title="移动审批" width="800" footer-hide :mask-closable="false">
      <HxUpdateHxPushInfoComponent
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

     <Form ref="formData"  :label-width="100" class="dict_form">

			<Row type="flex" justify="space-around" class="code-row-bg">
							
				<Col span="12">
					<FormItem prop="ifTodo" label="审批类型">
						<RadioGroup v-model="batchUpdateParams.ifTodo">
              <Radio :label="1">待办</Radio>
              <Radio :label="0">已办</Radio>
				
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="deleteStatus" label="鸿信是否删除">
						<RadioGroup v-model="batchUpdateParams.deleteStatus">
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
import HxUpdateHxPushInfoComponent from "../../../components/hx/mapproval/updateHxPushInfo";


export default {
  name: "HxPushInfoList",
  components: {
    HxUpdateHxPushInfoComponent,
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      modal1 : false,
      loading: false,
      queryParams: {
        pageNo: "",
        pageSize: "",
        appId: "",
        userId: "",
        hxMsgId: "",
        requestId: "",
        msgTitle:"",
        ifTodo: "",
			},
      batchUpdateParams:{
        batchList:[],
        deleteStatus:0,
        ifTodo: 1,
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
          title: "应用appId",
          key: "appId",
          width: 100,
          align: "center",
          tooltip: true,
        },
        {
          title: "应用名称",
          key: "appName",
          width: 100,
          align: "center",
        },
        {
          title: "账号",
          width: 100,
          key: "userId",
          tooltip: true,
        },
        {
          title: "标题",
          width: 100,
          key: "msgTitle",
          tooltip: true,
         
        },
        {
         
          title: "状态",
          key: "ifTodo",
          align: "center",
          width: 110,
          render: (h, params) => {
            if (params.row.ifTodo === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "已办"
              );
            } else if (params.row.ifTodo === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "待办"
              );
            }
          }
        },
        {
          title: "其它编码",
          width: 100,
          key: "processCode",
          tooltip: true,
        },
        {
          title: "访问url",
          width: 150,
          key: "ptUrl",
          tooltip: true,
          render: (h, params) => {
							return h("div", [
								h(
									"a", {
										on: {
											click: () => {
												this.jumpPtUrl(params.row.ptUrl+"&timestamp=hkdcapp");
											}
										}
                  },
                  "查看流程详情"
									//params.row.ptUrl+"&timestamp=hkdcapp"
								)
							]);
          }
        },
        {
          title: "业务id",
          width: 100,
          key: "requestId",
          tooltip: true,
        },
        {
          title: "鸿信消息id",
          width: 100,
          key: "hxMsgid",
          tooltip: true,
        },
        {
          title: "发起人",
          width: 100,
          key: "authorUser",
          tooltip: true,
        },
        {
          title: "是否抄送",
          width: 100,
          key: "isCc",
          tooltip: true,
        },
        {
          title: "待办时间",
          width: 100,
          key: "receiveTime",
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
      approvalAppData:[],
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getHongkunPushinfoList: async function(pages) {
      this.queryParams.pageNo = pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getHongkunPushinfoList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    }, 
    getHongkunOutsupportinfoList: async function(pages) {
 
      this.loading = true;
      const result = await this.$api.getHongkunOutsupportinfoList();
      this.loading = false;
      
      console.log(result.data);
      if (result.code === 1) {
        this.approvalAppData = result.data;
      }
    }, 
    handleSelectAll (status) {
      this.$refs.selection.selectAll(status);
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHongkunPushinfoList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHongkunPushinfoList(this.currentPage);
    },
    query() {

      if(this.queryParams.ifTodo === -1){
				this.queryParams.ifTodo = "";
      }

      this.currentPage=1;
      this.getHongkunPushinfoList(this.currentPage);
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
      this.getHongkunPushinfoList(this.currentPage);
    },
    getSelectionList(arr){
      this.batchUpdateParams.batchList = arr;

    },
    jumpPtUrl(src){
       console.log("src:"+src);
       window.open(src, '_blank');
    },    
    changeHxCustApp(changeHxCustAppData){
        console.log(changeHxCustAppData);
				this.queryParams.appId = changeHxCustAppData.value;
    },
    bathEdit(){
      if(this.batchUpdateParams.batchList.length==0){
        this.$Message.info('请先选择数据');
      }else if(this.queryParams.undoStatus == -1){
        this.$Message.info('请先选择待办已办通知类型');
      }else{
        this.batchUpdateParams.batchIfTodo = this.queryParams.ifTodo;
        this.modal1=true;
      }

    },        
    bathSure : async function(pages) {
           this.loading = true;
          console.log(this.batchUpdateParams);
          const result = await this.$api.batchUpdateHongkunPushinfo(this.batchUpdateParams);
          if (result.code === 1) {
            this.currentPage=1;
            this.getHongkunPushinfoList(this.currentPage);
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
    this.getHongkunOutsupportinfoList();
    //this.getHongkunPushinfoList(this.currentPage);
  }
};
</script>
