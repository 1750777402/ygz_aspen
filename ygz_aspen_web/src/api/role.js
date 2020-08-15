import request from '@/utils/request'

// 获取所有的Role
export function getRoleList(roleName, isDeleted, pageIndex, pageSize) {
  var url = 'role/list?pageIndex=' + pageIndex + '&pageSize=' + pageSize
  if (roleName) {
    url += '&roleName=' + roleName
  }
  if (isDeleted != null) {
    url += '&isDeleted=' + isDeleted
  }
  return request({
    url: url,
    method: 'get'
  })
}

export function saveRole(data) {
  return request({
    url: '/role/save',
    method: 'post',
    data
  })
}

export function delRole(id) {
  return request({
    url: '/role/delRole?roleId=' + id,
    method: 'get'
  })
}
