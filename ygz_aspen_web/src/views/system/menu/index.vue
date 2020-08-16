<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="queryMenuName" clearable placeholder="菜单名称" style="width: 200px;" class="filter-item"/>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="addMenuDiaLog">新增菜单</el-button>
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
          <span>{{ scope.row.hidden === 0 ? '否':'是' }}</span>
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
            @click="handleDel(scope.$index, scope.row)">删除</el-button>
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

    <!-- Menu Form -->
    <el-dialog :append-to-body="true" :visible.sync="dialogAddMenuVisible" :title="formTitle" width="600px">
      <el-form ref="addMenuForm" :model="addMenuForm" size="small" label-width="80px">
        <el-form-item label="菜单名称">
          <el-input v-model="addMenuForm.menuName" placeholder="名称" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="是否显示">
          <el-radio v-model="addMenuForm.hidden" label="1">是</el-radio>
          <el-radio v-model="addMenuForm.hidden" label="0" >否</el-radio>
        </el-form-item>
        <el-form-item label="菜单图标">
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            @show="$refs['iconSelect'].reset()">
            <IconSelect ref="iconSelect" @selected="selected" />
            <el-input slot="reference" v-model="addMenuForm.icon" style="width: 460px;" placeholder="点击选择图标" readonly>
              <svg-icon v-if="addMenuForm.icon" slot="prefix" :icon-class="addMenuForm.icon" class="el-input__icon" style="height: 32px;width: 16px;" />
              <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
            </el-input>
          </el-popover>
        </el-form-item>
        <el-form-item label="菜单排序">
          <el-input v-model.number="addMenuForm.sort" placeholder="序号越小越靠前" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input v-model="addMenuForm.path" placeholder="菜单路径" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="addMenuForm.component" placeholder="组件路径" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="父级菜单" style="width: 460px;">
          <el-cascader v-model="addMenuForm.parentId" :props="menuCascader" clearable style="width: 460px;" placeholder="请选择父级菜单，不选择表示为1级菜单"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddMenu">取 消</el-button>
        <el-button type="primary" @click="saveMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { delMenu, getMenuList, saveMenu, getMenuNext } from '@/api/menu'
import { parseTime } from '@/utils/index'
import IconSelect from '@/components/IconSelect'
export default {
  components: { IconSelect },
  data() {
    return {
      menus: [],
      delLoading: false,
      total: 0,
      pageIndex: 1,
      pageSize: 50,
      queryMenuName: '',
      load: false,
      dialogAddMenuVisible: false,
      addMenuForm: {
        menuId: null,
        menuName: '',
        sort: 999,
        path: '',
        component: '',
        hidden: '1',
        parentId: null,
        icon: ''
      },
      formLabelWidth: '120px',
      formTitle: '',
      menuCascader: {
        lazy: true,
        checkStrictly: true,
        lazyLoad(node, resolve) {
          var parentId = 0
          if (node.value) {
            parentId = node.value
          }
          getMenuNext(parentId).then(res => {
            if (res.code === 1001) {
              if (res.data) {
                resolve(res.data)
              }
            }
          })
        }
      }
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
    handleEdit(index, row) {
      this.addMenuForm = {
        menuId: row.menuId,
        menuName: row.menuName,
        sort: row.sort,
        path: row.path,
        component: row.component,
        hidden: row.hidden,
        parentId: [row.parentId],
        icon: row.icon
      }
      this.dialogAddMenuVisible = true
      this.formTitle = '编辑菜单'
    },
    handleDel(index, row) {
      delMenu(row.menuId).then(res => {
        this.delLoading = false
        this.getMenus()
        this.$message({
          showClose: true,
          type: 'success',
          message: '删除成功!',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        console.log(err)
      })
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
    },
    addMenuDiaLog() {
      this.dialogAddMenuVisible = true
      this.formTitle = '新增菜单'
      this.clearFormData()
    },
    cancelAddMenu() {
      this.dialogAddMenuVisible = false
      this.clearFormData()
    },
    clearFormData() {
      this.addMenuForm = {
        menuId: null,
        menuName: '',
        sort: 999,
        path: '',
        component: '',
        hidden: '1',
        parentId: null,
        icon: ''
      }
    },
    saveMenu() {
      var menuForm = this.addMenuForm
      debugger
      if (menuForm.parentId && menuForm.parentId.length > 0) {
        menuForm.parentId = menuForm.parentId[0]
      } else {
        menuForm.parentId = 0
      }
      saveMenu(menuForm).then(res => {
        this.getMenus()
        this.cancelAddMenu()
        this.$message({
          showClose: true,
          type: 'success',
          message: '操作成功',
          duration: 2500
        })
      }).catch(err => {
        console.log(err)
      })
    },
    selected(name) {
      this.addMenuForm.icon = name
    }
  }
}
</script>

<style scoped>

</style>
