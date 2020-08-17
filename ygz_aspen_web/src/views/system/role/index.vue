<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryRoleName" clearable placeholder="角色名称" style="width: 200px;" class="filter-item"/>
      <el-select v-model="queryIsDeleted" clearable placeholder="角色状态" class="filter-item" style="width: 90px">
        <el-option v-for="item in isDeletedOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="addRoleDiaLog">新增用户</el-button>
    </div>
    <!--表格渲染-->
    <el-table :data="roles" size="small" border style="width: 100%;">
      <el-table-column prop="roleId" label="角色id" width="100px"/>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色Code" />
      <el-table-column label="角色状态" >
        <template slot-scope="scope">
          <span>{{ scope.row.isDeleted === 0 ? '正常':'冻结' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="350px" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleRoleMenu(scope.$index, scope.row)">设置菜单</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDel(scope.$index, scope.row)">删除</el-button>
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

    <!-- Role Form -->
    <el-dialog :append-to-body="true" :visible.sync="dialogAddRoleVisible" :title="formTitle" width="550px">
      <el-form :model="addRoleForm" size="small">
        <el-row>
          <el-col >
            <el-form-item :label-width="formLabelWidth" label="角色名称" >
              <el-input v-model="addRoleForm.roleName" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col >
            <el-form-item :label-width="formLabelWidth" label="角色code" >
              <el-input v-model="addRoleForm.roleCode" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddRole">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </div>
    </el-dialog>

    <!-- RoleMenuTerr dialog -->
    <el-dialog :append-to-body="true" :visible.sync="dialogRoleMenuVisible" title="设置角色菜单" width="550px">
      <el-tree
        ref="roleMenuTree"
        :data="roleMenuTreeData"
        :default-checked-keys="defaultCheckedKeys"
        :props="roleMenuTreeDefaultProps"
        :default-expand-all="true"
        show-checkbox
        node-key="id"
      >
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelSetRoleMenu">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { delRole, saveRole, getRoleList, saveRoleMenu } from '@/api/role'
import { getRoleMenuTree } from '@/api/menu'
import { parseTime } from '@/utils/index'
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
      queryRoleName: '',
      dialogAddRoleVisible: false,
      dialogRoleMenuVisible: false,
      addRoleForm: {
        roleName: '',
        roleCode: '',
        roleId: null
      },
      formLabelWidth: '120px',
      formTitle: '',
      roleMenuTreeData: [],
      roleMenuTreeDefaultProps: {
        children: 'children',
        label: 'label'
      },
      defaultCheckedKeys: [],
      selectRoleId: null
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
    handleDel(id, row) {
      this.delLoading = true
      delRole(row.roleId).then(res => {
        this.delLoading = false
        if (res && res.code === 1001) {
          this.getRoleALL()
          this.$message({
            showClose: true,
            type: 'success',
            message: '删除成功!',
            duration: 2500
          })
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => {
        this.delLoading = false
        this.$message.error('删除角色出错')
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
      this.formTitle = '编辑角色'
      this.addRoleForm = {
        roleName: row.roleName,
        roleCode: row.roleCode,
        roleId: row.roleId
      }
      this.dialogAddRoleVisible = true
    },
    pageChange(e) {
      this.pageIndex = e
      this.getRoleALL()
    },
    sizeChange(e) {
      this.page = 1
      this.pageSize = e
      this.getRoleALL()
    },
    addRoleDiaLog() {
      this.dialogAddRoleVisible = true
      this.formTitle = '新增角色'
      this.clearFormData()
    },
    saveRole() {
      saveRole(this.addRoleForm).then(res => {
        this.getRoleALL()
        this.$message({
          showClose: true,
          type: 'success',
          message: '操作成功',
          duration: 2500
        })
        this.cancelAddRole()
      }).catch(err => {
        console.log(err)
      })
    },
    saveRoleMenu() {
      const menNodes = this.$refs.roleMenuTree.getCheckedNodes(false, true)
      const menIds = []
      if (menNodes != null && menNodes.length > 0) {
        menNodes.forEach(item => menIds.push(item.id))
      }
      saveRoleMenu(this.selectRoleId, menIds).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            this.cancelSetRoleMenu()
          } else {
            this.$message.error(res.message)
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    cancelAddRole() {
      this.dialogAddRoleVisible = false
      this.clearFormData()
    },
    cancelSetRoleMenu() {
      this.dialogRoleMenuVisible = false
      this.selectRoleId = null
    },
    clearFormData() {
      this.addRoleForm = { roleName: '', roleId: null }
    },
    handleRoleMenu(index, row) {
      this.selectRoleId = row.roleId
      this.dialogRoleMenuVisible = true
      getRoleMenuTree(row.roleId).then(res => {
        if (res.code === 1001) {
          if (res.data) {
            this.roleMenuTreeData = res.data.treeVOS
            this.defaultCheckedKeys = res.data.roleMenuIds
          }
        } else {
          this.$message.error('查询菜单树出错')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
