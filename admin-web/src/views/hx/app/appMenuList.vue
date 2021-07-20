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
        应用appid：
        		<Select style="width:150px;" filterable v-model="queryParams.appId" :label-in-value="true"  @on-change="changeHxCustApp">
							<Option  v-for="item in hxCustAppList" :value="item.appId" :key="item.id">{{ item.appName }}</Option>
						</Select>
      </div>
      <div>
        名称：
        <Input v-model="appName" placeholder="请输入名称" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
      <div>
        <Button type="success" @click="add">新增</Button>
      </div>
    </div>
    <Table
      row-key="appId"
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
    <Modal v-model="modal" title="应用子菜单" width="900" footer-hide :mask-closable="false">
      <dictComponent
        v-on:func="saveAndCloseModal"
        v-on:funclose="closeModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :rowData="this.rowData"
        :queryParams="this.queryParams"
      />
    </Modal>
  </div>
</template>
<script>
import dictComponent from "./addMenu";
export default {
  name: "List",
  components: {
    dictComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      loading: false,
      rowData: {},
      hxCustAppList:[],
      queryParams: {
          appId:"",
          appName: "",
			},
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "轻应用id",     
          key: "appId",
          minWidth: 200,
          tree: true,
        },
        {
          title: "轻应用名称",
          width: 100,
          key: "appName",
          tooltip: true,
        },
        {
          title: "等级",
          key: "level",
          align: "center",
          width: 70,
          render: (h, params) => {
            return h(
              "Tag",
              {
                props: this.$tool.getNumberColor(params.row.level),
                style: {
                  borderRadius: "50%",
                  textAlign: "center"
                }
              },
              params.row.level
            );
          }
        },
        {
          title: "父级id",
          width: 100,
          key: "parentAppId",
           tooltip: true,
        },
        {
          title: "父级名称",
          width: 100,
          key: "parentAppName",
          tooltip: true,
        },
        {
          title: "状态",
          width: 100,
          key: "status",
          tooltip: true,
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
                "禁用"
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
            }

          }
        },
        {
          title: "链接类型",
          width: 100,
          key: "appAddressType",
          tooltip: true,
          render: (h, params) => {
            if (params.row.appAddressType === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "外部"
              );
            } else if (params.row.appAddressType === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "内部"
              );
            }

          }
        },
        {
          title: "链接跳转地址",
          width: 200,
          key: "appAddress",
          tooltip: true,
        },
        {
          title: "图片类型",
          width: 100,
          key: "appPhotoUrlType",
          tooltip: true,
          render: (h, params) => {
            if (params.row.appPhotoUrlType === 1) {
              return h(
                "Tag",
                {
                  props: {
                    color: "error",
                    type: "border"
                  }
                },
                "外部"
              );
            } else if (params.row.appPhotoUrlType === 2) {
              return h(
                "Tag",
                {
                  props: {
                    color: "success",
                    type: "border"
                  }
                },
                "内部"
              );
            }

          }
        },
        {
          title: "图片地址",
          width: 200,
          key: "appPhotoUrl",
          tooltip: true,
        },
        {
          title: "排序",
          key: "showOrder",
          align: "center",
          width: 90,
          render: (h, params) => {
            return h(
              "Tag",
              {
                props: this.$tool.getNumberColor(params.row.showOrder),
                style: {
                  borderRadius: "50%",
                  textAlign: "center"
                }
              },
              params.row.showOrder
            );
          }
        },
        {
          title: "修改姓名",
          width: 120,
          key: "updateName",
          tooltip: true,
        },
        {
          title: "修改账号",
          width: 120,
          key: "updateAccout",
          tooltip: true,
        },
        {
          title: "创建时间",
          width: 150,
          key: "createTime",
          tooltip: true,
        },
        {
          title: "修改时间",
          width: 150,
          key: "updateTime",
          tooltip: true,
        },
        {
         
          title: "备注",
          key: "remark",
          align: "center",
          width: 110,
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
                      this.update(params);
                    }
                  }
                },
                "编辑"
              ),h(
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
                      this.addParent(params);
                    }
                  }
                },
                "新增"
              )
            ]);
          }
        }
      ],
      appName: "",
      status: "",
      parentId: "",
      level: "",
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getHxCustAppMenuList: async function(pages) {
      this.loading = true;
      const result = await this.$api.getHxCustAppMenuList(this.queryParams);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    getHxCustAppInfoList: async function(pages) {
        this.queryParams.pageNo = 1;
        this.queryParams.pageSize = 1000;

        this.loading = true;
        const result = await this.$api.getHxCustAppInfoList(this.queryParams);
        this.loading = false;
        if (result.code === 1) {
          this.hxCustAppList = result.data.records;
  
        }
    }, 
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHxCustAppMenuList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxCustAppMenuList(this.currentPage);
    },
    //条件查询
    query() {
      this.getHxCustAppMenuList(this.currentPage);
    },
    add(node) {
      this.rowData.actionType = "add";
      this.canEdit = true;
      this.modal = true;
    },
    addParent(node) {
      this.rowData = node.row;
      this.rowData.actionType = "parent";
      this.modal = true;
    },
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
      this.getHxCustAppMenuList(this.currentPage);
    },
    changeHxCustApp(changeHxCustAppData){
				this.queryParams.appName = changeHxCustAppData.label;
    },

  },
  mounted() {
    //获取轻应用列表
    this.getHxCustAppInfoList();

  }
};
</script>
