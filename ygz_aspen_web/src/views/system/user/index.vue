<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryUsername" clearable placeholder="输入关键字搜索" style="width: 200px;" class="filter-item"/>
      <el-select v-model="queryIsDeleted" clearable placeholder="状态" class="filter-item" style="width: 90px">
        <el-option v-for="item in isDeletedOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="addUserDiaLog">新增用户</el-button>
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
            :loading="delLoading"
            size="mini"
            type="danger"
            @click="handleDel(scope.$index, scope.row)">删除</el-button>
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

    <!-- User Form -->
    <el-dialog :append-to-body="true" :visible.sync="dialogAddUserVisible" :title="formTitle" width="850px">
      <el-form :model="addUserForm" size="small">
        <el-row>
          <el-col :span="12">
            <el-form-item :label-width="formLabelWidth" label="用户名" >
              <el-input v-model="addUserForm.username" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label-width="formLabelWidth" label="用户昵称">
              <el-input v-model="addUserForm.usernick" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label-width="formLabelWidth" label="手机号">
              <el-input v-model="addUserForm.phone" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label-width="formLabelWidth" label="所属角色" >
              <el-select v-model="addUserForm.roleIds" multiple placeholder="请选择用户所属角色">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddUser">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { delUser, getUserList, saveUser } from '@/api/user'
import { getRoleList } from '@/api/role'
import { parseTime } from '@/utils/index'

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
      queryUsername: '',
      dialogAddUserVisible: false,
      addUserForm: {
        username: '',
        usernick: '',
        phone: '',
        roleIds: [],
        userId: null
      },
      roleOptions: [],
      formLabelWidth: '120px',
      formTitle: ''
    }
  },
  created() {
    this.getUserALL()
    this.getRoleOptions()
  },
  methods: {
    parseTime,
    beforeInit() {
    },
    handleDel(id, row) {
      this.delLoading = true
      delUser(row.userId).then(res => {
        this.delLoading = false
        this.getUserALL()
        this.$message({
          showClose: true,
          type: 'success',
          message: '删除成功!',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$message.error('删除用户出错')
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
      this.getRoleOptions()
      this.formTitle = '编辑用户'
      this.addUserForm = {
        username: row.username,
        usernick: row.usernick,
        phone: row.phone,
        roleIds: row.roleIds,
        userId: row.userId
      }
      this.dialogAddUserVisible = true
    },
    pageChange(e) {
      this.pageIndex = e
      this.getUserALL()
    },
    sizeChange(e) {
      this.page = 1
      this.pageSize = e
      this.getUserALL()
    },
    addUserDiaLog() {
      this.dialogAddUserVisible = true
      this.formTitle = '新增用户'
      this.getRoleOptions()
    },
    saveUser() {
      saveUser(this.addUserForm).then(res => {
        this.getUserALL()
        this.$message({
          showClose: true,
          type: 'success',
          message: '操作成功',
          duration: 2500
        })
        this.cancelAddUser()
      }).catch(err => {
        console.log(err)
      })
    },
    getRoleOptions() {
      getRoleList(null, 0, 1, 100).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            const dataList = res.data.dataList.map(item => {
              return { label: item.roleName, value: item.roleId }
            })
            this.roleOptions = dataList
          }
        } else {
          this.$message.error('获取角色选项加载出错')
        }
      })
    },
    cancelAddUser() {
      this.dialogAddUserVisible = false
      this.clearFormData()
    },
    clearFormData() {
      this.addUserForm = { username: '', usernick: '', phone: '', role: [], userId: null }
    }
  }
}
</script>

<style scoped>

</style>
