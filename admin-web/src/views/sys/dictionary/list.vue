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
    <Modal v-model="modal" title="字典" width="900" footer-hide :mask-closable="false">
      <dictComponent
        v-on:func="saveAndCloseModal"
        v-on:funclose="closeModal"
        v-if="modal"
        :menuTheme="this.menuTheme"
        :rowData="this.rowData"
      />
    </Modal>
  </div>
</template>
<script>
import dictComponent from "./add";
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
      columns: [
        {
          title: "序号",
          type: "index",
          width: 70,
          align: "center"
        },
        {
          title: "名称",
          key: "dictName",
          minWidth: 160,
          tree: true
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
          title: "系统id",
          key: "id",
          width: 100,
          tooltip: true,
        },
        {
          title: "字典组",
          key: "dictGroup",
          width: 100,
          tooltip: true,
        },
        {
          title: "编码",
          key: "dictCode",
          width: 100,
          tooltip: true,
        },
        {
          title: "备注",
          key: "remark",
          width: 100,
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
                      this.add(params);
                    }
                  }
                },
                "添加"
              )
            ]);
          }
        },
        {
          title: "扩展字段1",
          key: "ext1",
          width: 100,
          tooltip: true,
        },
        {
          title: "扩展字段2",
          key: "ext2",
          width: 100,
          tooltip: true,
        },
        {
          title: "扩展字段3",
          key: "ext3",
          width: 100,
          tooltip: true,
        },
        {
          title: "扩展字段4",
          key: "ext4",
          width: 100,
          tooltip: true,
        },
        {
          title: "扩展字段5",
          key: "ext5",
          width: 100,
          tooltip: true,
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
    getDictList: async function(pages) {
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
      const result = await this.$api.getDictList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getDictList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getDictList(this.currentPage);
    },
    //条件查询
    query() {
      this.getDictList(this.currentPage);
    },
    add(node) {
      this.rowData = node.row;
      this.rowData.actionType = "add";
      this.canEdit = true;
      this.modal = true;
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
      this.getDictList(this.currentPage);
    }
  },
  mounted() {
    this.getDictList(this.currentPage);
  }
};
</script>
