import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/users/',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/users/' + id + '/',
    method: 'delete'
  })
}

export function edit(id, data) {
  return request({
    url: 'api/users/' + id + '/',
    method: 'put',
    data
  })
}

export function updatePasswd(id, data) {
  return request({
    url: 'api/users/' + id + '/change-passwd/',
    method: 'post',
    data
  })
}

export function getUserList(username, isDeleted, pageIndex, pageSize) {
  var url = 'user/list?pageIndex=' + pageIndex + '&pageSize=' + pageSize
  if (username) {
    url += '&username=' + username
  }
  if (isDeleted) {
    url += '&isDeleted=' + isDeleted
  }
  return request({
    url: url,
    method: 'get'
  })
}

