<style scoped>
.search {
  width: 200px;
  float: right;
}
.commons {
  line-height: 1;
}
.userinput {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 5px;
}
.userinput > div {
  margin-left: 10px;
}
</style>
<template>
  <div class="commons">
    <div class="userinput">
      <div>
        编码：
        <Input v-model="sysCode" placeholder="请输入编码：" style="width: 2rem" />
      </div>
      <div>
        名称：
        <Input v-model="sysName" placeholder="请输入名称" style="width: 2rem" />
      </div>
      <div>
        <Button type="primary" @click="query">查询</Button>
      </div>
      <div>
        <Button type="success" @click="add">新增</Button>
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
    <Modal v-model="modal" title="系统配置" width="400" footer-hide :mask-closable="false">
      <interfaceComponent         
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal" 
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData" />
    </Modal>
  </div>
</template>
<script>
import interfaceComponent from "../../components/interfaceComponent/index";
export default {
  name: "InterfaceList",
  components: {
    interfaceComponent
  },
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      modal: false,
      loading: false,
      columns: [
        {
          title: "序号",
          type: "index",       
          align: "center",
          width: 70,
          tooltip: true,
        },
        {
          title: "系统id",
          key: "id",
          width: 120,
          tooltip: true,
        },
        {
          title: "系统编码",
          key: "sysCode",
          width: 120,
          tooltip: true,
        },
        {
          title: "系统名称",
          key: "sysName",
          width: 120,
          tooltip: true,
        },
        {
          title: "秘钥",
          key: "sysSercret",
          width: 260,
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
          title: "修改时间",
          key: "updateTime",
          width: 120,
          tooltip: true,
        },
        {
          title: "操作",
          width: 120,
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
      sysCode: "",
      sysName: "",
      rowData: {},
      sizes: 20,
      currentPage: 1,
      totals: 0,
      data: []
    };
  },
  methods: {
    getSysInfoList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        sysCode: this.sysCode,
        sysName: this.sysName,
      };
      this.loading = true;
      const result = await this.$api.getSysInfoList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getSysInfoList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getSysInfoList(this.currentPage);
    },
    query() {
      this.getSysInfoList(this.currentPage);
    },
    add() {
      this.rowData.actionType = "add";
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
      this.getSysInfoList(this.currentPage);
    }
  },
  mounted() {
    this.getSysInfoList(this.currentPage);
  }
};
</script>
