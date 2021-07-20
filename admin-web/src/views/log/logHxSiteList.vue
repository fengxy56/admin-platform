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
        账户:
        <Input v-model="userid" placeholder="请输入账户" style="width: 100px" />
      </div>
      <div>
        开始时间：
        <DatePicker v-model="queryParams.startTime" @on-change="changeStartTime"  format="yyyy-MM-dd HH:mm:ss" type="datetime"  placeholder="请选择开始时间" style="width: 100px"></DatePicker>
      </div>
      <div>
        结束时间：
        <DatePicker v-model="queryParams.endTime" @on-change="changeEndTime"  format="yyyy-MM-dd HH:mm:ss" type="datetime"  placeholder="请选择结束时间" style="width: 100px"></DatePicker>
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
      <!-- <div>
        <Button type="success" @click="openPowerComponent">数据汇总</Button>
      </div> -->
      <div>
        <Button type="success" @click="exportExcel">汇总导出</Button>
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
    <Modal v-model="modal" title="数据汇总" width="900" footer-hide :mask-closable="false">
      <SiteComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :roleId="roleId"
      />
    </Modal>
  </div>
</template>
<script>
import SiteComponent from "../../components/log/site/siteList";
export default {
  name: "PowerList",
  components: {
    SiteComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      queryParams: {
        startTime: "",
        endTime: "",
      },
      modal: false,
      loading: false,
      roleId: "",
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center",
          tooltip: true,
        },
        {
          title: "账户",
          width: 150,
          key: "userid",
          align: "center",
          tooltip: true,
        },
        {
          title: "姓名",
          width: 150,
          key: "name",
          align: "center",
          tooltip: true,
        },
        {
          title: "组织",
          width: 300,
          key: "department",
          align: "center",
          tooltip: true,
        },
        {
          title: "岗位",
          width: 150,
          key: "jobTitle",
          align: "center",
          tooltip: true,
        },
        {
          title: "访问时间",
          width: 150,
          key: "createTime",
          align: "center",
          tooltip: true,
        }
        
      ],
      userid: "",
      startTime:"",
      endTime:"",
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getLogHxSiteList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        userid: this.userid,
        startTime: this.startTime,
        endTime: this.endTime
      };
      this.loading = true;
      const result = await this.$api.getLogHxSiteList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getLogHxSiteList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getLogHxSiteList(this.currentPage);
    },
    query() {
      this.currentPage=1;
      this.getLogHxSiteList(this.currentPage);
    },
    openPowerComponent(e) {
      this.modal = true;
    },
    async publish(e) {
      this.loading = true;
      const param = { roleId: e.row.bRoleId };
      const result = await this.$api.releaseUserXRoleAuthVO(param);
      this.loading = false;
      if (result.code === 1) {
        this.$Message.success("发布成功!");
        this.getLogHxSiteList(this.currentPage);
      }
    },
    exportExcel(){
      window.location.href=(window.location.origin+`/api/hkdc-opex-api/logHxSite/exportExcel?startTime=`
      +this.startTime+'&endTime='+this.endTime);
    },
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getLogHxSiteList(this.currentPage);
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
				
			}
  },
  mounted() {
    this.getLogHxSiteList(this.currentPage);
  }
};
</script>
