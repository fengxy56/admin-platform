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
    <Modal v-model="modal" title="二维码" width="400" footer-hide :mask-closable="false">
      <QcodeComponent         
        v-on:funclose="closeModal"
        v-on:func="saveAndCloseModal"
        v-if="modal" 
        :menuTheme="this.menuTheme" 
        :rowData="this.rowData" />
    </Modal>
  </div>
</template>
<script>
import QcodeComponent from "../../components/qcode/add";
export default {
  name: "QcodeList",
  components: {
    QcodeComponent
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
          width: 60,
          tooltip: true,
        },
        {
          title: "主键id",
          key: "id",
          width: 80,
          tooltip: true,
        },
        {
          title: "名称",
          key: "name",
          width: 200,
          tooltip: true,
        },
        {
          title: "跳转地址",
          key: "goUrl",
          width: 120,
          tooltip: true,
        },
        {
          title: "下载地址",
          key: "downUrl",
          width: 200,
          tooltip: true,
          render: (h, params) => {
            return h("img", 
                {
                  attrs:{
                    src: params.row.downUrl,
                    style: 'width: 150px;border-radius: 2px;margin-top:4px;' //图片在表格中的样式
                  },
                  props: {
                    type: "info",
                    size: "small"
                  }
                }

            );
          }
        },
        {
          title: "创建时间",
          key: "createTime",
          width: 120,
          tooltip: true,
        },
        {
          title: "修改时间",
          key: "updateTime",
          width: 120,
          tooltip: true,
        },
        {
          title: "操作",
          width: 200,
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
                        this.downloadIamge(params);
                      }
                    }
                  },
                  "二维码下载"
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
      data: [],
      name: "qrCodeCom",
    };
  },
  methods: {
    getHxCodeInfoList: async function(pages) {
      const data = {
        pageNo: pages,
        pageSize: this.sizes,
        sysCode: this.sysCode,
        sysName: this.sysName,
      };
      this.loading = true;
      const result = await this.$api.getHxCodeInfoList(data);
      this.loading = false;
      if (result.code === 1) {
        this.data = result.data.records;
        this.totals = result.data.total;
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getHxCodeInfoList(this.currentPage);
    },
    //切换每页容量
    swichSize(size) {
      this.sizes = size;
      this.getHxCodeInfoList(this.currentPage);
    },
    query() {
      this.getHxCodeInfoList(this.currentPage);
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
      this.getHxCodeInfoList(this.currentPage);
    },
    downloadIamge(params) {

      console.log(params.row);
      let imgsrc = params.row.downUrl;
      let name = params.row.name;

      name = name.replace(/\./g,'');
      
      console.log(name);

      //下载图片地址和图片名
      let image = new Image();
      // 解决跨域 Canvas 污染问题
      image.setAttribute("crossOrigin", "anonymous");
      image.onload = function() {
        let canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        let context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        let url = canvas.toDataURL("image/png"); //得到图片的base64编码数据
        let a = document.createElement("a"); // 生成一个a元素
        let event = new MouseEvent("click"); // 创建一个单击事件
        a.download = name || "photo"; // 设置图片名称
        a.href = url; // 将生成的URL设置为a.href属性
        a.dispatchEvent(event); // 触发a的单击事件
      };
      image.src = imgsrc;
	
    }


  },
  mounted() {
    this.getHxCodeInfoList(this.currentPage);
  }
};
</script>
