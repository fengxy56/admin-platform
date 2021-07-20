<style ></style>
<template >
  <div v-if="isFolder">
    <div v-for="item in menuData.children" :key="item.index">
      <Submenu v-if="item.children.length>0" :name="item.menuCode">
        <template slot="title">
          <Icon :type="item.icon"></Icon>
          {{item.menuName}}
        </template>
        <menuComponet :menuData="item" />
      </Submenu>
      <MenuItem v-else :name="item.menuCode">{{item.menuName}}</MenuItem>
    </div>
  </div>
</template>
<script>
export default {
  name: "menuComponet",
  props: {
    menuData: Object
  },
  computed: {
    // 是否还有子列表需要渲染，作为v-if的判断条件
    isFolder() {
      return this.menuData.children && this.menuData.children.length > 0;
    }
  }
};
</script>
