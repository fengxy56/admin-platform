<style>
.menu_list {
  display: flex;
  justify-content: space-between;
}
.menu_list > div:nth-child(1) {
  width: 25;
  overflow-y: auto;
  overflow-x: auto;
  border: 1px solid #ddd;
}

.menu_list > div:nth-child(2) {
  width: 74%;
  border: 1px solid #ddd;
}
.demo-tree-render .ivu-tree-title {
  width: 100%;
}


.userinput,.userManage,.orgManage {
  display: flex;
  justify-content: flex-start;
  margin: 10px 0 10px 30px;
}
.userinput > div,.userManage > div > button,.orgManage > div > button{
  margin-left: 10px;
}



</style>
<template>
  <div class="menu_list" :style="{ height:(windowHeight-120)+'px'}">
    <div>
      <Tree :data="data" :render="renderContent" class="demo-tree-render"></Tree>
    </div>

    <div> 
      <div class="userinput">
          <div>
            账号：
            <Input v-model="queryParams.account" placeholder="请输入账号" style="width: 1.5rem" />
          </div>
          <div>
            姓名：
            <Input v-model="queryParams.name" placeholder="请输入姓名" style="width: 1.5rem" />
          </div>
          <div>
            <Button :size="buttonSize" type="primary" @click="queryUser">查询</Button>
          </div>
      </div>
      <div class="userManage">
        用户管理：
           <div>
            <Button :size="buttonSize" type="primary" @click="addUser">添加用户</Button>
            <Button :size="buttonSize" type="error" @click="queryUser">删除所选用户</Button>
            <Button :size="buttonSize" type="warning" @click="queryUser">移动用户</Button>
          </div> 
      </div>
      <div class="orgManage">
        组织管理：
           <div>
            <Button :size="buttonSize" type="primary" @click="addOrg">新增组织</Button>
            <Button :size="buttonSize" type="primary" @click="editOrg">编辑组织</Button>
          </div> 
      </div>
      <div>
      <Table
            row-key="id"
            stripe
            border
            :height="this.windowHeight - 300"
            :columns="userColumns"
            :data="userData"
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
          :page-size-opts="[10, 20, 50, 100]"
          @on-change="swichPage"
          @on-page-size-change="swichSize"
        />

      </div>
      <Spin fix v-show="addLoading"></Spin>
    </div>


    <Modal v-model="modalAddUser" title="人员信息" width="800" max-height='200' footer-hide :mask-closable="false">
        <AddUserComponent         
        v-on:funclose="closeModalAddUser"
        v-on:func="saveAndCloseModalAddUser"
        v-if="modalAddUser" :menuTheme="this.menuTheme" :rowUserData="this.rowUserData" />
    </Modal>

    <Modal v-model="modalAddOrg" title="组织信息" width="800" max-height='200' footer-hide :mask-closable="false">
        <AddOrgComponent         
        v-on:funclose="closeModalAddOrg"
        v-on:func="saveAndCloseModalAddOrg"
        v-if="modalAddOrg" :menuTheme="this.menuTheme" :rowOrgData="this.rowOrgData" />
    </Modal>


  </div>
</template>
<script>
import moment from "moment";
import AddUserComponent from "./addUser";
import AddOrgComponent from "./addOrg";

export default {
  menuName: "SysOrg",
  components: {
    AddUserComponent,
    AddOrgComponent,
  },
  props: {
    menuTheme: String,
    windowHeight: Number,
    components: Array
  },
  data() {
    return {
      buttonSize: 'small',
      data: [
        {
          title: "菜单树",
          expand: true,
          render: (h, { root, node, data }) => {
            return h(
              "span",
              {

                 props: Object.assign({}, this.buttonProps, {
                  icon: "ios-add",
                  type: "primary"
                }),
                 style: {
                  display: "inline-block",
                  width: "100%"
                },
                on: {
                  click: () => {
                    this.getOrgUserList(root, node, data);
                  }
                },
  
              },
              [
                h("span", [
                  h("Icon", {
                    props: {
                      type: "ios-folder-outline"
                    },
                    style: {
                      marginRight: "8px"
                    }
                  }),
                  h("span", data.title)
                ]),
                h(
                  "span",
                  {
                    style: {
                      display: "inline-block",
                      float: "right",
                      marginRight: "32px"
                    }
                  },
                  [
                    h("Button", {
                      props: Object.assign({}, this.buttonProps, {
                        icon: "ios-add",
                        type: "primary"
                      }),
                      style: {
                        width: "56px"
                      },
                      on: {
                        click: () => {
                          this.append(data);
                        }
                      }
                    })
                  ]
                )
              ]
            );
          },
          children: []
        }
      ],
      sizes: 10,
      currentPage: 1,
      totals: 0,
      loading: false,
      modal:false,
      modalAddUser:false,
      modalAddOrg:false,
      rowOrgData: {},
      rowUserData:{},
      userData:[],
      userColumns: [
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
          title: "账号",
          key: "account",
          width: 120,
        },
        {
          title: "姓名",
          key: "name",
          width: 100,
        },
        {
          title: "生日",
          width: 100,
          key: "birthday",
          // render: (h, params) => {
          //   if (params.row.birthday) {
          //     //let dateList = params.row.birthday.split(' ');
          //     // console.log("date:",dateList);
          //     return h(
          //       "span",  moment(params.row.birthday).format("yyyy-MM-DD")
          //     );
          //   }
          // }

        },
        {
          title: "性别",
          width: 100,
          align: "center",
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
                "未知"
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
          title: "手机号",
          key: "phone",
          width: 100,
        },
        {
          title: "岗位",
          key: "jobTitle",
          width: 120,
        },
        {
          title: "邮箱",
          key: "120",
          width: 70,
        },
        {
          title: "组织",
          key: "orgName",
          width: 120,
        },
        {
          title: "组织长路径",
          key: "orgLongName",
          width: 160,
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
                        this.updateUser(params.row);
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
                        this.moveSysUserSort(params.row,"moveUp");
                      }
                    }
                  },
                  "上移"
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
                        this.moveSysUserSort(params.row,"moveDown");
                      }
                    }
                  },
                  "下移"
                )

              ]);

          }

        }
      ],
      buttonProps: {
        type: "default",
        size: "small"
      },
      queryParams: {
        account: "",
        name: "",
        orgId: "",
        status: 1,
      },
      formData: {
        menuName: "",
        type: 0,
        showOrder: 0,
        menuUrl: "",
        menuCode: "",
        parentId: "",
        status: 1,
        menuType: 0,
        icon: ""
      },
      ruleInline: {
        menuName: [
          {
            required: true,
            message: "名称不能为空",
            trigger: "blur"
          }
        ],
        menuCode: [
          {
            required: true,
            message: "编码不能为空",
            trigger: "blur"
          }
        ],
        menuUrl: [
          {
            required: true,
            message: "外链路径不能为空",
            trigger: "blur"
          }
        ]
      },
      addLoading: false,
      parentNodeName: "",
      appendParentNode: "",
      iconList: [
        "ios-add",
        "md-add",
        "ios-add-circle",
        "ios-add-circle-outline",
        "ios-analytics",
        "ios-analytics-outline",
        "logo-android",
        "logo-apple",
        "ios-apps",
        "md-arrow-dropright-circle",
        "md-cog",
        "ios-checkmark-circle",
        "ios-at",
        "md-arrow-round-down",
        "md-arrow-round-up",
        "ios-phone-portrait",
        "md-pricetags",
        
   
      ]
    };
  },
  mounted() {
    this.getSysOrgTreeList();
  },
  methods: {
    //获取菜单数据
    getSysOrgTreeList: async function() {
      const result = await this.$api.getSysOrgTreeList();
      if (result.code === 1) {
        this.data[0].children = result.data;
      }
    },
    //菜单树状结构渲染方法
    renderContent(h, { root, node, data }) {
        return h(
          "span",
          {
            style: {
              display: "flex",
              justifyContent: "space-between",
              width: "100%"
            },
            on: {
              click: () => {
                this.getOrgUserList(root, node, data);
              }
            }
          },
          [
            h("span", [
              h("Icon", {
                props: {
                  type: "ios-paper-outline"
                },
                style: {
                  marginRight: "8px"
                }
              }),
              h("span", data.title)
            ]),
            h(
              "span",
              {
                style: {
                  display: "inline-block",
                  float: "right",
                  marginRight: "26px"
                }
              },
              [
                h(
                  "Button", {
                    props: Object.assign({}, this.buttonProps, {
                      icon: "md-arrow-round-up",
                    }),
                    style: {
                      marginRight: "8px"
                    },
                    on: {
                      click: () => {
                        //this.append(data);
                        //this.modal=true;
                        this.moveSysOrgSort(data,"moveUp");
                      }
                    },
                    
                  },
                ),
                h("Button", {
                  props: Object.assign({}, this.buttonProps, {
                    icon: "md-arrow-round-down"
                  }),
                  style: {
                    marginRight: "8px"
                  },
                  on: {
                    click: () => {
                      //this.remove(root, node, data);
                      this.moveSysOrgSort(data,"moveDown");
                    }
                  }
                })
              ]
            )
          ]
        );

    },
    //编辑菜单,单击菜单事件
    getOrgUserList(root, node, data) {

      console.log("编辑菜单,单击菜单事件");

      this.rowOrgData = {};//先值为空
      this.rowOrgData = data;
      this.queryParams.orgId = data.id;//组织id
      //获取当前用户列表
      this.currentPage = 1;
      this.getSysUserList();

    },
    getSysUserList: async function() {
      this.queryParams.pageNo = this.currentPage;
      this.queryParams.pageSize = this.sizes;
      const result = await this.$api.getSysUserList(this.queryParams);
      console.log(result);
      if (result.code === 1) {
        this.userData = result.data.records;
        this.totals = result.data.total;
      }else {
        this.loading = false;
        this.$Message.warning(result.errMsg);
      }
    },
    //切换页码
    swichPage(page) {
      this.currentPage = page;
      this.getSysUserList();
    },
    //切换每页容量
    swichSize(size) {
      console.log("切换每页容量:"+size);
      this.sizes = size;
      this.getSysUserList();
    },
    //查询用户
    queryUser(){
      this.getSysUserList();
    },
    addUser(){
      console.log(this.rowOrgData);
      this.rowUserData.actionType = "add";
      this.rowUserData.orgId = this.rowOrgData.id ;
      this.rowUserData.orgName = this.rowOrgData.menuName ;
      this.rowUserData.orgLongName = this.rowOrgData.orgLongName ;


      if(this.rowUserData.orgId==null || this.rowUserData.orgId==''){
        this.$Message.warning("请先选中组织");
      }else{
        this.modalAddUser = true;
      }

    },
    updateUser(data){
      console.log(data);
      this.rowUserData = data;
      this.rowUserData.actionType = "update";
      this.modalAddUser = true;
    },
    async moveSysUserSort(data,moveType){

          console.log(data);
          const queryData = {
              moveType : moveType,
              showOrder : data.showOrder,
              orgId : data.orgId,
              id : data.id,
          };
          console.log(queryData);
          const result = await this.$api.moveSysUserSort(queryData);
          this.loading = false;
          if (result.code === 1) {
            this.$Message.success("移动人员成功!");
            this.currentPage = 1;
            this.getSysUserList();
            //this.$emit("func");
          } else {
            this.$Message.error(result.errMsg);
          }

    },
     //关闭授权组件
    closeModalAddUser() {
      this.modalAddUser = false;
    },
    //关闭授权组件
    saveAndCloseModalAddUser() {
      this.modalAddUser = false;
      this.currentPage = 1;
     //获取当前用户列表
      this.getSysUserList();
    },

    addOrg(){
      console.log(this.rowOrgData);
      if(this.rowOrgData.id == null || this.rowOrgData.id == ''){
        this.rowOrgData.parentId = "-1";
      }
      this.rowOrgData.actionType = "add";
      //跳转页面
      this.modalAddOrg = true;

    },
    editOrg(){
      console.log(this.rowOrgData);
      this.rowOrgData.actionType = "update";
      //跳转页面
      this.modalAddOrg = true;
    },
    async moveSysOrgSort(data,moveType){

          console.log(data);
          const queryData = {
              moveType : moveType,
              showOrder : data.showOrder,
              parentId : data.parentId,
              id : data.id,
          };
          console.log(queryData);
          const result = await this.$api.moveSysOrgSort(queryData);
          this.loading = false;
          if (result.code === 1) {
            this.$Message.success("移动组织成功!");
            this.getSysOrgTreeList();
            //this.$emit("func");
          } else {
            this.$Message.error(result.errMsg);
          }

    },
     //关闭授权组件
    closeModalAddOrg() {
      this.modalAddOrg = false;
    },
    //关闭授权组件
    saveAndCloseModalAddOrg() {
      this.modalAddOrg = false;
      this.getSysOrgTreeList();
    },

  }
};
</script>