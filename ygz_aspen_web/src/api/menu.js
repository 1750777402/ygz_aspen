import request from '@/utils/request'

// 获取所有的菜单树
export function getMenuList(queryMenuName, parentMenuId, pageIndex, pageSize) {
  var url = 'menu/list?pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&parentMenuId=' + parentMenuId
  if (queryMenuName) {
    url += '&menuName=' + queryMenuName
  }
  return request({
    url: url,
    method: 'get'
  })
}

export function getRoleMenuTree(roleId) {
  var url = 'menu/roleMenuTree?roleId=' + roleId
  return request({
    url: url,
    method: 'get'
  })
}

export function saveMenu(data) {
  return request({
    url: '/menu/save',
    method: 'post',
    data
  })
}

export function delMenu(id) {
  return request({
    url: '/menu/del?menuId=' + id,
    method: 'get'
  })
}

export function getMenuTree() {
  var url = 'menu/getMenuTree'
  return request({
    url: url,
    method: 'get'
  })
}
