<style >
.demo-tree-render .ivu-tree-title {
  width: 90%;
}
</style>
<template>
  <div class="user">
    <Tree :data="data" class="demo-tree-render" show-checkbox multiple @on-check-change="checks"></Tree>
    <Spin fix v-show="loading"></Spin>
    <div slot="footer" class="clearfix">
      <Button type="success" @click="save" style="float:right;">保存</Button>
      <Button type="info" @click="closeModal()" style="float:right;margin-right: 10px;">取消</Button>
    </div>
  </div>
</template>
<script>
export default {
  name: "HxOrgComponent",
  props: {
    authId: String,
    authType: Number,
  },
  data() {
    return {
      data: [],
      menuIdList: [],
      loading: false,
      selectAction: false,
      queryParams: {
					authId: "",
          authType: 0,
          sysType:"hx_org_auth",
          sysName:"鸿信组织权限",
			},

    };
  },
  methods: {
    //获取权限菜单数据
    async getHxOrgTreeList() {
      this.loading = true;
      const result = await this.$api.getHxOrgTreeList(this.queryParams);
      if (result.code === 1) {
        this.data = result.data;
        this.loading = false;
      }
    },
    async save() {
      if (this.selectAction) {
        this.loading = true;
        const param = { 
              menuIdList: this.menuIdList, 
              authId: this.queryParams.authId,
              authType: this.queryParams.authType,
              sysType:this.queryParams.sysType ,
              sysName:this.queryParams.sysName
           };
        const result = await this.$api.saveOrUpdateSysAuthMenu(param);
        if (result.code === 1) {
          this.$Message.success("保存成功!");
          this.loading = false;
          this.$emit("func"); //关闭父级modal
        }else {
          this.loading = false;
          this.$Message.warning(result.errMsg);
        }
      } else {
        this.$Message.warning("操作无效！");
        this.$emit("func"); //关闭父级modal
      }
    },
    checks(root, node) {
      this.selectAction = true;
      const arr = [];
      root.forEach(item => {
        arr.push(item.menuId);
      });
      this.menuIdList = arr;
    },
    closeModal() {
      this.$emit("funclose"); //关闭父级modal
    }
  },
  mounted() {

    console.log(this.authId+":"+this.authType);
    this.queryParams.authId = this.authId;

    this.getHxOrgTreeList();
  }
};
</script>