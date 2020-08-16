import request from '@/utils/request'

// 获取所有的菜单树
export function getMenuList(queryMenuName, parentMenuId, pageIndex, pageSize) {
  var url = 'menu/tree?pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&parentMenuId=' + parentMenuId
  if (queryMenuName) {
    url += '&menuName=' + queryMenuName
  }
  return request({
    url: url,
    method: 'get'
  })
}

export function getMenuTree(roleId) {
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

export function getMenuNext(parentMenuId) {
  var url = 'menu/getMenuNext?parentMenuId=' + parentMenuId
  return request({
    url: url,
    method: 'get'
  })
}
