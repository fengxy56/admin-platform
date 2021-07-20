<template>
    <div>
        <Tree :data="treedata" @on-select-change="choiceAll" ref="tree"></Tree>
    </div>
</template>
<script>
export default {
  name: "left_tree",
  props: {
    menuTheme: String,
    windowHeight: Number
  },
  data() {
    return {
      loading: true,
      treedata:[],
      data1:[
                {
                    title: '鸿坤产业',
                    expand: true,
                    level_id:1,
                    children: [
                        {
                            title: '北京公司',
                            expand: true,
                            level_id:2,
                            children: [
                                {
                                    title: '鸿坤·七星长安',
                                    level_id:3,
                                    id:'1'
                                },
                                {
                                    title: '花语墅(宜昌)项目',
                                    level_id:3,
                                    id:'2'
                                },{
                                    title: '理想城(廊坊)项目',
                                    level_id:3,
                                    id:'3'
                                }
                            ]
                        },
                        {
                            title: '华北公司',
                            expand: true,
                            level_id:2,
                            children: [
                                {
                                    title: '凤凰城(张家口)项目',
                                    level_id:3,
                                    id:'4'
                                },
                                {
                                    title: '理想尔湾项目',
                                    level_id:3,
                                    id:'5'
                                }
                            ]
                        }
                    ]
                }
            ]
    }
  },
  methods: {
    choiceAll(data){
        this.$emit('func',data)
    //   this.$router.push({
    //     path: '/info',
    //     query: ''
    //   });
    },
    getData(data) {
        if(!data.length) {
            return;
        }
        data.forEach((item, index) => {
            data[index].title = item.text
            if(item.children && item.children.length) {
                this.getData(item.children)
            }
        })
    },
    // 获取项目列表左侧组织树
    async projectList() {
        const treelist = await this.$api.projectList();
        let treedata = [treelist.data];
        this.getData(treedata)
        this.treedata = treedata
    },
    

  },
  mounted() {
      this.projectList()
  }
};
</script>
<style scoped>

</style>