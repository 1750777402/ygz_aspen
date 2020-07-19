import request from '@/utils/request'

// 获取所有的Role
export function getRoleList(roleName, isDeleted, pageIndex, pageSize) {
  var url = 'role/list?pageIndex=' + pageIndex + '&pageSize=' + pageSize
  if (roleName) {
    url += '&roleName=' + roleName
  }
  if (isDeleted) {
    url += '&isDeleted=' + isDeleted
  }
  return request({
    url: url,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/roles/',
    method: 'post',
    data
  })
}

export function delRole(id) {
  return request({
    url: 'api/roles/' + id + '/',
    method: 'delete'
  })
}

export function edit(id, data) {
  return request({
    url: 'api/roles/' + id + '/',
    method: 'put',
    data
  })
}

export function retrieve(id) {
  return request({
    url: 'api/roles/' + id + '/',
    method: 'get'
  })
}

export function save(id, data) {
  return request({
    url: 'api/roles/' + id + '/',
    method: 'patch',
    data
  })
}
