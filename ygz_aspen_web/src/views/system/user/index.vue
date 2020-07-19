<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryUsername" clearable placeholder="输入关键字搜索" style="width: 200px;" class="filter-item"/>
      <el-select v-model="queryIsDeleted" clearable placeholder="状态" class="filter-item" style="width: 90px">
        <el-option v-for="item in isDeletedOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
    </div>
    <!--表格渲染-->
    <el-table :data="users" size="small" border style="width: 100%;">
      <el-table-column label="头像" width="60px">
        <template slot-scope="scope">
          <img :src="scope.row.image" class="el-avatar">
        </template>
      </el-table-column>
      <el-table-column prop="userId" label="用户Id" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="usernick" label="用户昵称"/>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="phone" label="手机号码"/>
      <el-table-column label="状态" width="50px">
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
      :page-sizes="[20, 30, 50, 100]"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
  </div>
</template>

<script>
import { del } from '@/api/user'
import { getUserList } from '@/api/user'
import { parseTime } from '@/utils/index'
// import eHeader from './module/header'
// import edit from './module/edit'
// import updatePass from './module/updatePass'
export default {
  components: { },
  // components: { eHeader, edit, updatePass },
  data() {
    return {
      users: [],
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
      queryUsername: ''
    }
  },
  created() {
    this.getUserALL()
  },
  methods: {
    parseTime,
    beforeInit() {

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
    getUserALL() {
      getUserList(this.queryUsername, this.queryIsDeleted, this.pageIndex, this.pageSize).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            const dataList = res.data.dataList.map(item => {
              return { ...item, label: item.username }
            })
            this.users = dataList
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
      this.getUserALL()
    },
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    pageChange(e) {
      this.pageIndex = e
      this.getUserALL()
    },
    sizeChange(e) {
      this.page = 1
      this.pageSize = e
      this.getUserALL()
    }
  }
}
</script>

<style scoped>

</style>
