<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryRoleName" clearable placeholder="角色名称" style="width: 200px;" class="filter-item"/>
      <el-select v-model="queryIsDeleted" clearable placeholder="角色状态" class="filter-item" style="width: 90px">
        <el-option v-for="item in isDeletedOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
    </div>
    <!--表格渲染-->
    <el-table :data="roles" size="small" border style="width: 100%;">
      <el-table-column prop="roleId" label="角色id" width="100px"/>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column label="角色状态" >
        <template slot-scope="scope">
          <span>{{ scope.row.isDeleted === 0 ? '正常':'冻结' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200px" align="center">
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
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
  </div>
</template>

<script>
import { delRole } from '@/api/role'
import { getRoleList } from '@/api/role'
import { parseTime } from '@/utils/index'
// import edit from './module/edit'
export default {
  components: { },
  // components: { eHeader, edit, updatePass },
  data() {
    return {
      roles: [],
      delLoading: false,
      isDeletedOptions: [
        {
          value: '0',
          label: '正常'
        },
        {
          value: '1',
          label: '冻结'
        }
      ],
      queryIsDeleted: null,
      total: 0,
      pageIndex: 1,
      pageSize: 20,
      queryRoleName: ''
    }
  },
  created() {
    this.getRoleALL()
  },
  methods: {
    parseTime,
    beforeInit() {
      return true
    },
    subDelete(id) {
      this.delLoading = true
      delRole(id).then(res => {
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
    getRoleALL() {
      getRoleList(this.queryRoleName, this.queryIsDeleted, this.pageIndex, this.pageSize).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            const dataList = res.data.dataList.map(item => {
              return { ...item, label: item.roleName }
            })
            this.roles = dataList
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
      this.getRoleALL()
    },
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
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
