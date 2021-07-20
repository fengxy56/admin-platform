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
        账号：
        <Input v-model="phone" placeholder="账号(精准搜索)" style="width: 2rem" />
      </div>
      <div>
        姓名：
        <Input v-model="name" placeholder="姓名(模糊搜索)" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
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
      :total="totals"
      show-sizer
      :style="{padding:'15px'}"
      :page-size="sizes"
      @on-change="swichPage"
      @on-page-size-change="swichSize"
    />
    <Modal v-model="modal" title="人员信息" width="400" footer-hide :mask-closable="false">
      <HxPersonInfoComponent
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :roleId="roleId"
      />
    </Modal>

     <Modal
        v-model="modal6"
        title="Title"
        :loading="loading"
        @on-ok="asyncUpdateLastTimeOK">
        <p>是否确定更新该人员最后修改时间</p>
    </Modal>

  </div>
</template>
<script>
import HxPersonInfoComponent from "../../../components/hx/person/index";
export default {
  name: "HxPersonInfoList",
  components: {
    HxPersonInfoComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal6Data:{},
      modal6: false,
      modal: false,
      loading: false,
      roleId: "",
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "用户id",
          width: 100,
          key: "id",
          tooltip: true,
        },
        {
          title: "姓名",
          width: 100,
          key: "name",
          tooltip: true,
        },
        {
          title: "账号",
          width: 100,
          key: "phone",
          tooltip: true,
        },
        {
          title: "用户oId",
          width: 200,
          key: "oId",
          tooltip: true,
        },
        {
         
          title: "状态",
          key: "status",
          align: "center",
          width: 110,
          render: (h, params) => {
            if (params.row.status === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "注销"
              );
            } else if (params.row.status === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "有效"
              );
            }else if (params.row.status === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "warning",
                    type: "border"
                  }
                },
                "禁用"
              );
            }
          }
        },
        {
          title: "手机号",
          width: 100,
          key: "officePhone1",
          tooltip: true,
        },
        {
          title: "用户openId",
          width: 100,
          key: "openId",
          tooltip: true,
        },
        {
          title: "最后修改时间",
          width: 100,
          key: "lastUpdateTime",
          tooltip: true,
        },
        {
         
          title: "性别",
          key: "gender",
          align: "center",
          width: 110,
          render: (h, params) => {
            if (params.row.gender === 0) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "不确定"
              );
            } else if (params.row.gender === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "男"
              );
            }else if (params.row.gender === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "warning",
                    type: "border"
                  }
                },
                "女"
              );
            }
          }
        },
        {
          title: "邮箱",
          width: 100,
          key: "emails",
          tooltip: true,
        },
        {
          title: "组织简称",
          width: 100,
          key: "department",
          tooltip: true,
        },
        {
          title: "组织",
          width: 100,
          key: "orgLongName",
          tooltip: true,
        },
        {
          title: "组织Id",
          width: 100,
          key: "orgId",
          tooltip: true,
        },
        {
          title: "创建时间",
          width: 100,
          key: "createTime",
          tooltip: true,
        },
        {
          title: "岗位",
          width: 100,
          key: "jobTitle",
          tooltip: true,
        },
        {
          title: "操作",
          width: 155,
          align: "center",
          fixed: "right",
          render: (h, params) => {
            if (params.row.status === 0) {
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
                        this.updateComponent(params);
                      }
                    }
                  },
                  "更新时间"
                )
              ]);
            } 
          }
        }
      ],
      phone: "",
      name: "",  
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getTBdPersonList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        phone: this.phone,
        name: this.name
      };
      this.loading = true;
      const result = await this.$api.getTBdPersonList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getTBdPersonList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getTBdPersonList(this.currentPage);
    },
    query() {
      this.getTBdPersonList(this.currentPage);
    },
    updateComponent(e) {

      this.modal6 = true;
      const data = {
        phone: e.row.phone,
        oId: e.row.oId,
        status: e.row.status
      };
     this.modal6Data=data;


    },
    async asyncUpdateLastTimeOK() {

      //updateTBdPersonLastUpdateTime
      const result = await this.$api.updateTBdPersonLastUpdateTime(this.modal6Data);

      console.log(JSON.stringify(result));
      if (result.code === 1) {
            this.$Message.success("修改成功!");
      }else{
          console.log(result.errMsg);
          this.$Message.error(result.errMsg);
      }
      this.modal6=false;

    },
    //关闭授权组件
    closeModal() {
      this.modal = false;
    },
    //关闭授权组件
    saveAndCloseModal() {
      this.modal = false;
      this.getTBdPersonList(this.currentPage);
    }
  },
  mounted() {
    this.getTBdPersonList(this.currentPage);
  }
};
</script>
