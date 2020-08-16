<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryMenuName" clearable placeholder="菜单名称" style="width: 200px;" class="filter-item"/>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
    </div>
    <!--表格渲染-->
    <el-table
      :data="menus"
      :expand-all="false"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :load="getNextMenu"
      row-key="menuId"
      border
      size="small"
      lazy >
      <el-table-column prop="menuName" label="角色名称" />
      <el-table-column prop="icon" label="图标" align="center" width="80px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="sort" align="center" width="100px" label="排序">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.sort }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="path" label="链接地址"/>
      <el-table-column :show-overflow-tooltip="true" prop="component" label="组件路径"/>
      <el-table-column prop="is_frame" width="80px" label="是否显示">
        <template slot-scope="scope">
          <span>{{ scope.row.hidden === 0 ? '是':'否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleEdit(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :page-sizes="[50, 100, 300]"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
  </div>
</template>

<script>
import { del, getMenuList } from '@/api/menu'
import { parseTime } from '@/utils/index'
// import edit from './module/edit'
export default {
  // components: { eHeader, edit, treeTable },
  components: { },
  data() {
    return {
      menus: [],
      delLoading: false,
      total: 0,
      pageIndex: 1,
      pageSize: 50,
      queryMenuName: '',
      load: false
    }
  },
  created() {
    this.getMenus()
  },
  methods: {
    parseTime,
    beforeInit() {
      return true
    },
    subDelete(id) {
      this.delLoading = true
      del(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.init()
        this.$message({
          showClose: true,
          type: 'success',
          message: '删除成功!',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[id].doClose()
        console.log(err)
      })
    },
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    getMenus() {
      getMenuList(this.queryMenuName, 0, this.pageIndex, this.pageSize).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            const dataList = res.data.dataList.map(item => {
              return { ...item, label: item.roleName }
            })
            this.menus = dataList
            this.total = res.data.total
            this.pageIndex = res.data.pageIndex
            this.pageSize = res.data.pageSize
          }
        } else {
          this.$message.error('查询出错')
        }
      })
    },
    // 去查询
    toQuery() {
      this.getMenus()
    },
    getNextMenu(tree, treeNode, resolve) {
      getMenuList(this.queryMenuName, tree.menuId, this.pageIndex, this.pageSize).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            const dataList = res.data.dataList.map(item => {
              return { ...item, label: item.roleName }
            })
            resolve(dataList)
          }
        } else {
          this.$message.error('查询出错')
        }
      })
    },
    pageChange(e) {
      this.pageIndex = e
      this.getRoleALL()
    },
    sizeChange(e) {
      this.page = 1
      this.pageSize = e
      this.getRoleALL()
    }
  }
}
</script>

<style scoped>

</style>
