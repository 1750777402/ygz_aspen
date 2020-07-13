<template>
  <div class="app-container">
    <!-- <eHeader :users="users" :organizations="organizations" :query="query"/> -->
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" border style="width: 100%;">
      <el-table-column label="头像" width="50px">
        <template slot-scope="scope">
          <img :src="scope.row.image" class="el-avatar">
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="150px"/>
      <el-table-column prop="usernick" label="姓名" width="100px"/>
      <el-table-column prop="roleName" label="邮箱"/>
      <el-table-column prop="phone" label="手机号码" width="100px"/>
      <el-table-column label="状态" width="50px">
        <template slot-scope="scope">
          <span>{{ scope.row.isDeleted ? '激活':'锁定' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200px" align="center">
        <!-- <template slot-scope="scope">
          <edit v-if="checkPermission(['admin','user_all','user_edit'])" :data="scope.row" :users="users" :organizations="organizations" :sup_this="sup_this"/>
          <updatePass v-if="checkPermission(['admin','user_all'])" :data="scope.row" :sup_this="sup_this"/>
          <el-popover
            v-if="checkPermission(['admin','user_all','user_delete'])"
            :ref="scope.row.id"
            placement="top"
            width="180">
            <p>确定删除本条数据吗？所有关联的数据将会被清除</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
            </div>
            <el-button slot="reference" :disabled="scope.row.id === 1" type="danger" size="mini">删除</el-button>
          </el-popover>
        </template> -->
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
import checkPermission from '@/utils/permission'
// import initData from '@/mixins/initData'
import { del } from '@/api/user'
import { getUserList } from '@/api/user'
import { getOrganizationTree } from '@/api/organization'
import { parseTime } from '@/utils/index'
// import eHeader from './module/header'
// import edit from './module/edit'
// import updatePass from './module/updatePass'
export default {
  components: { },
  // components: { eHeader, edit, updatePass },
  // mixins: [initData],
  data() {
    return {
      users: [], organizations: [], delLoading: false, sup_this: this
    }
  },
  created() {
    this.getUserALL()
    // this.getOrganizations()
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    parseTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/users/'
      const sort = 'id'
      const query = this.query
      const value = query.value
      const is_active = query.is_active
      this.params = { page: this.page, size: this.size, ordering: sort }
      if (is_active !== '' && is_active !== null) { this.params['is_active'] = is_active }
      if (value) { this.params['search'] = value }
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
    getOrganizations() {
      getOrganizationTree().then(res => {
        this.organizations = res.data
      })
    },
    getUserALL() {
      getUserList().then(res => {
        const newres = res.results.map(item => {
          return { ...item, label: item.name }
        })
        debugger
        this.users = newres
      })
    }
  }
}
</script>

<style scoped>

</style>
