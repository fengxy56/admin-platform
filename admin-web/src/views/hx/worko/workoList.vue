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
</style>
<template>
  <div class="commons">
    <div class="powerInput">
       <div> 
          是否删除:
              <Select v-model="queryParams.isDelete" clearable style="width: 1rem">
                <Option :value="0">否</Option>
                <Option :value="1">是</Option>
              </Select>

      </div>
      <div> 
          是否点问:
              <Select v-model="queryParams.dwType" clearable style="width: 1rem">
                <Option :value="-1">否</Option>
                <Option :value="1">是</Option>
              </Select>

      </div>
      <div>
        发布人:
        <Input v-model="pubUserName" placeholder="请输入发布人" style="width: 100px" />
      </div>
      <div>
        发布人姓名:
        <Input v-model="pubUserTrueName" placeholder="请输入发布人姓名" style="width: 100px" />
      </div>
      <div>
        发布内容:
        <Input v-model="pubWordsContent" placeholder="请输入发布内容" style="width: 100px" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
      <!-- <div>
        <Button type="success" @click="openPowerComponent">数据汇总</Button>
      </div> -->
      <!-- <div>
        <Button type="success" @click="exportExcel">汇总导出</Button>
      </div> -->
    </div>
    <Table
      stripe
      border
      :height="this.windowHeight - 200"
      :columns="columns"
      :data="data"
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
    <!-- <Modal v-model="modal" title="数据汇总" width="900" footer-hide :mask-closable="false">
      <SiteComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :roleId="roleId"
      />
    </Modal> -->

    <Modal
        v-model="modal1"
        title="批量编辑"
        @on-ok="bathSure"
        @on-cancel="cancel">

     <Form ref="formData"  :label-width="80" class="dict_form">

			<Row type="flex" justify="space-around" class="code-row-bg">
							
				<Col span="12">
					<FormItem prop="isTop" label="是否置顶">
						<RadioGroup v-model="updateParams.isTop">
							<Radio :label="0">否</Radio>
							<Radio :label="1">置顶</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isDelete" label="是否删除">
						<RadioGroup v-model="updateParams.isDelete">
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
// import SiteComponent from "../../components/log/site/siteList";
export default {
  name: "PowerList",
  components: {
   // SiteComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      queryParams: {
        isDelete: "",
        dwType: "",
        pubUserName: "",
        pubUserTrueName: "",
        pubWordsContent: "",
        pageNo: 1,
        pageSize: 10,
      },
      modal: false,
      modal1: false,
      loading: false,
      roleId: "",
      updateParams:{
        id:"",
        dataKey:"",
        isDelete:0,
        isTop: 0,
      },
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center",
          tooltip: true,
        },
        {
          title: "主键id",
          width: 100,
          key: "id",
          align: "center",
          tooltip: true,
        },
        {
          title: "数据key",
          width: 100,
          key: "dataKey",
          align: "center",
          tooltip: true,
        },
        {
          title: "发布人账户",
          width: 150,
          key: "pubUserName",
          align: "center",
          tooltip: true,
        },
        {
          title: "发布人姓名",
          width: 150,
          key: "pubUserTrueName",
          align: "center",
          tooltip: true,
        },
        {
          title: "内容",
          width: 300,
          key: "pubWordsContent",
          align: "center",
          tooltip: true,
        },
        {
          title: "图片",
          width: 150,
          key: "pubImages",
          align: "center",
          tooltip: true,
        },
        {
         
          title: "是否置顶",
          key: "isTop",
          align: "center",
          width: 110,
          render: (h, params) => {
            if (params.row.isTop === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "否"
              );
            } else if (params.row.isTop === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "置顶"
              );
            }

          }
        },
        {
         
          title: "是否删除",
          key: "isDelete",
          align: "center",
          width: 110,
          render: (h, params) => {
            if (params.row.isDelete === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "否"
              );
            } else if (params.row.isDelete === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "删除"
              );
            }

          }
        },
        {
          title: "操作",
          width: 155,
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
                        this.edit(params);
                      }
                    }
                  },
                  "编辑"
                )
              ]);
          }
        }
        
      ],

      startTime:"",
      endTime:"",
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getWorkoList: async function(pages) {

      this.queryParams.pageNo = this.pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getWorkoList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getWorkoList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getWorkoList(this.currentPage);
    },
    query() {
      this.currentPage=1;
      this.getWorkoList(this.currentPage);
    },
    openPowerComponent(e) {
      this.modal = true;
    },
    exportExcel(){
      window.location.href=(window.location.origin+`/api/hkdc-opex-api/hxWorkReport/exportExcel?startTime=`
      +this.startTime+'&endTime='+this.endTime);
    },
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getWorkoList(this.currentPage);
    },
			changeStartTime(date) {
				console.log("changeStartTime:"+date);
				if(typeof(date) != "undefined"){
					this.startTime=date;
				}else{
					this.startTime="";
				}
				
			},
			changeEndTime(date) {
				console.log("changeEndTime:"+date);
				if(typeof(date) != "undefined"){
					this.endTime=date;
				}else{
					this.endTime="";
				}
				
			},
    edit(e) {

      this.modal1 = true;
      this.updateParams.id=e.row.id;
      this.updateParams.dataKey=e.row.dataKey;
      this.updateParams.isTop=e.row.isTop;
      this.updateParams.isDelete=e.row.isDelete;
      console.log(e.row);
      //  this.modal6Data=data;
    },      
    bathSure : async function(pages) {

          console.log( this.updateParams.dataKey);


          this.loading = true;
          const result = await this.$api.updateWorko(this.updateParams);
          if (result.code === 1) {
            this.currentPage=1;
            this.getWorkoList(this.currentPage);
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
    this.getWorkoList(this.currentPage);
  }
};
</script>
