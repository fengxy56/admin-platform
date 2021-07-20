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
          是否在职:
              <Select v-model="queryParams.isDelete" clearable  style="width: 1rem">
                <Option :value="0">在职</Option>
                <Option :value="1">离职</Option>
              </Select>

      </div>
      <div>
        账号:
        <Input v-model="queryParams.userName" placeholder="请输入账号" style="width: 100px" />
      </div>
      <div>
        姓名:
        <Input v-model="queryParams.trueName" placeholder="请输入姓名" style="width: 100px" />
      </div>
      <div>
        部门:
        <Input v-model="queryParams.dept" placeholder="请输入部门" style="width: 100px" />
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
        title="同事圈用户启用/禁用修改"
        @on-ok="bathSure"
        @on-cancel="cancel">

     <Form ref="formData"  :label-width="80" class="dict_form">

			<Row type="flex" justify="space-around" class="code-row-bg">
				<Col span="12">
					<FormItem prop="isDelete" label="是否在职">
						<RadioGroup v-model="updateParams.isDelete">
							<Radio :label="0">在职</Radio>
							<Radio :label="1">离职</Radio>
						</RadioGroup>
					</FormItem>
				</Col>
			</Row>
			<div style="display: flex;justify-content: start;">
				<h4 style="line-height: 30px;margin-right: 5px;">启动/禁用原因：</h4>
				<p><Input v-model="updateParams.approvalReason" placeholder="请输入启动/禁用原因" style="width: 300px" /></p>
			</div>

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
        userName: "",
        trueName: "",
        dept: "",
        pageNo: 1,
        pageSize: 10,
      },
      modal: false,
      modal1: false,
      loading: false,
      roleId: "",
      updateParams:{
        id:"",
        username:"",
        isDelete:0,
        approvalReason: "",
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
          title: "账号",
          width: 100,
          key: "username",
          align: "center",
          tooltip: true,
        },
        {
          title: "姓名",
          width: 150,
          key: "trueName",
          align: "center",
          tooltip: true,
        },
        {
         
          title: "是否在职",
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
                "在职"
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
                "离职"
              );
            }

          }
        },
        {
          title: "邮箱",
          width: 150,
          key: "email",
          align: "center",
          tooltip: true,
        },
        {
          title: "岗位",
          width: 300,
          key: "jobs",
          align: "center",
          tooltip: true,
        },
        {
          title: "头像",
          width: 150,
          key: "headIcon",
          align: "center",
          tooltip: true,
        },
        {
          title: "部门",
          width: 150,
          key: "dept",
          align: "center",
          tooltip: true,
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
    getWorkoUsersList: async function(pages) {

      this.queryParams.pageNo = this.pages;
      this.queryParams.pageSize = this.sizes;

      this.loading = true;
      const result = await this.$api.getWorkoUsersList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getWorkoUsersList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getWorkoUsersList(this.currentPage);
    },
    query() {
      if(this.queryParams.isDelete === -2){
				this.queryParams.isDelete = "";
      }
      if(this.queryParams.dwType === -2){
				this.queryParams.dwType = "";
      }
      this.currentPage=1;
      this.getWorkoUsersList(this.currentPage);
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
      this.getWorkoUsersList(this.currentPage);
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
      this.updateParams.username=e.row.username;
      this.updateParams.isDelete=e.row.isDelete;
      console.log(e.row);
      //  this.modal6Data=data;
    },      
    bathSure : async function(pages) {

          console.log( this.updateParams.username);


          this.loading = true;
          const result = await this.$api.saveOrUpdateOaApprovalUsers(this.updateParams);
          if (result.code === 1) {
            this.currentPage=1;
            this.getWorkoUsersList(this.currentPage);
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
    this.getWorkoUsersList(this.currentPage);
  }
};
</script>
