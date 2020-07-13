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

export function getUserList(name) {
  if (name) {
    return request({
      url: 'user/list?pageIndex=0&pageSize=10',
      method: 'get'
    })
  } else {
    return request({
      url: 'user/list?pageIndex=0&pageSize=10',
      method: 'get'
    })
  }
}

