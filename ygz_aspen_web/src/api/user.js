import request from '@/utils/request'

export function saveUser(data) {
  debugger
  return request({
    url: '/user/save',
    method: 'post',
    data
  })
}

export function delUser(id) {
  return request({
    url: '/user/del?userId=' + id,
    method: 'get'
  })
}

export function editUser(data) {
  return request({
    url: '/user/save',
    method: 'post',
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

