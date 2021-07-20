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
        组别：
        <Input v-model="dictGroup" placeholder="请输入组别" style="width: 2rem" />
      </div>
      <div>
        名称：
        <Input v-model="dictName" placeholder="请输入名称" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
      <div>
        <Button type="success" @click="addParent">新增</Button>
      </div>
    </div>
    <Table
      row-key="id"
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
    <Modal v-model="modal" title="系统公司" width="1000" footer-hide :mask-closable="false">
      <AddBusinessSystemComponent
        v-on:func="saveAndCloseModal"
        v-on:funclose="closeModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :rowData="this.rowData"
      />
    </Modal>

     <Modal v-model="modalAdmin" title="系统公司-管理员" width="900" footer-hide :mask-closable="false">
      <AdminListComponent
        v-on:func="saveAndCloseModalAdmin"
        v-on:funclose="closeModalAdmin"
        v-if="modalAdmin"
        :menuTheme="this.menuTheme"
        :rowData="this.rowData"
      />
    </Modal>

  </div>
</template>
<script>
import AddBusinessSystemComponent from "./add";
import AdminListComponent from "./adminList";

export default {
  name: "List",
  components: {
    AddBusinessSystemComponent,
    AdminListComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modalAdmin:false,
      modal: false,
      loading: false,
      rowData: {},
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "公司id",
          key: "id",
          width: 260,
          tooltip: true,
        },
        {
          title: "公司名称",
          key: "businessSystemName",
          width: 180,
          tooltip: true,
        },
        {
          title: "公司编码",
          key: "businessSystemCode",
          width: 100,
          tooltip: true,
        },
        {
          title: "管理员账户",
          key: "adminAccount",
          width: 180,
          tooltip: true,
        },
        {
          title: "备注",
          key: "remark",
          width: 120,
          tooltip: true,
        },
        {
          title: "状态",
          key: "status",
          align: "center",
          width: 100,
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
                "无效"
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
          title: "排序",
          key: "showOrder",
          align: "center",
          width: 80,
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
          title: "操作",
          width: 180,
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
              ),
              h(
                "Button",
                {
                  props: {
                    type: "success",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.adminList(params);
                    }
                  }
                },
                "管理员"
              )
            ]);
          }
        }


      ],
      dictGroup: "",
      dictName: "",
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
    getInitList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        dictGroup: this.dictGroup,
        dictName: this.dictName,
        status: this.status,
        parentId: this.parentId,
        level: this.level
      };
      if (!this.dictGroup && !this.dictName && !this.parentId) {
        data.level = 1;
      }
      this.loading = true;
      const result = await this.$api.getBusinessSystemList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getInitList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getInitList(this.currentPage);
    },
    //条件查询
    query() {
      this.getInitList(this.currentPage);
    },
    adminList(node) {
      console.log(node.row);

      this.rowData = node.row;
      this.rowData.actionType = "add";
      this.canEdit = true;
      this.modalAdmin = true;

      console.log("adminList");
    },
    addParent() {
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
      this.getInitList(this.currentPage);
    },

    //关闭授权组件
    closeModalAdmin() {
      this.modalAdmin = false;
    },
    //关闭授权组件
    saveAndCloseModalAdmin() {
      this.modalAdmin = false;
      this.getInitList(this.currentPage);
    }

  },
  mounted() {
    this.getInitList(this.currentPage);
  }
};
</script>
