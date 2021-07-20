<style>
.menu_list {
  display: flex;
  justify-content: space-between;
}
.menu_list > div:nth-child(1) {
  width: 40%;
  overflow-y: auto;
  overflow-x: hidden;
  border: 1px solid #ddd;
}

.menu_list > div:nth-child(2) {
  width: 59%;
  border: 1px solid #ddd;
}
.demo-tree-render .ivu-tree-title {
  width: 100%;
}

.menu_form {
  padding: 8px 20px 0 0;
  width: 60%;
}
</style>
<template>
  <div class="menu_list" :style="{ height:(windowHeight-120)+'px'}">
    <div>
      <Tree :data="data" :render="renderContent" class="demo-tree-render"></Tree>
    </div>
    <div>
      <Form
        ref="formData"
        :model="formData"
        :rules="ruleInline"
        :label-width="80"
        class="menu_form"
      >
        <FormItem prop="parentId" label="父级">
          <Input type="text" v-model="formData.parentId" :style="{display:'none'}" />
          <Input type="text" v-model="parentNodeName" disabled />
        </FormItem>
        <FormItem prop="menuName" label="名称">
          <Input type="text" v-model="formData.menuName" placeholder="菜单或按钮名称" />
        </FormItem>
        <FormItem prop="type" label="类型">
          <RadioGroup v-model="formData.type">
            <Radio :label="0">
              父级菜单
              <Tooltip :theme="menuTheme">
                <Icon type="md-help-circle" />
                <div slot="content">
                  <p>父级菜单不具备页面跳转能力</p>
                  <p>
                    <i>点击只是展开子菜单</i>
                  </p>
                </div>
              </Tooltip>
            </Radio>
            <Radio :label="1">
              末级菜单
              <Tooltip content="末级菜单负责页面跳转" :theme="menuTheme">
                <Icon type="md-help-circle" />
              </Tooltip>
            </Radio>
            <Radio :label="2">按钮</Radio>
          </RadioGroup>
        </FormItem>

        <FormItem v-if="this.formData.type===1" prop="menuUrl" label="组件">
          <Select v-model="formData.menuUrl" style="width:200px">
            <Option v-for="item in components" :value="item.value" :key="item.value">{{ item.name }}</Option>
          </Select>
        </FormItem>
        <FormItem v-if="this.formData.type===2" prop="menuUrl" label="路径">
          <Input type="text" v-model="formData.menuUrl" placeholder="/main/getSysInfo" />
        </FormItem>
        <FormItem v-if="this.formData.type>0" prop="menuCode" label="编码">
          <Input type="text" :value="formData.menuCode" placeholder="菜单或按钮编码" disabled />
        </FormItem>
        <FormItem v-else prop="icon" label="图标">
          <Select v-model="formData.icon" style="width:200px">
            <Option v-for="item in iconList" :value="item" :key="item">
              <Icon size="25" :type="item" />
              <span style="margin-left:5px;font-size:20px;line-height:25px;">{{item}}</span>
            </Option>
          </Select>
        </FormItem>
        <FormItem prop="status" label="状态">
          <RadioGroup v-model="formData.status">
            <Radio :label="1">启用</Radio>
            <Radio :label="0">禁用</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem prop="showOrder" label="排序">
          <InputNumber :max="100" :min="0" v-model="formData.showOrder"></InputNumber>
        </FormItem>
        <FormItem>
          <Button type="success" @click="handleSubmit('formData')">保存</Button>
        </FormItem>
      </Form>
      <Spin fix v-show="addLoading"></Spin>
    </div>
  </div>
</template>
<script>
export default {
  menuName: "MenuIndex",
  props: {
    menuTheme: String,
    windowHeight: Number,
    components: Array
  },
  data() {
    return {
      data: [
        {
          title: "菜单树",
          expand: true,
          render: (h, { root, node, data }) => {
            return h(
              "span",
              {
                style: {
                  display: "inline-block",
                  width: "100%"
                }
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
      buttonProps: {
        type: "default",
        size: "small"
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
        "ios-phone-portrait",
        "md-pricetags"
      ]
    };
  },
  mounted() {
    this.getTreeXMenuList();
  },
  methods: {
    //获取菜单数据
    getTreeXMenuList: async function() {
      const result = await this.$api.getTreeXMenuList();
      if (result.code === 1) {
        this.data[0].children = result.data;
      }
    },
    //菜单树状结构渲染方法
    renderContent(h, { root, node, data }) {
      if (data.type !== 2) {
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
                this.updateMune(root, node, data);
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
                h("Button", {
                  props: Object.assign({}, this.buttonProps, {
                    icon: "ios-add"
                  }),
                  style: {
                    marginRight: "8px"
                  },
                  on: {
                    click: () => {
                      this.append(data);
                    }
                  }
                }),
                h("Button", {
                  props: Object.assign({}, this.buttonProps, {
                    icon: "ios-remove"
                  }),
                  style: {
                    marginRight: "8px"
                  },
                  on: {
                    click: () => {
                      this.remove(root, node, data);
                    }
                  }
                })
              ]
            )
          ]
        );
      } else {
        //按钮
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
                this.updateMune(root, node, data);
              }
            }
          },
          [
            h("span", [
              h("Icon", {
                props: {
                  type: "ios-locate-outline"
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
                  marginRight: "34px"
                }
              },
              [
                h("Button", {
                  props: Object.assign({}, this.buttonProps, {
                    icon: "ios-remove"
                  }),
                  on: {
                    click: () => {
                      this.remove(root, node, data);
                    }
                  }
                })
              ]
            )
          ]
        );
      }
    },
    //新增菜单表单
    append(data) {
      event.stopPropagation();
      this.parentNodeName = data.title;
      this.formData = {
        menuName: "",
        type: 0,
        showOrder: 0,
        menuUrl: "",
        menuCode: this.$tool.genID(),
        parentId: data.id || -1,
        status: 1,
        menuType: 0,
        icon: ""
      };
      this.appendParentNode = data;
    },
    //删除菜单
    remove(root, node, data) {
      this.$Modal.confirm({
        title: "确认删除",
        content: `您确认要删除【${data.title}】？</br> 删除后将<span style="color: red;">无法恢复！</span>`,
        onOk: async () => {
          this.addLoading = true;
          const result = await this.$api.deleteXMenuById({ id: data.id });
          if (result.code === 1) {
            //ui删除
            const parentKey = root.find(el => el === node).parent;
            const parent = root.find(el => el.nodeKey === parentKey).node;
            const index = parent.children.indexOf(data);
            parent.children.splice(index, 1);
            this.$Message.success("删除成功!");
            this.$emit("func");
          } else {
            this.$Message.error(result.errMsg);
          }
          this.addLoading = false;
        }
      });
    },
    //提交菜单
    async handleSubmit(form) {
      if (!this.appendParentNode) {
        this.$Message.error("请选择父级菜单");
        return;
      }
      this.$refs[form].validate(async valid => {
        if (valid) {
          this.addLoading = true;
          const result = await this.$api.saveOrUpdateHkMenu(this.formData);
          this.addLoading = false;
          if (result.code === 1) {
            if (!this.formData.id) {
              //提交新增
              this.$Message.success("保存成功!");
              const children = this.appendParentNode.children || [];
              children.push({
                title: this.formData.menuName,
                expand: true
              });
              this.$set(this.appendParentNode, "children", children);
            } else {
              //提交修改
              this.$Message.success("修改成功!");
              let d = this.appendParentNode.children.find(
                el => el === this.formData
              );
              d.title = this.formData.menuName;
            }
            this.$emit("func");
          } else {
            this.$Message.error(result.errMsg);
          }
        }
      });
    },
    //编辑菜单
    updateMune(root, node, data) {
      const parentKey = root.find(el => el === node).parent;
      const parent = root.find(el => el.nodeKey === parentKey).node;
      this.formData = data;
      this.appendParentNode = parent;
      this.parentNodeName = parent.title;
    }
  }
};
</script>